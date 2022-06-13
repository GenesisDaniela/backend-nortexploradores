/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Compra;
import nexp.com.app.model.Descuento;
import nexp.com.app.model.Devolucion;
import nexp.com.app.model.Empresa;
import nexp.com.app.service.DescuentoService;
import nexp.com.app.service.DevolucionService;
import nexp.com.app.service.imp.EmailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author GenesisDanielaVJ
 */
@RestController
@RequestMapping("/devolucion")
@CrossOrigin(origins = "*")
@Slf4j
public class DevolucionRest {

    @Autowired
    DevolucionService dser;

    @Value("${spring.mail.username}")
    String emailUsuarioEmisor;

    @Value("${spring.mail.password}")
    String clave;

    @Autowired
    EmailServiceImp emailServiceImp;
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Devolucion d, BindingResult br) throws MessagingException {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Compra compra = d.getCompra();
        if(compra ==  null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","la compra no existe"), HttpStatus.NOT_FOUND);
        }

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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Devolución</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Se ha registrado una devolución para una compra de un paquete que has realizado, aqui están tus datos:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                    <td style=\"padding:0;\">\n" +
                "                        <table class=\"simple-style\" border='1'>\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th scope=\"col\">#Compra</th>\n" +
                "                                    <th scope=\"col\">Destino</th>\n" +
                "                                    <th scope=\"col\">Total compra</th>\n" +
                "                                    <th scope=\"col\">Total devolución</th>\n" +
                "                                    <th scope=\"col\">Fecha</th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>"+compra.getIdCompra()+"</td>\n" +
                "                                    <td>"+compra.getTour().getPaquete().getMunicipio().getNombre()+"</td>\n" +
                "                                    <td>"+compra.getTotalCompra()+"</td>\n" +
                "                                    <td>"+d.getCantidad()+"</td>\n" +
                "                                    <td>"+d.getFecha()+"</td>\n" +
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
        emailServiceImp.enviarEmail("Devolución NorteXploradores", cuerpo,compra.getUsuario().getEmail());

        d.setCompra(compra);
        dser.guardar(d);
        return ResponseEntity.ok(d);
    }

    @GetMapping
    public ResponseEntity<List<Devolucion>> getDevolucion() {
        return ResponseEntity.ok(dser.listar());
    }

    @GetMapping(path ="/listar")
    public ResponseEntity<List<Devolucion>> listarDevolucion() {
        return ResponseEntity.ok(dser.listar());
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Devolucion d, BindingResult br) throws MessagingException {
        if(br.hasErrors()){
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Devolucion dev = dser.encontrar(d.getIdDevolucion()).orElse(null);
        if(dev == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","la devolucion no existe"), HttpStatus.NOT_FOUND);
        }
        dev.setEstado(false);
        Compra compra = dev.getCompra();
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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Respuesta devolución</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">El administrador ha respondido a tu devolucion, esta es la información:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                    <td style=\"padding:0;\">\n" +
                "                        <table class=\"simple-style\" border='1'>\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th scope=\"col\">#Compra</th>\n" +
                "                                    <th scope=\"col\">Destino</th>\n" +
                "                                    <th scope=\"col\">Total compra</th>\n" +
                "                                    <th scope=\"col\">Total devolución</th>\n" +
                "                                    <th scope=\"col\">Fecha</th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>"+compra.getIdCompra()+"</td>\n" +
                "                                    <td>"+compra.getTour().getPaquete().getMunicipio().getNombre()+"</td>\n" +
                "                                    <td>"+compra.getTotalCompra()+"</td>\n" +
                "                                    <td>"+d.getCantidad()+"</td>\n" +
                "                                    <td>"+d.getFecha()+"</td>\n" +
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
        emailServiceImp.enviarEmail("Devolución NorteXploradores compra #"+compra.getIdCompra(), cuerpo, compra.getUsuario().getEmail());

        dser.guardar(d);
        return ResponseEntity.ok(dser.encontrar(d.getIdDevolucion()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Devolucion> eliminarDevolucion(@PathVariable int id) {
        Devolucion d = dser.encontrar(id).orElse(null);
        dser.eliminar(id);
        return ResponseEntity.ok(d);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarDevolucion(@PathVariable int id) {
        Devolucion d = dser.encontrar(id).orElse(null);
        if (d == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe la devolucion"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(d);
    }

    
}
