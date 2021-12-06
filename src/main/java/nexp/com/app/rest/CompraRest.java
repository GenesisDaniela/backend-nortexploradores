/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.*;
import nexp.com.app.negocio.EmailService;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.*;

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
        for(Compra c: usuario.compraCollection()){
            if(c.getTour().getIdTour() == t.getIdTour() && c.getEstado().equals("PAGADO")|| c.getEstado().equals("PAGO_PARCIAL")){
                return new ResponseEntity("No puedes comprar un mismo tour dos veces", HttpStatus.BAD_REQUEST);
            }
        }

        Reserva reserva = new Reserva();
        reserva.setFecha(new Date());
        reserva.setEstado("PENDIENTE");
        reservaService.guardar(reserva);
        compra.setFecha(new Date());
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

}
