/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.security.controlador;

import nexp.com.app.model.Mensaje;
import nexp.com.app.model.PasswordResetToken;
import nexp.com.app.security.dto.JwtDto;
import nexp.com.app.security.dto.LoginUsuario;
import nexp.com.app.security.dto.NuevoUsuario;
import nexp.com.app.security.model.Rol;
import nexp.com.app.security.model.Rol.RolNombre;
import nexp.com.app.security.jwt.JwtProvider;
import nexp.com.app.security.servicio.RolService;
import nexp.com.app.security.servicio.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.service.PasswordResetTokenService;
import nexp.com.app.service.imp.EmailServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

import nexp.com.app.security.model.Usuario;
/**
 *
 * @author santi
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
@Slf4j
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    EmailServiceImp emailServiceImp;

    @Value("${spring.mail.username}")
    String emailUsuarioEmisor;

    @Value("${spring.mail.password}")
    String clave;

    @Value("${urifrontend}")
    private String urlFrontend;

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) throws MessagingException {
        if(bindingResult.hasErrors())
            return new ResponseEntity("campos mal puestos o email inválido", HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(("ese email ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuario.setFecha(LocalDate.now());
        usuario.setImgUrl("https://yorktonrentals.com/wp-content/uploads/2017/06/usericon.png");
        usuario.setEstado(false);
        usuario.setConfirmationToken(UUID.randomUUID().toString());

        usuarioService.guardar(usuario);

        emailServiceImp.enviarEmail("Confirmación de cuenta",
                "<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Confirmación cuenta</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Hola "+usuario.getUsername()+", te has registrado en el aplicativo web de NorteXploradores, por favor confirma que eres tú ingresando al siguiente link:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding:0;\">\n" +
                "                        <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">\n" +
                "                          <tr>\n" +
                        "            <div style=\"margin: 2rem auto; width: 120px; background-color: #009045; padding: 8px; border-radius: 6px; \">\n" +
                        "                <a style=\"color: #ffffff;  text-align:center;text-decoration: none\" href=\""+urlFrontend+"login/confirmation/"+usuario.getConfirmationToken()+"\">Continuar</a>\n" +
                        "            </div>\n" +
                "                            <td style=\"width:20px;padding:0;font-size:0;line-height:0;\">&nbsp;</td>\n" +
                "                          </tr>\n" +
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
                "                              <a href=\"https://www.facebook.com/nortexploradores/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/fb_1.png\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                      <td style=\"padding:0;width:50%;\" align=\"left\">\n" +
                "                        <p style=\"margin:0;font-size:14px;line-height:16px;font-family:Arial,sans-serif;color:#ffffff;\">\n" +
                "                          &reg; NorteXploradores, 2021<br/><a href=\"https://front-nort-exploradores-2.vercel.app/inicio\" style=\"color:#ffffff;text-decoration:underline;\">!Gracias por elegirnos!</a>\n" +
                "                        </p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>",
                usuario.getEmail());

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(("campos mal puestos"), HttpStatus.BAD_REQUEST);

        Usuario usuario = usuarioService.getByEmail(loginUsuario.getEmail()).orElse(null);

        if(usuario == null){
            return new ResponseEntity(("El nombre de usuario no existe"), HttpStatus.NOT_FOUND);
        }

        if(!usuario.getEstado()){
            return new ResponseEntity(("El usuario se encuentra deshabilitado"), HttpStatus.NOT_FOUND);
        }

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

    @GetMapping("/solicitudPassword/{email}")
    public ResponseEntity<?> recuperarPassword(@PathVariable String email) throws MessagingException {
        Usuario u = usuarioService.findByEmail(email);

        if(u==null)
            return new ResponseEntity(new Mensaje("El email no existe"), HttpStatus.NOT_FOUND);

        if(u.passwordResetTokenCollection().size()>0){
            PasswordResetToken passwordResetToken = u.passwordResetTokenCollection().iterator().next();
            if(passwordResetToken.getFechaExpiracion().before(new Date())){
                passwordResetTokenService.eliminarByToken(passwordResetToken.getToken());
            }else{
                return new ResponseEntity(new Mensaje("Ya hay una solicitud de reestablecimiento pendiente"), HttpStatus.BAD_REQUEST);

            }
        }

        PasswordResetToken passwordResetToken = new PasswordResetToken(u);
        passwordResetTokenService.guardar(passwordResetToken);


        String cuerpo2="<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Cambio de contraseña</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Has solicitado cambiar tu contraseña, por favor ingresa al siguiente link:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding:0;\">\n" +
                "                        <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">\n" +
                "                          <tr>\n" +
                "                            <td style=\"width:260px;padding:0;vertical-align:top;color:#153643;\">\n" +
                "            <div style=\"margin: 2rem auto; width: 120px; background-color: #009045; padding: 8px; border-radius: 6px; \">\n" +
                "                <a style=\"color: #ffffff; text-align:center; text-decoration: none\" href=\""+urlFrontend+"password-reset/confirmation/"+passwordResetToken.getToken()+"\">Continuar</a>\n" +
                "            </div>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
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
                "                              <a href=\"https://www.facebook.com/nortexploradores/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/fb_1.png\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                      <td style=\"padding:0;width:50%;\" align=\"left\">\n" +
                "                        <p style=\"margin:0;font-size:14px;line-height:16px;font-family:Arial,sans-serif;color:#ffffff;\">\n" +
                "                          &reg; NorteXploradores, 2021<br/><a href=\"https://front-nort-exploradores-2.vercel.app/inicio\" style=\"color:#ffffff;text-decoration:underline;\">!Gracias por elegirnos!</a>\n" +
                "                        </p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>";

        emailServiceImp.enviarEmail("Cambio de contraseña", cuerpo2, u.getEmail());

        return ResponseEntity.ok(new Mensaje("Mensaje de recuperación enviado al correo"));
    }

    @GetMapping("/recuperar/{token}") //petición que recibe el backend de parte del frontend, recordar cambiar el link de la linea 131 a un URL del frontend
    public ResponseEntity<?>confirmarRecuperarPassword(@PathVariable String token){

        PasswordResetToken passwordResetToken = passwordResetTokenService.buscarToken(token);

        if(passwordResetToken == null)
            return new ResponseEntity(new Mensaje("El token no existe"), HttpStatus.NOT_FOUND);

        if(passwordResetToken.getFechaExpiracion().before(new Date()))
            return new ResponseEntity(new Mensaje("El token ha expirado"), HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(new Mensaje("Contraseña cambiada con exito"));
    }

    @PostMapping("/recuperar/{token}")
    public ResponseEntity<?>cambiarPassword(@PathVariable String token, @RequestBody LoginUsuario loginUsuario) throws MessagingException {

        PasswordResetToken passwordResetToken = passwordResetTokenService.buscarToken(token);
        Usuario uToken = usuarioService.findByResetPassword(token);

        if(passwordResetToken == null)
            return new ResponseEntity(new Mensaje("El token no existe"), HttpStatus.NOT_FOUND);

        if(passwordResetToken.getFechaExpiracion().before(new Date()))
            return new ResponseEntity(new Mensaje("El token ha expirado"), HttpStatus.BAD_REQUEST);

        loginUsuario.setEmail(passwordResetToken.getUsuario().getEmail());

        Usuario u = usuarioService.findByEmail(loginUsuario.getEmail());

        if(u==null){
            return new ResponseEntity(new Mensaje("El correo no existe"), HttpStatus.BAD_REQUEST);
        }


        if(uToken==null){
            return new ResponseEntity(new Mensaje("El token no está asociado a ningun usuario"), HttpStatus.BAD_REQUEST);
        }

        if(!u.getEmail().equals(uToken.getEmail())){
            return new ResponseEntity(new Mensaje("El token se encuentra asociado a otro usuario"), HttpStatus.BAD_REQUEST);
        }

        log.info(loginUsuario.getPassword());
        log.info(passwordResetToken.getUsuario().getEmail());
        log.info(uToken.getEmail());

        u.setPassword(passwordEncoder.encode(loginUsuario.getPassword()));
        usuarioService.guardar(u);

        String cuerpo2="<table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Cambio de contraseña</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Hola, <b>"+u.getUsername()+"</b>, se ha cambiado tu contraseña en el sistema. </p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding:0;\">\n" +
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
                "                              <a href=\"https://www.facebook.com/nortexploradores/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/fb_1.png\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                      <td style=\"padding:0;width:50%;\" align=\"left\">\n" +
                "                        <p style=\"margin:0;font-size:14px;line-height:16px;font-family:Arial,sans-serif;color:#ffffff;\">\n" +
                "                          &reg; NorteXploradores, 2021<br/><a href=\"https://front-nort-exploradores-2.vercel.app/inicio\" style=\"color:#ffffff;text-decoration:underline;\">!Gracias por elegirnos!</a>\n" +
                "                        </p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </table>";

        emailServiceImp.enviarEmail("Contraseña actualizada", cuerpo2, u.getEmail());
        passwordResetTokenService.eliminarByToken(passwordResetToken.getToken());


        return ResponseEntity.ok(new Mensaje("Contraseña cambiada con exito"));
    }

    @GetMapping("/confirmacion/{token}")
    public ResponseEntity<?> confirmarToken(@PathVariable String token){
        Usuario usuario = usuarioService.findByConfirmationToken(token);

        if(usuario==null){
            return new ResponseEntity(new Mensaje("Error, Token no encontrado"), HttpStatus.NOT_FOUND);
        }

        usuario.setEstado(true);
        usuarioService.guardar(usuario);

        return ResponseEntity.ok(new Mensaje("Usuario verificado correctamente"));
    }
}
