package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.*;
import nexp.com.app.negocio.EmailService;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/solicitudtour")
@CrossOrigin(origins = "*")
@Slf4j
public class SolicitudTourRest {

    @Autowired
    SolicitudTourService spaqser;

    @Autowired
    UsuarioService user;

    @Autowired
    PaqueteService pser;

    @Autowired
    NotificacionService nser;

    @Autowired
    TourService tourService;

    @Autowired
    MunicipioService municipioService;

    @Autowired
    PaqueteService paqueteService;

    @Autowired
    AlojamientoService alojamientoService;

    @Autowired
    NotificacionService notificacionService;



    @Value("${spring.mail.username}")
    String emailUsuarioEmisor;

    @Value("${spring.mail.password}")
    String clave;

    @GetMapping(path = "/total")
    public ResponseEntity<Integer> cantidadSolicitudes() {
        int total = 0;
      for(SolicitudTour solicitudTour: spaqser.listar()) {
          if(!solicitudTour.getEstado().equals("ACEPTADO") && !solicitudTour.getEstado().equals("RECHAZADO")){
              total++;
          }

      }
      return ResponseEntity.ok(total);
    }


    @PostMapping()
    public ResponseEntity<?> guardar(@RequestBody @Valid SolicitudTour solicitudTour, BindingResult br){

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Tour tour = solicitudTour.getTour();
        if (tour == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El tour no existe"), HttpStatus.NOT_FOUND);
        }
        tour.setEstado("PENDIENTE");
        tourService.guardar(tour);

        Alojamiento aloj = solicitudTour.getAlojamiento();
        if(aloj != null){
            Alojamiento alojamiento = alojamientoService.encontrar(aloj.getIdAlojamiento()).orElse(null);
            if (alojamiento == null) {
                return new ResponseEntity<ObjectError>(new ObjectError("id", "El alojamiento no existe"), HttpStatus.NOT_FOUND);
            }
            solicitudTour.setAlojamiento(alojamiento);
        }
        Municipio muni = municipioService.encontrar(solicitudTour.getMunicipio().getIdMuni()).orElse(null);
        if (muni == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El municipio no existe"), HttpStatus.NOT_FOUND);
        }
        solicitudTour.setMunicipio(muni);
        solicitudTour.setFecha(new Date());
        solicitudTour.setEstado("PENDIENTE");
        solicitudTour.setTour(tour);
        spaqser.guardar(solicitudTour);

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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Viaje personalizado</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Has enviado un viaje personalizado, deberás esperar mientras el administrador responde tu solicitud, esta es la información que enviaste:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                    <td style=\"padding:0;\">\n" +
                "                        <table class=\"simple-style\" border='1'>\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th scope=\"col\">Destino</th>\n" +
                "                                    <th scope=\"col\">Descripción</th>\n" +
                "                                    <th scope=\"col\">Fecha Salida</th>\n" +
                "                                    <th scope=\"col\">Fecha llegada</th>\n" +
                "                                    <th scope=\"col\">Cantidad pasajeros</th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>"+solicitudTour.getMunicipio().getNombre()+"</td>\n" +
                "                                    <td>"+solicitudTour.getDescripcion()+"</td>\n" +
                "                                    <td>"+solicitudTour.getTour().getFechaSalida()+"</td>\n" +
                "                                    <td>"+solicitudTour.getTour().getFechaLlegada()+"</td>\n" +
                "                                    <td>"+solicitudTour.getTour().getCantCupos()+"</td>\n" +
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
        EmailService email=new EmailService(emailUsuarioEmisor, clave);
        email.enviarEmail(solicitudTour.getUsuario().getEmail(), "Solicitud de viaje", cuerpo);


        Notificacion notificacion = new Notificacion();
        notificacion.setDescripcion("Has recibo una solicitud de paquete personalizado de: " + solicitudTour.getUsuario().getUsername());
        notificacion.setUsuario(solicitudTour.getUsuario());
        notificacion.setEstado((short)0);
        notificacion.setFecha(solicitudTour.getFecha());
        notificacion.setSolicitudTour(solicitudTour);
        nser.guardar(notificacion);

        return ResponseEntity.ok(solicitudTour);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudTour>> getSolicitudPaquete() {
        return ResponseEntity.ok(spaqser.listar());
    }

    @GetMapping(path = "/{id}/rechazar")
    public ResponseEntity<?> rechazarSolicitud(@PathVariable int id) {
        SolicitudTour s = spaqser.encontrar(id).orElse(null);
        Tour t = s.getTour();
        List<Notificacion> notificacions =(List) s.notificacionCollection();
        notificacionService.eliminar(notificacions.get(0).getIdNotificacion());
        spaqser.eliminar(s.getIdSolicitud());
        tourService.eliminar(t.getIdTour());

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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Solicitud rechazada</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">El administrador rechazó tu solicitud de paquete personalizado, esta es la información que enviaste:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                    <td style=\"padding:0;\">\n" +
                "                        <table class=\"simple-style\" border='1'>\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th scope=\"col\">Destino</th>\n" +
                "                                    <th scope=\"col\">Descripción</th>\n" +
                "                                    <th scope=\"col\">Fecha Salida</th>\n" +
                "                                    <th scope=\"col\">Fecha llegada</th>\n" +
                "                                    <th scope=\"col\">Cantidad pasajeros</th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>"+s.getMunicipio().getNombre()+"</td>\n" +
                "                                    <td>"+s.getDescripcion()+"</td>\n" +
                "                                    <td>"+s.getTour().getFechaSalida()+"</td>\n" +
                "                                    <td>"+s.getTour().getFechaLlegada()+"</td>\n" +
                "                                    <td>"+s.getTour().getCantCupos()+"</td>\n" +
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
        EmailService email=new EmailService(emailUsuarioEmisor, clave);
        email.enviarEmail(s.getUsuario().getEmail(), "Solicitud rechazada", cuerpo);
        return ResponseEntity.ok("Solicitud eliminada");

    }

    @PutMapping()
    public ResponseEntity<?> editar(@RequestBody @Valid SolicitudTour solicitud, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        SolicitudTour s = spaqser.encontrar(solicitud.getIdSolicitud()).orElse(null);
        if (s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La solicitud no existe"), HttpStatus.NOT_FOUND);
        }
        spaqser.guardar(solicitud);
    return ResponseEntity.ok(spaqser.encontrar(solicitud.getIdSolicitud()));
    }

    @PostMapping(path = "/aceptar")
    public ResponseEntity<?> aceptarSolicitudTour(@RequestBody SolicitudTour solicitudTour) { // aqui deberia enviar la solicitud completa guardarla y aceptarla aqui mismo

        Tour tour = solicitudTour.getTour();

        if (tour == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El tour no existe"), HttpStatus.NOT_FOUND);
        }
        Municipio municipio = municipioService.encontrar(solicitudTour.getMunicipio().getIdMuni()).get();
        solicitudTour.setMunicipio(municipio);
        tour.setEstado("PERSONALIZADO");

        tourService.guardar(tour);
        solicitudTour.setTour(tour);
        solicitudTour.setFecha(new Date());
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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Solicitud Aceptada</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">El administrador aceptó tu solicitud de paquete personalizado, esta es la información que enviaste:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                    <td style=\"padding:0;\">\n" +
                "                        <table class=\"simple-style\" border='1'>\n" +
                "                            <thead>\n" +
                "                                <tr>\n" +
                "                                    <th scope=\"col\">Destino</th>\n" +
                "                                    <th scope=\"col\">Descripción</th>\n" +
                "                                    <th scope=\"col\">Fecha Salida</th>\n" +
                "                                    <th scope=\"col\">Fecha llegada</th>\n" +
                "                                    <th scope=\"col\">Cantidad pasajeros</th>\n" +
                "                                </tr>\n" +
                "                            </thead>\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>"+solicitudTour.getMunicipio().getNombre()+"</td>\n" +
                "                                    <td>"+solicitudTour.getDescripcion()+"</td>\n" +
                "                                    <td>"+solicitudTour.getTour().getFechaSalida()+"</td>\n" +
                "                                    <td>"+solicitudTour.getTour().getFechaLlegada()+"</td>\n" +
                "                                    <td>"+solicitudTour.getTour().getCantCupos()+"</td>\n" +
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
        EmailService email=new EmailService(emailUsuarioEmisor, clave);
        email.enviarEmail(solicitudTour.getUsuario().getEmail(), "Solicitud aceptada", cuerpo);
        solicitudTour.setEstado("ACEPTADO");
        spaqser.guardar(solicitudTour);
    return ResponseEntity.ok(solicitudTour);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> rechazarSolicitudTour(@PathVariable int id) {
        SolicitudTour s = spaqser.encontrar(id).orElse(null);
        if (s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La solicitud no existe"), HttpStatus.NOT_FOUND);
        }
        tourService.eliminar(s.getTour().getIdTour());
        pser.eliminar(s.getTour().getPaquete().getIdPaq());
        spaqser.eliminar(id);
        return ResponseEntity.ok(s);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarSolicitudPaquete(@PathVariable int id){
        SolicitudTour s = spaqser.encontrar(id).orElse(null);
        if (s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La solicitud no existe"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(s);
    }


}
