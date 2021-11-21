/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;


import nexp.com.app.model.Compra;
import nexp.com.app.model.Transaccionp;
import nexp.com.app.negocio.NorteXploradores;
//import nexp.com.app.security.service.UsuarioService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import nexp.com.app.service.TransaccionService;
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
    public CompraService compraService;
//    @Autowired
//    public UsuarioService user;

    NorteXploradores nexp = new NorteXploradores();
    
    @GetMapping
    public ResponseEntity<List<Transaccionp>> transacciones(){
       return new ResponseEntity(pser.listar(), HttpStatus.OK);
    }


    @PostMapping(path = "/confirmacion")
    public ResponseEntity<?> pago(@RequestParam Map<String, String> body) {

        Transaccionp pay = new Transaccionp();

        pay.setDate(nexp.convertirFecha(body.get("date"),"\\."));
        pay.setDescription(body.get("description")+"");
        Long idCompra = Long.parseLong(body.get("reference_sale"));
        Compra compra = compraService.encontrar(idCompra).orElse(null);

        pay.setReferenceSale(compra);
        pay.setResponseMessagePol(body.get("response_message_pol"));
        pay.setTransactionId(body.get("transaction_id"));
        pay.setValue(Long.parseLong(body.get("value").split("\\.")[0]));

//      Compra Fallida
        if(!pay.getResponseMessagePol().equals("APPROVED") && !pay.getResponseMessagePol().equals("PENDING")){
            pser.guardar(pay);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }

//      Es una compra o pago total 100%
        if(pay.getResponseMessagePol().equals("APPROVED") && pay.getValue() == (long)compra.getTotalCompra()){ //Se pagó el total y fue aprobada
            pser.guardar(pay);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }

//      Es una reserva;
//      Si la compra actual está aprobada y la transaccion anterior fue aprobada

        boolean estaAprobada =false;
        if(compra.transaccionpCollection().size()>0 && pay.getResponseMessagePol().equals("APPROVED")){

            for (Transaccionp transaccionp : compra.transaccionpCollection()) { //Recorro todas las transacciones, y si alguna esta aprobada entonces se que ya tiene una segunda transaccion aprobada
                if (transaccionp.getResponseMessagePol().equals("APROVED")) {
                    estaAprobada = true;
                    break;
                }
            }
            if(estaAprobada){
                compra.setEstado("PAGADO");
            }else{
                compra.setEstado("PAGO_PARCIAL");
            }
            pser.guardar(pay);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }


        if (pay.getResponseMessagePol().equals("APPROVED")){ //SIGUE EN DUDA ESTE METODO
            compra.setEstado("PAGO_PARCIAL");
        }

        pser.guardar(pay);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
