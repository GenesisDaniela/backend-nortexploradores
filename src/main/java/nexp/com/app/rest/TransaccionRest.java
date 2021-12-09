/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;


import nexp.com.app.model.*;
import nexp.com.app.negocio.EmailService;
import nexp.com.app.negocio.NorteXploradores;

import java.sql.Time;
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
        pay.setDate(new Date());
        pay.setDescription(body.get("description")+"");
//        pay.set
        long idCompra = Long.parseLong(body.get("extra1"));
        Compra compra = compraService.encontrar(idCompra).orElse(null);
        pay.setReferenceSale(compra);
        pay.setCurrency(body.get("currency")+"");
        pay.setAttempts(Short.parseShort(body.get("attempts")));
        pay.setResponseMessagePol(body.get("response_message_pol"));
        pay.setTransactionId(body.get("transaction_id"));
        pay.setValue(Long.parseLong(body.get("value").split("\\.")[0]));
        pay.setShippingCountry(body.get("shipping_country"));
        pay.setTax(body.get("tax"));
        pay.setEmailBuyer(body.get("email_buyer")+"");
        pay.setPaymentMethodName(body.get("payment_method_name")+"");
        pay.setPaymentMethodType(Short.parseShort(body.get("payment_method")));
        pay.setPaymentMethodId(Short.parseShort(body.get("payment_method_id")));
        Usuario usuario = compra.getUsuario();

//      Compra Fallida
        if(!pay.getResponseMessagePol().equals("APPROVED") && !pay.getResponseMessagePol().equals("PENDING")){

            EmailService email=new EmailService(emailUsuarioEmisor, clave);
            String cuerpo=" <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
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
                    "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Compra fallida</h1>\n" +
                    "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">\n" +
                    "                        Se ha registrado una compra fallida para el paquete turistico destino " +compra.getTour().getPaquete().getMunicipio().getNombre()+
                    "                         con fecha de salida de "+compra.getTour().getFechaSalida()+" y fecha de llegada: "+compra.getTour().getFechaLlegada()+"</p>\n" +
                    "                        <center>\n" +
                    "                            <img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Spec_indicator_fail.svg/1200px-Spec_indicator_fail.svg.png\" alt=\"\" style=\"display: inline;width: 160px;height: auto;\">\n" +
                    "                        </center>\n" +
                    "                    </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"padding:0;\">\n" +
                    "                        <table class=\"simple-style\" border='1'>\n" +
                    "                            <thead>\n" +
                    "                                <tr>\n" +
                    "                                    <th scope=\"col\">#Referencia</th>\n" +
                    "                                    <th scope=\"col\">#Transacción</th>\n" +
                    "                                    <th scope=\"col\">Total</th>\n" +
                    "                                    <th scope=\"col\">Estado</th>\n" +
                    "                                </tr>\n" +
                    "                            </thead>\n" +
                    "                            <tbody>\n" +
                    "                                <tr>\n" +
                    "                                    <td>"+compra.getIdCompra()+"</td>\n" +
                    "                                    <td>"+pay.getTransactionId()+"</td>\n" +
                    "                                    <td>"+pay.getValue()+"</td>\n" +
                    "                                    <td>"+pay.getResponseMessagePol()+"</td>\n" +
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

            email.enviarEmail(usuario.getEmail(), "Compra fallida", cuerpo);


            pser.guardar(pay);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }

//      Es una compra o pago total 100%
        if(pay.getResponseMessagePol().equals("APPROVED") && pay.getValue() == (long)compra.getTotalCompra()){ //Se pagó el total y fue aprobada
            compra.setEstado("PAGADO");
            Tour t = compra.getTour();
            t.setCantCupos(t.getCantCupos()-compra.getCantidadPasajeros());
            Notificacion notificacion = new Notificacion();
            notificacion.setFecha(new Date());
            notificacion.setDescripcion("Actualmente quedan "+t.getCantCupos()+" disponibles del Tour destino "+t.getPaquete().getMunicipio().getNombre());
            tourService.guardar(t);
            notificacionService.guardar(notificacion);
            pser.guardar(pay);


            EmailService email=new EmailService(emailUsuarioEmisor, clave);

            String cuerpo=" <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
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
                    "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Pago total</h1>\n" +
                    "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">\n" +
                    "                              ¡Hola "+usuario.getUsername()+
                    ", has realizado el pago total del paquete turistico destino " +
                    ""+compra.getTour().getPaquete().getMunicipio().getNombre()+", gracias por viajar con nosotros!"+compra.getTotalCompra()+", esta es la informacion de tu compra:" +
                    "                    </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"padding:0;\">\n" +
                    "                        <table class=\"simple-style\" border='1'>\n" +
                    "                            <thead>\n" +
                    "                                <tr>\n" +
                    "                                    <th scope=\"col\">#Referencia</th>\n" +
                    "                                    <th scope=\"col\">#Transaccion</th>\n" +
                    "                                    <th scope=\"col\">Total</th>\n" +
                    "                                    <th scope=\"col\">Destino</th>\n" +
                    "                                    <th scope=\"col\">Estado</th>\n" +
                    "                                </tr>\n" +
                    "                            </thead>\n" +
                    "                            <tbody>\n" +
                    "                                <tr>\n" +
                    "                                    <td>"+compra.getIdCompra()+"</td>\n" +
                    "                                    <td>"+pay.getTransactionId()+"</td>\n" +
                    "                                    <td>"+pay.getValue()+"</td>\n" +
                    "                                    <td>"+compra.getTour().getPaquete().getMunicipio().getNombre()+"</td>\n" +
                    "                                    <td>"+pay.getResponseMessagePol()+"</td>\n" +
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

            email.enviarEmail(usuario.getEmail(), "Pago total registrado", cuerpo);

            return new ResponseEntity<>(body, HttpStatus.OK);
        }

