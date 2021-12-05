/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;


import nexp.com.app.model.*;
import nexp.com.app.negocio.EmailService;
import nexp.com.app.negocio.NorteXploradores;

import java.util.Date;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author santi
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pagos",
        method = RequestMethod.POST,
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})

@Slf4j
public class TransaccionRest {
  
    @Autowired
    public TransaccionService pser;

    @Autowired
    public TourService tourService;

    @Autowired
    public CompraService compraService;

    @Autowired
    public NotificacionService notificacionService;

    @Autowired
    public ReservaService reservaService;
//    @Autowired
//    public UsuarioService user;

    @Value("${spring.mail.username}")
    String emailUsuarioEmisor;

    @Value("${spring.mail.password}")
    String clave;

    NorteXploradores nexp = new NorteXploradores();
    
    @GetMapping
    public ResponseEntity<List<Transaccionp>> transacciones(){
       return new ResponseEntity(pser.listar(), HttpStatus.OK);
    }


    @PostMapping(path = "/confirmacion")
    public ResponseEntity<?> pago(@RequestParam Map<String, String> body) {

        Transaccionp pay = new Transaccionp();
        log.info(body.toString());
        pay.setDate(nexp.convertirFecha(body.get("date"),"\\."));
        pay.setDescription(body.get("description")+"");

        long idCompra = Long.parseLong(body.get("extra1"));
        Compra compra = compraService.encontrar(idCompra).orElse(null);
        pay.setReferenceSale(compra);
        pay.setResponseMessagePol(body.get("response_message_pol"));
        pay.setTransactionId(body.get("transaction_id"));
        pay.setValue(Long.parseLong(body.get("value").split("\\.")[0]));
        Usuario usuario = compra.getUsuario();

//      Compra Fallida
        if(!pay.getResponseMessagePol().equals("APPROVED") && !pay.getResponseMessagePol().equals("PENDING")){
            EmailService email=new EmailService(emailUsuarioEmisor, clave);
            email.enviarEmail(usuario.getEmail(), "Compra fallida",
                    "Hola "+usuario.getUsername()+"" +
                            ", has intentado realizar el pago del paquete turistico destino " +
                            ""+compra.getTour().getPaquete().getMunicipio().getNombre()+" " +
                            "por un total de: "+compra.getTotalCompra()+"" +
                            "<hr> Numero Factura: "+compra.getIdCompra()+"<br> Fecha:"+compra.getFecha());

            pser.guardar(pay);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }

//      Es una compra o pago total 100%
        if(pay.getResponseMessagePol().equals("APPROVED") && pay.getValue() == (long)compra.getTotalCompra()){ //Se pagó el total y fue aprobada
            compra.setEstado("PAGADO");
            Tour t = compra.getTour();
            t.setCantCupos(t.getCantCupos()-t.getCantCupos());
            Notificacion notificacion = new Notificacion();
            notificacion.setFecha(new Date());

            notificacion.setDescripcion("Actualmente quedan "+t.getCantCupos()+" disponibles del Tour destino "+t.getPaquete().getMunicipio().getNombre());
            tourService.guardar(t);
            notificacionService.guardar(notificacion);
            pser.guardar(pay);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }

//      Es una reserva;
//      Si la compra actual está aprobada y la transaccion anterior fue aprobada
        log.info("entra");
        boolean estaAprobada =false;

        List<Transaccionp> transaccionps =(List)compra.transaccionpCollection();
        Tour t = compra.getTour();

        if(transaccionps.size()>0 && pay.getResponseMessagePol().equals("APPROVED")){
            log.info("es una compra actual y tiene reservas"+transaccionps.size()+"+++++++++++++++++++++++++++");
            for (Transaccionp transaccionp : transaccionps) { //Recorro todas las transacciones, y si alguna esta aprobada entonces se que ya tiene una segunda transaccion aprobada
                log.info(transaccionp.getResponseMessagePol()+"+++++++++++++++++++++++++++++++++++++++++++++++++");
                if (transaccionp.getResponseMessagePol().equals("APPROVED") && !transaccionp.getTransactionId().equals(pay.getTransactionId())) {
                    log.info("tiene una transaccion aprobada "+transaccionp.getTransactionId());
                    estaAprobada = true;
                    break;
                }
            }

            if(estaAprobada){
                compra.setEstado("PAGADO");
                t.setCantCupos(t.getCantCupos()-compra.getCantidadPasajeros());
                Notificacion notificacion = new Notificacion();
                notificacion.setFecha(new Date());
                notificacion.setDescripcion("Actualmente quedan "+t.getCantCupos()+" disponibles del Tour destino "+t.getPaquete().getMunicipio().getNombre());
                tourService.guardar(t);
                notificacionService.guardar(notificacion);
            }else{
                t.setCantCupos(t.getCantCupos()-compra.getCantidadPasajeros());
                compra.setEstado("PAGO_PARCIAL");
                Reserva reserva = compra.getReserva();
                reserva.setEstado("PAGO_PARCIAL");
                reservaService.guardar(reserva);
                EmailService email=new EmailService(emailUsuarioEmisor, clave);
                email.enviarEmail(usuario.getEmail(), "Pago pendiente",
                        "Hola "+usuario.getUsername()+"" +
                                ", has realizado el pago parcial del paquete turistico destino " +
                                ""+compra.getTour().getPaquete().getMunicipio().getNombre()+", recuerda pagar el otro 50% restante" +
                                "por un total de: "+compra.getTotalCompra()+"" +
                                "<hr> Numero Factura: "+compra.getIdCompra()+"<br> Fecha:"+compra.getFecha());
                tourService.guardar(t);

            }
            pser.guardar(pay);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }


        if (pay.getResponseMessagePol().equals("APPROVED")){ //SIGUE EN DUDA ESTE METODO
            t.setCantCupos(t.getCantCupos()-compra.getCantidadPasajeros());
            Reserva reserva = compra.getReserva();
            reserva.setEstado("PAGO_PARCIAL");
            reservaService.guardar(reserva);
            EmailService email=new EmailService(emailUsuarioEmisor, clave);
            email.enviarEmail(usuario.getEmail(), "Pago pendiente",
                    "Hola "+usuario.getUsername()+"" +
                            ", has realizado el pago parcial del paquete turistico destino " +
                            ""+compra.getTour().getPaquete().getMunicipio().getNombre()+", recuerda pagar el otro 50% restante" +
                            "por un total de: "+compra.getTotalCompra()+"" +
                            "<hr> Numero Factura: "+compra.getIdCompra()+"<br> Fecha:"+compra.getFecha());
            compra.setEstado("PAGO_PARCIAL");
            tourService.guardar(t);

        }

        pser.guardar(pay);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
