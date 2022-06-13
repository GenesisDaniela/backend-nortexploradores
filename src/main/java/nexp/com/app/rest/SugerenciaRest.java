/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.*;
import nexp.com.app.negocio.response.CorreoSugerencia;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.NotificacionService;
import nexp.com.app.service.SugerenciaService;
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
import java.util.Date;
import java.util.List;

/**
 *
 * @author GenesisDanielaVJ
 * clase no finalizada debido a cambios en la bd, pendiente
 */
@RestController
@RequestMapping("/sugerencia")
@CrossOrigin(origins = "*")
public class SugerenciaRest {
    
    @Autowired
    SugerenciaService sser;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SugerenciaService sugerenciaService;

    @Autowired
    NotificacionService nser;

    @Value("${spring.mail.username}")
    String emailUsuarioEmisor;

    @Value("${spring.mail.password}")
    String clave;

    @Autowired
    EmailServiceImp emailServiceImp;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Sugerencia s, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Date fecha = new Date();
        s.setFecha(fecha);
        Usuario usuario = usuarioService.encontrar(s.getUsuario().getId_Usuario()).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "La empresa no existe"), HttpStatus.NOT_FOUND);
        }
        s.setUsuario(usuario);
        sser.guardar(s);
        Notificacion notificacion = new Notificacion();
        notificacion.setDescripcion("Has recibo una sugerencia de: " + usuario.getUsername());
        notificacion.setUsuario(usuario);
        notificacion.setEstado((short)0);
        notificacion.setFecha(new Date());
        notificacion.setSugerencia(s);
        notificacion.setSugerencia(s);

        nser.guardar(notificacion);
        return ResponseEntity.ok(s);
    }

    @PostMapping(path = "/responder")
    public ResponseEntity<?> responderSugerencia(@RequestBody CorreoSugerencia correoSugerencia) throws MessagingException {
        Sugerencia sugerencia =sugerenciaService.encontrar(correoSugerencia.getIdSugerencia()).get();

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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Sugerencia realizada</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">El administrador de NorteXploradores respondio tu sugerencia:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                    <td style=\"padding:0;\">\n" +
                "                        <table class=\"simple-style\" border='1'>\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th scope=\"col\">Tu sugerencia</th>\n" +
                "                                    <th scope=\"col\">Respuesta del Administrador</th>\n" +
                "                                    <th scope=\"col\">Fecha de sugerencia</th>\n" +

                "                                </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>"+sugerencia.getDescripcion()+"</td>\n" +
                "                                    <td>"+correoSugerencia.getRespsugerencia()+"</td>\n" +
                "                                    <td>"+sugerencia.getFecha()+"</td>\n" +
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
        emailServiceImp.enviarEmail("Respuesta a sugerencia", cuerpo,correoSugerencia.getCorreo());

        return ResponseEntity.ok(sser.listar());
    }

    @GetMapping
    public ResponseEntity<List<Sugerencia>> getSugerencia() {
        return ResponseEntity.ok(sser.listar());
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Sugerencia s, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioService.encontrar(s.getUsuario().getId_Usuario()).orElse(null);
        if(usuario ==null){
            return new ResponseEntity<ObjectError>(new ObjectError("id", "Seguro no existe"), HttpStatus.NOT_FOUND);
        }
        sser.guardar(s);
        return ResponseEntity.ok(sser.encontrar(s.getIdSug()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarSugerencia(@PathVariable int id) {
        Sugerencia sugerencia = sser.encontrar(id).orElse(null);
        if (sugerencia == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe seguro"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sugerencia);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminarSugerencia(@PathVariable int id) {
        Sugerencia s = sser.encontrar(id).orElse(null);
        if(s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe seguro"), HttpStatus.NOT_FOUND);
        }
        sser.eliminar(id);
        return ResponseEntity.ok(s);
}
}