//      Es una reserva;
//      Si la compra actual está aprobada y la transaccion anterior fue aprobada
        boolean estaAprobada =false;

        List<Transaccionp> transaccionps =(List)compra.transaccionpCollection();
        Tour t = compra.getTour();
        if(transaccionps.size()>0 && pay.getResponseMessagePol().equals("APPROVED")){
            for (Transaccionp transaccionp : transaccionps) { //Recorro todas las transacciones, y si alguna esta aprobada entonces se que ya tiene una segunda transaccion aprobada
                if (transaccionp.getResponseMessagePol().equals("APPROVED") && !transaccionp.getTransactionId().equals(pay.getTransactionId())) {
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

                EmailService email=new EmailService(emailUsuarioEmisor, clave);

                String cuerpo=" <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
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
                        "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Segundo pago parcial</h1>\n" +
                        "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">\n" +
                        "                              ¡Hola "+usuario.getUsername()+
                        ", has realizado el pago total del paquete turistico destino " +
                        ""+compra.getTour().getPaquete().getMunicipio().getNombre()+", gracias por viajar con nosotros!"+compra.getTotalCompra()+", esta es la informacion de tu compra:" +
                        "                    </td>\n" +
                        "                    </tr>\n" +
                        "                    <tr>\n" +
                        "                      <td style=\"padding:0;\">\n" +
                        "                        <table class=\"simple-style\" border='1'>\n" +
                        "                            <thead>\n" +
                        "                                <tr>\n" +
                        "                                    <th scope=\"col\">#Referencia</th>\n" +
                        "                                    <th scope=\"col\">#Transaccion</th>\n" +
                        "                                    <th scope=\"col\">Total</th>\n" +
                        "                                    <th scope=\"col\">Destino</th>\n" +
                        "                                    <th scope=\"col\">Estado</th>\n" +
                        "                                </tr>\n" +
                        "                            </thead>\n" +
                        "                            <tbody>\n" +
                        "                                <tr>\n" +
                        "                                    <td>"+compra.getIdCompra()+"</td>\n" +
                        "                                    <td>"+pay.getTransactionId()+"</td>\n" +
                        "                                    <td>"+pay.getValue()+"</td>\n" +
                        "                                    <td>"+compra.getTour().getPaquete().getMunicipio().getNombre()+"</td>\n" +
                        "                                    <td>"+pay.getResponseMessagePol()+"</td>\n" +
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

                email.enviarEmail(usuario.getEmail(), "Segundo pago registrado", cuerpo);

            }

            pser.guardar(pay);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
        else{
            log.info("Primer pago, parcial");

            t.setCantCupos(t.getCantCupos()-compra.getCantidadPasajeros());
            compra.setEstado("PAGO_PARCIAL");
            Reserva reserva = compra.getReserva();
            reserva.setEstado("PAGO_PARCIAL");
            reservaService.guardar(reserva);
            EmailService email=new EmailService(emailUsuarioEmisor, clave);

            String cuerpo=" <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
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
                    "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Pago parcial</h1>\n" +
                    "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">\n" +
                    "                               Hola "+usuario.getUsername()+
                    ", has realizado el pago parcial del paquete turistico destino " +
                    ""+compra.getTour().getPaquete().getMunicipio().getNombre()+", recuerda pagar el otro 50% restante" +
                    " por un total de: $"+compra.getTotalCompra()+", esta es la informacion de tu compra:" +
                    "                    </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"padding:0;\">\n" +
                    "                        <table class=\"simple-style\" border='1'>\n" +
                    "                            <thead>\n" +
                    "                                <tr>\n" +
                    "                                    <th scope=\"col\">#Referencia</th>\n" +
                    "                                    <th scope=\"col\">#Transaccion</th>\n" +
                    "                                    <th scope=\"col\">Total</th>\n" +
                    "                                    <th scope=\"col\">Destino</th>\n" +
                    "                                    <th scope=\"col\">Estado</th>\n" +
                    "                                </tr>\n" +
                    "                            </thead>\n" +
                    "                            <tbody>\n" +
                    "                                <tr>\n" +
                    "                                    <td>"+compra.getIdCompra()+"</td>\n" +
                    "                                    <td>"+pay.getTransactionId()+"</td>\n" +
                    "                                    <td>"+pay.getValue()+"</td>\n" +
                    "                                    <td>"+compra.getTour().getPaquete().getNombre()+"</td>\n" +
                    "                                    <td>"+pay.getResponseMessagePol()+"</td>\n" +
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

            email.enviarEmail(usuario.getEmail(), "Pago pendiente", cuerpo);
            tourService.guardar(t);

        }

        pser.guardar(pay);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
