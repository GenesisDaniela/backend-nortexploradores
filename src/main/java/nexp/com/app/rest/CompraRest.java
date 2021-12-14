/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.*;
import nexp.com.app.negocio.EmailService;
import nexp.com.app.negocio.response.*;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.*;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Santi & GenesisDanielaVJ
 */

@RestController
@RequestMapping("/compra")
@Slf4j
@CrossOrigin(origins = "*")
public class CompraRest {
    @Autowired
    CompraService compraservice;

    @Autowired
    TourService tourService;

    @Autowired
    ReservaService reservaService;

    @Autowired
    DetalleCompraService detalleCompraService;

    @Autowired
    TransaccionService transaccionService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    NotificacionService notificacionService;

    @Autowired
    PaqueteService paqueteService;

    @Autowired
    DevolucionService devolucionService;

    @Value("${spring.mail.username}")
    String emailUsuarioEmisor;

    @Value("${spring.mail.password}")
    String clave;
    
    @GetMapping
    public ResponseEntity<List<Compra>> getCompra() {
        return ResponseEntity.ok(compraservice.listar());
    }

    @GetMapping(path = "/{idCompra}")
    public ResponseEntity<Compra> encontrarCompra(@PathVariable Long idCompra) {
        Compra compra = compraservice.encontrar(idCompra).orElse(null);

        if(compra ==null){
            return new ResponseEntity("COMPRA NO ENCONTRADA", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(compra);
    }

    @PostMapping
    public ResponseEntity<Compra> crearCompra(@RequestBody @Valid Compra compra){
        compraservice.guardar(compra);
        return ResponseEntity.ok(compra);
    }

    @GetMapping(path = "/{idUsuario}/comprasPagadas")
    public ResponseEntity<?> compraPagada(@PathVariable int idUsuario){
        return ResponseEntity.ok(compraservice.comprasPagadas(idUsuario));
    }
    @PostMapping(path = "/compraReservada/{idtour}")
    public ResponseEntity<Compra> crearCompraReservada(@RequestBody @Valid Compra compra, @PathVariable int idtour){
        Tour t = tourService.encontrar(idtour).get();
        Date fechaSalida = t.getFechaSalida();
        Date fechaReserva = new Date();
        int milisegundospordia= 86400000;
        int dias = (int) ((fechaSalida.getTime()-fechaReserva.getTime()) / milisegundospordia);

        if(dias<3){
            return new ResponseEntity("No se puede reservar menos de 3 días antes de la salida del paquete", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioService.encontrar(compra.getUsuario().getId_Usuario()).get();
//        for(Compra c: usuario.compraCollection()){
//            if(c.getTour().getIdTour() == idtour){
//                if(c.getEstado().equals("PAGADO")|| c.getEstado().equals("PAGO_PARCIAL")){
//                    return new ResponseEntity("No puedes comprar un mismo tour dos veces", HttpStatus.BAD_REQUEST);
//                }
//
//            }
//        }

        Reserva reserva = new Reserva();
        reserva.setFecha(LocalDate.now());
        reserva.setEstado("PENDIENTE");
        reservaService.guardar(reserva);
        compra.setFecha(LocalDate.now());
        compra.setReserva(reserva);
        compra.setEstado("PENDIENTE");
        compraservice.guardar(compra);
        return ResponseEntity.ok(compra);
    }

    @GetMapping(path = "/{id}/transacciones")
    public ResponseEntity<List<Transaccionp>> transaccionPorCompra(@PathVariable Long id) {
        return ResponseEntity.ok((List)compraservice.encontrar(id).get().transaccionpCollection());
    }

    @GetMapping(path = "/{id}/cancelarReserva")
    public ResponseEntity<?> cancelarReserva(@PathVariable int id) {
        Reserva reserva = reservaService.encontrar(id).get();
        if(reserva == null){
            return new ResponseEntity("RESERVA NO ENCONTRADA", HttpStatus.NOT_FOUND);
        }

        Compra compra = ((List<Compra>)reserva.compraCollection()).get(0);

        String cuerpo = "<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
                "        <tr>\n" +
                "          <td align=\"center\" style=\"padding:0;\">\n" +
                "            <table role=\"presentation\" style=\"width:602px;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0;text-align:left;\">\n" +
                "              <tr>\n" +
                "                <td align=\"center\" style=\"padding:40px 0 30px 0;background:#153643;\">\n" +
                "                  <img src=\"https://raw.githubusercontent.com/SantiagoAndresSerrano/img-soka/master/LOGO-01.png\" alt=\"\" width=\"300\" style=\"height:auto;display:block;\" />\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <tr>\n" +
                "                <td style=\"padding:36px 30px 42px 30px;\">\n" +
                "                  <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding:0 0 36px 0;color:#153643;\">\n" +
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Cancelación de reserva</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Cancelaste la reserva del paquete destino "+compra.getTour().getPaquete().getMunicipio().getNombre()+", con fecha de salida de "+compra.getTour().getFechaSalida()+" y fecha de llegada: "+compra.getTour().getFechaLlegada()+", información:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                    <td style=\"padding:0;\">\n" +
                "                        <table class=\"simple-style\" border='1'>\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th scope=\"col\">#Referencia</th>\n" +
                "                                    <th scope=\"col\">Total</th>\n" +
                "                                    <th scope=\"col\">Estado</th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>"+compra.getIdCompra()+"</td>\n" +
                "                                    <td>"+compra.getTotalCompra()+"</td>\n" +
                "                                    <td>CANCELADO</td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <tr>\n" +
                "                <td style=\"padding:30px;background:#009045;\">\n" +
                "                  <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;font-size:9px;font-family:Arial,sans-serif;\">\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding:0;width:50%;\" align=\"left\">\n" +
                "                        <p style=\"margin:0;font-size:14px;line-height:16px;font-family:Arial,sans-serif;color:#ffffff;\">\n" +
                "                          &reg; NorteXploradores, 2021<br/><a href=\"https://front-nort-exploradores-2.vercel.app/inicio\" style=\"color:#ffffff;text-decoration:underline;\">Bienvenido</a>\n" +
                "                        </p>\n" +
                "                      </td>\n" +
                "                      <td style=\"padding:0;width:50%;\" align=\"right\">\n" +
                "                        <table role=\"presentation\" style=\"border-collapse:collapse;border:0;border-spacing:0;\">\n" +
                "                          <tr>\n" +
                "                            <td style=\"padding:0 0 0 10px;width:38px;\">\n" +
                "                              <a href=\"http://www.twitter.com/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/tw_1.png\" alt=\"Twitter\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
                "                            </td>\n" +
                "                            <td style=\"padding:0 0 0 10px;width:38px;\">\n" +
                "                              <a href=\"http://www.facebook.com/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/fb_1.png\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>";

        if(reserva.getEstado().equals("PENDIENTE")){
                for(DetalleCompra det: compra.detalleCompraCollection()) {
                    detalleCompraService.eliminar(det.getIdDetalle());
                }
                for(Transaccionp t: compra.transaccionpCollection()){
                        transaccionService.eliminar(t.getTransactionId());
                }
                compraservice.eliminar(compra.getIdCompra());


            EmailService email=new EmailService(emailUsuarioEmisor, clave);
            email.enviarEmail(compra.getUsuario().getEmail(), "Reserva cancelada",cuerpo);
            return ResponseEntity.ok(reserva); // La reserva no fue pagada ni en su 50%
        }

        int cuposDisponibles = compra.getTour().getCantCupos() + compra.getCantidadPasajeros();
        Tour tour = compra.getTour();
        tour.setCantCupos(cuposDisponibles);
        Notificacion notificacion = new Notificacion();
        EmailService email=new EmailService(emailUsuarioEmisor, clave);
        email.enviarEmail(compra.getUsuario().getEmail(), "Reserva cancelada",cuerpo);

        notificacion.setDescripcion("La reserva del usuario "+compra.getUsuario().getUsername()+" al viaje del paquete destino "+compra.getTour().getPaquete().getMunicipio().getNombre()+" con fecha de salida "+compra.getTour().getFechaSalida()+" ha sido cancelada");
        notificacion.setUsuario(compra.getUsuario());
        notificacion.setFecha(new Date());
        notificacionService.guardar(notificacion);

        reserva.setEstado("CANCELADA");
        compra.setEstado("CANCELADO");
        compraservice.guardar(compra);
        tourService.guardar(tour);
        reservaService.guardar(reserva);
        return ResponseEntity.ok(reserva);
    }

    //cantidad de tours vendidos(compras de cualquier tour?)
    // en el mes exceptuando los que devolucion != null
//    //cantidad de tours vendidos(compras de cualquier tour?)
//    // en el mes exceptuando los que devolucion != null
//    @GetMapping(path = "/cantidadToursVendidos")
//    public ResponseEntity<?> cantidadTours(){
//        int cantidadTours = 0;
//        List<Compra> compras = compraservice.listar();
//        Date fechaActual = new Date();
//        for (Compra c: compras){
//            if( c.devolucionCollection().size() == 0 && c.getEstado().equals("PAGADO") &&
//                    c.getFecha().getMonth() == fechaActual.getMonth() &&
//                    c.getFecha().getYear() == fechaActual.getYear()){
//                cantidadTours++;
//            }
//        }
//        return ResponseEntity.ok(cantidadTours);
//    }
    //pend revisar el bisiesto
    @GetMapping(path = "/totalMeses")
    public ResponseEntity<?> totalMes() throws ParseException {
        int[] totalMeses = new int[12];

        for (int i = 0; i < totalMeses.length; i++) {
            String mes = "" + (i+1);
            if(i < 9)
                mes = "0" + (i+1);
            String fecha1="2021-"+(mes)+"-01";
            String fecha2="2021-"+(mes)+"-31";

            if(i==1)
                fecha2="2021-"+(mes)+"-28";
            if(i==3 || i==5 || i==10 || i==8)
                fecha2="2021-"+(mes)+"-30";

            Integer totalC = compraservice.comprasAprobadasFecha(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2));

            Integer totalD = compraservice.devolucionesFecha(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2));

            if(totalC ==null)
                totalC=0;
            else{
                if(totalD!=null)
                    totalC-=totalD;
            }
            totalMeses[i] = totalC;
        }
        return ResponseEntity.ok(totalMeses);
    }

    @GetMapping(path = "/totalMesTabla") // corregir
    public ResponseEntity<?> totalMesTabla() throws ParseException {
        List<PaqueteCantidad> totalMeses = new ArrayList<>();
        String[] meses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "setiembre", "octubre", "noviembre", "diciembre"};

        for (int i = 0; i < 12; i++) {
            String mes = "" + (i+1);
            if(i < 9)
                mes = "0" + (i+1);
            String fecha1="2021-"+(mes)+"-01";
            String fecha2="2021-"+(mes)+"-31";

            if(i==1)
                fecha2="2021-"+(mes)+"-28";
            if(i==3 || i==5 || i==10 || i==8)
                fecha2="2021-"+(mes)+"-30";

            Integer totalC = compraservice.comprasAprobadasFecha(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2));

            Integer totalD = compraservice.devolucionesFecha(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2));

            PaqueteCantidad response = new PaqueteCantidad();
            response.setMes(meses[i]);
            if(totalC ==null){
                totalC=0;
                response.setCantidad(0);
            }
            else{
                if(totalD!=null){
                    totalC-=totalD;
                    response.setCantidad(totalC);
                }else{
                    response.setCantidad(totalC);
                }
            }
            totalMeses.add(response);
        }
        return ResponseEntity.ok(totalMeses);
    }

    @GetMapping(path = "/cantidadpaq")
    public ResponseEntity<?> cantidadPaquetes() {
        List<Compra> compras = compraservice.listar();
        int cantidadPaq[] = new int[12];
        LocalDate fechaActual = LocalDate.now();
        for (Compra c : compras) {
            if (c.getEstado().equals("PAGADO") && c.getFecha().getYear() == fechaActual.getYear()) {
                cantidadPaq[(c.getFecha().getMonth().getValue())-1] += c.getCantidadPasajeros();
            }
        }
        return ResponseEntity.ok(cantidadPaq);
    }

    @GetMapping(path = "/cantidadPaquetesTabla")
    public ResponseEntity<?> cantidadPaquetesTabla() {
        List<Compra> compras = compraservice.listar();
        List<PaqueteCantidad> cantCompras = new ArrayList<>();
        String[] meses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "setiembre", "octubre", "noviembre", "diciembre"};
        int cantidadPaq[] = new int[12];
        LocalDate fechaActual = LocalDate.now();
        for (Compra c : compras) {
            if (c.getEstado().equals("PAGADO") && c.getFecha().getYear() == fechaActual.getYear()) {
                cantidadPaq[(c.getFecha().getMonth().getValue())-1] += c.getCantidadPasajeros();
            }
        }

        for (int i = 0; i < cantidadPaq.length; i++) {
            PaqueteCantidad paqueteCantidad = new PaqueteCantidad();
            paqueteCantidad.setMes(meses[i]);
            paqueteCantidad.setCantidad(cantidadPaq[i]);
            cantCompras.add(paqueteCantidad);
        }

        return ResponseEntity.ok(cantCompras);
    }

    @GetMapping(path = "/totalPaquetes")
    public ResponseEntity<?> totalPaquetes() throws ParseException {
        String fecha1=""+ Calendar.getInstance().get(Calendar.YEAR)+"-01-01";
        String fecha2=""+ Calendar.getInstance().get(Calendar.YEAR)+"-12-31";
        log.info(fecha1 + "*******" + fecha2);
        List<Paquete> paquetes=paqueteService.listar();

        List total = new ArrayList();

        for(Paquete p:paquetes){
            Integer totalC = compraservice.comprasDePaquete(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2),
                    p.getIdPaq());

            Integer totalD = compraservice.devDePaquete(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2),
                    p.getIdPaq());

            if(totalC!=null){
                Object x[] = new Object[2];
                x[0] =p.getNombre();
                if(totalD!=null)
                    x[1] =(totalC-totalD);
                else{
                    x[1] =totalC;
                }
                total.add(x);
            }
        }
        return ResponseEntity.ok(total);
    }

    @GetMapping(path = "/totalPaquetesTabla")
    public ResponseEntity<?> totalPaquetesTabla() throws ParseException {
        String fecha1=""+ Calendar.getInstance().get(Calendar.YEAR)+"-01-01";
        String fecha2=""+ Calendar.getInstance().get(Calendar.YEAR)+"-12-31";
        List<Paquete> paquetes=paqueteService.listar();

        List total = new ArrayList();

        for(Paquete p:paquetes){
            Integer totalC = compraservice.comprasDePaquete(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2),
                    p.getIdPaq());

            Integer totalD = compraservice.devDePaquete(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2),
                    p.getIdPaq());

            PaqueteTotal paqueteTotal = new PaqueteTotal();

            if(totalC!=null){
                Object x[] = new Object[2];
                x[0] =p.getNombre();
                paqueteTotal.setNombre(p.getNombre());
                if(totalD!=null){
                    x[1] =(totalC-totalD);
                    paqueteTotal.setTotal(totalC-totalD);
                }

                else{
                    x[1] =totalC;
                    paqueteTotal.setTotal(totalC);

                }
                total.add(paqueteTotal);
            }
        }
        return ResponseEntity.ok(total);
    }


    @GetMapping(path = "/{mes}/totalPaquetesMes")
    public ResponseEntity<?> totalPaquetes(@PathVariable int mes) throws ParseException {
        int dia = 31;
        if(mes==2)
            dia=28;
        if(mes==4 || mes==6 || mes==11 || mes==9)
            dia=30;
        String mesT = "" + mes;
        if(mes < 10)
            mesT = "0" + mes;
        String fecha1=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-01";
        String fecha2=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-"+dia+"";

        List<Paquete> paquetes=paqueteService.listar();

        List total = new ArrayList();

        for(Paquete p:paquetes){
            Integer totalC = compraservice.comprasDePaquete(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2),
                    p.getIdPaq());

            Integer totalD = compraservice.devDePaquete(
                    LocalDate.parse(fecha1),
                    LocalDate.parse(fecha2),
                    p.getIdPaq());

            if(totalC!=null){
                Object x[] = new Object[2];
                x[0] = p.getNombre();
                if(totalD!=null)
                    x[1] = totalC-totalD;
                else{
                    x[1] = totalC;
                }
                total.add(x);
            }
        }

        return ResponseEntity.ok(total);
    }

    @GetMapping(path = "/{mes}/totalVendidoMes")
    public ResponseEntity<?> totalVendidoMes(@PathVariable int mes){
        int dia = 31;
        if(mes==2)
            dia=28;
        if(mes==4 || mes==6 || mes==11 || mes==9)
            dia=30;
        String mesT = "" + mes;
        if(mes < 10)
            mesT = "0" + mes;

        String fecha1=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-01";
        String fecha2=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-"+dia+"";

        LocalDate fechaLD1 = LocalDate.parse(fecha1);
        LocalDate fechaLD2 = LocalDate.parse(fecha2);

        TotalPaquete paqueteCantidad = new TotalPaquete();

        Integer totalDev = compraservice.totalPaquetesDev(fechaLD1,fechaLD2);
        Integer totalCompra = compraservice.totalPaquetes(fechaLD1,fechaLD2);
        if(totalDev== null){
            totalDev=0;
        }

        if(totalCompra== null){
            totalCompra=0;
        }


        paqueteCantidad.setTotal("total");
        paqueteCantidad.setVentaMes(totalCompra-totalDev);
        return ResponseEntity.ok(paqueteCantidad);
    }


    @GetMapping(path = "/{mes}/resumenTotal")
    public ResponseEntity<?> resumenTotal(@PathVariable int mes) throws ParseException {
        ResumenTotal resumenTotal = new ResumenTotal();

        int dia = 31;
        if(mes==2)
            dia=28;
        if(mes==4 || mes==6 || mes==11 || mes==9)
            dia=30;
        String mesT = "" + mes;
        if(mes < 10)
            mesT = "0" + mes;

        String fecha1=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-01";
        String fecha2=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-"+dia+"";

        LocalDate fechaLD1 = LocalDate.parse(fecha1);
        LocalDate fechaLD2 = LocalDate.parse(fecha2);

        List<Paquete> paqueteVendidos = compraservice.tourMasVendido(fechaLD1,fechaLD2);

        Integer totalDev = compraservice.devolucionesFecha(fechaLD1,fechaLD2);
        Integer totalCompra = compraservice.comprasAprobadasFecha(fechaLD1,fechaLD2);
        Integer totalTours = compraservice.totalTours(fechaLD1,fechaLD2);
        Paquete paqueteMasV;
        Paquete paqueteMenosV;

        if(totalCompra==null){
            totalCompra = 0;
        }
        if(totalDev==null){
            totalDev=0;
        }
        if(totalTours==null){
            totalTours=0;
        }

        if(paqueteVendidos.size()>0){
            paqueteMasV= paqueteVendidos.get(paqueteVendidos.size()-1);
            paqueteMenosV= paqueteVendidos.get(0);
            resumenTotal.setPaqueteMenosVendido(paqueteMenosV.getNombre());
            resumenTotal.setPaqueteMasVendido(paqueteMasV.getNombre());
            Integer devPaquete = compraservice.devDePaquete(fechaLD1,fechaLD2,paqueteMasV.getIdPaq());
            if(devPaquete==null) devPaquete=0;
            Integer vendidoPaquete = compraservice.comprasDePaquete(fechaLD1,fechaLD2,paqueteMasV.getIdPaq())-devPaquete;
            resumenTotal.setPorcentajeMasVendido((vendidoPaquete/(double)totalCompra)*100);
        }

        resumenTotal.setTotalDevoluciones(totalDev);
        resumenTotal.setTotalVentas(totalCompra);
        resumenTotal.setTotalTours(totalTours);

        return ResponseEntity.ok(resumenTotal);

    }

    @GetMapping(path = "/{mes}/totalPaquetesMesTabla")
    public ResponseEntity<?> totalPaquetesMesTabla(@PathVariable int mes) throws ParseException {
        int dia = 31;
        if(mes==2)
            dia=28;
        if(mes==4 || mes==6 || mes==11 || mes==9)
            dia=30;
        String mesT = "" + mes;
        if(mes < 10)
            mesT = "0" + mes;
        String fecha1=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-01";
        String fecha2=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-"+dia+"";

        List<Paquete> paquetes=paqueteService.listar();

        LocalDate fechaLD1 = LocalDate.parse(fecha1);
        LocalDate fechaLD2 = LocalDate.parse(fecha2);

        List total = new ArrayList();
        Integer totalDev = compraservice.totalPaquetesDev(fechaLD1,fechaLD2);
        Integer totalCompra = compraservice.totalPaquetes(fechaLD1,fechaLD2);
        if(totalDev== null){
            totalDev=0;
        }

        if(totalCompra== null){
            totalCompra=0;
        }

            Integer totalValorPaquetes =  totalCompra- totalDev;

        for(Paquete p:paquetes){
            Integer totalC = compraservice.comprasDePaquete( fechaLD1,fechaLD2,p.getIdPaq());
            Integer totalD = compraservice.devDePaquete(fechaLD1,fechaLD2,p.getIdPaq());
            Integer totalPasajeros = compraservice.pasajerosDePaquete(fechaLD1,fechaLD2,p.getIdPaq());

            PaqueteCantidadInfo paqueteTotal = new PaqueteCantidadInfo();
            if (totalPasajeros ==null) totalPasajeros = 0;
            paqueteTotal.setTotalPasajeros(totalPasajeros);
            if(totalC!=null){
                Tour t = p.tourCollection().iterator().next();
                String tipoTour = "";
                if(t.getEstado().equals("ACTIVO") && t.getPaquete().getEstado().equals("ACTIVO") && t.getFechaLlegada().getDay() != t.getFechaSalida().getDay())
                {
                    tipoTour="Estadia";
                }else{
                    tipoTour="Pasadia";
                }
                paqueteTotal.setTipoTour(tipoTour);
            }

            if(totalC!=null){
                Object x[] = new Object[2];
                x[0] = p.getNombre();
                paqueteTotal.setNombreTour(p.getNombre());
                if(totalD!=null){
                    x[1] = totalC-totalD;
                    paqueteTotal.setTotal(totalC-totalD);
                }


                else{
                    x[1] = totalC;
                    paqueteTotal.setTotal(totalC);
                }
                paqueteTotal.setPorcentajeVentas(((paqueteTotal.getTotal()/(double)totalValorPaquetes)*100));
                total.add(paqueteTotal);
            }
        }

        return ResponseEntity.ok(total);
    }

    @GetMapping(path = "/{mes}/totalReservasMesTabla")
    public ResponseEntity<?> totalReservasMesTabla(@PathVariable int mes){

        int dia = 31;
        if(mes==2)
            dia=28;
        if(mes==4 || mes==6 || mes==11 || mes==9)
            dia=30;
        String mesT = "" + mes;
        if(mes < 10)
            mesT = "0" + mes;

        String fecha1=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-01";
        String fecha2=""+ Calendar.getInstance().get(Calendar.YEAR)+"-"+mesT+"-"+dia+"";

        LocalDate fechaLD1 = LocalDate.parse(fecha1);
        LocalDate fechaLD2 = LocalDate.parse(fecha2);
        List<ReservaTabla> reservaTablas = new ArrayList<>();

        for(Compra c: compraservice.reservasFecha(fechaLD1,fechaLD2)){
            ReservaTabla reservaTabla = new ReservaTabla();
            reservaTabla.setIdReserva(c.getReserva().getIdReserva());
            reservaTabla.setEmail(c.getUsuario().getEmail());
            reservaTabla.setTotalCompra(c.getTotalCompra());
            reservaTabla.setFecha(c.getReserva().getFecha());
            reservaTabla.setCantidadPasajeros(c.getCantidadPasajeros());
            reservaTabla.setEstado(c.getReserva().getEstado());
            reservaTablas.add(reservaTabla);
        }

        return ResponseEntity.ok(reservaTablas);
    }

    //hacer uri del total de ventas en todo el año para la card de ventas (la verde)
    @GetMapping(path = "/totalVentasAnuales")
    public ResponseEntity<?> totalVentasAnuales(){
        LocalDate fechaActual = LocalDate.now();
        int totalVentas = 0;
        for(Compra c: compraservice.listar()) {
            if (c.getEstado().equals("PAGADO") && c.getFecha().getYear() == fechaActual.getYear())
                totalVentas += c.getTotalCompra();
        }
        for(Devolucion d: devolucionService.listar()){
            if(d.getFecha().getYear() == fechaActual.getYear())
                totalVentas -= d.getCantidad();
        }
        return ResponseEntity.ok(totalVentas);
    }
}
