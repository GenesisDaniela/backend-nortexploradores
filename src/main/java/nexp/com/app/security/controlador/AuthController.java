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
        usuario.setEstado(true);
        usuario.setConfirmationToken(UUID.randomUUID().toString());

        usuarioService.guardar(usuario);



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
                "                        <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">Bienvenido al aplicativo web de NorteXploradores</h1>\n" +
                "                        <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Te has registrado en el aplicativo web de NorteXploradores, estos son tus datos de inicio de sesión:</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                      <td style=\"padding:0;\">\n" +
                "                        <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">\n" +
                "                          <tr>\n" +
                "                            <td style=\"width:260px;padding:0;vertical-align:top;color:#153643;\">\n" +
                "                              <p style=\"margin:0 0 25px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\"><img src=\"https://uybor.uz/borless/uybor/img/user-images/user_no_photo_300x300.png\" alt=\"\" width=\"260\" style=\"width: 160px;height:auto;display:block;\" /></p>\n" +
                "                              <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Usuario:</p>\n" +
                "                              <p style=\"margin:0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">"+usuario.getUsername()+"</p>\n" +
                "                            </td>\n" +
                "                            <td style=\"width:20px;padding:0;font-size:0;line-height:0;\">&nbsp;</td>\n" +
                "                            <td style=\"width:260px;padding:0;vertical-align:top;color:#153643;\">\n" +
                "                              <p style=\"margin:0 0 25px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\"><img src=\"https://cdn.pixabay.com/photo/2019/10/24/08/23/lock-4573711_960_720.png\" alt=\"\" width=\"260\" style=\"width: 220px;height:auto;display:block;\" /></p>\n" +
                "                              <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">Contraseña: </p>\n" +
                "                              <p style=\"margin:0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">"+nuevoUsuario.getPassword()+"</p>\n" +
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
        emailServiceImp.enviarEmail("Registro de sesión en el aplicativo web NorteXploradores", cuerpo2,usuario.getEmail());
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

        emailServiceImp.enviarEmail("Cambio de contraseña",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Document</title>\n" +
                        "</head>\n" +
                        "\n" +
                        "<body style=\"width: 800px\">\n" +
                        "    <div style=\"background-color: #a5b4fc; width: 100%; padding: 3rem 0;\">\n" +
                        "        <div style=\"text-align: center; background-color: #ffffff; margin: 0 auto; width: 80%; border-radius: 8px;\">\n" +
                        "            <img style=\"margin-top: 3rem; width: 190px\"\n" +
                        "                src=\"https://master.d1oc2nyuhwk984.amplifyapp.com/assets/images/logo.png\" alt=\"logo\">\n" +
                        "            <p style=\"margin: 1rem 0; font-size: 25px;\">Cambio de contraseña</p>\n" +
                        "            <p style=\"color: #424242;\">Hola, <b>"+u.getUsername()+"</b>, has solicitado cambiar tu contraseña, <br> para cambiar tu contraseña ingresa al siguiente link:  \n" +
                        "            </p>\n" +
                        "            <div style=\"margin: 2rem auto; width: 120px; background-color: #4f46e5; padding: 8px; border-radius: 6px; \">\n" +
                        "                <a style=\"color: #ffffff; text-decoration: none\" href=\""+urlFrontend+"password-reset/confirmation/"+passwordResetToken.getToken()+"\">Continuar</a>\n" +
                        "            </div>\n" +
                        "            <div style=\"width: 100%; border-top: 2px solid #a5b4fc; padding: 1rem 0\">\n" +
                        "                <p>Copyright © 2022 Analytic Hierarchy Process <br> Todos los derechos reservados.</p>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>"

                ,
                u.getEmail());

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

        emailServiceImp.enviarEmail("Contraseña actualizada",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Document</title>\n" +
                        "</head>\n" +
                        "\n" +
                        "<body style=\"width: 800px\">\n" +
                        "    <div style=\"background-color: #a5b4fc; width: 100%; padding: 3rem 0;\">\n" +
                        "        <div style=\"text-align: center; background-color: #ffffff; margin: 0 auto; width: 80%; border-radius: 8px;\">\n" +
                        "            <img style=\"margin-top: 3rem; width: 190px\"\n" +
                        "                src=\"https://master.d1oc2nyuhwk984.amplifyapp.com/assets/images/logo.png\" alt=\"logo\">\n" +
                        "            <p style=\"margin: 1rem 0; font-size: 25px;\">Cambio de contraseña</p>\n" +
                        "            <p style=\"color: #424242;\">Hola, <b>"+u.getUsername()+"</b>, se ha cambiado tu contraseña en el sistema.  \n" +
                        "            </p>\n" +
                        "            <div style=\"width: 100%; border-top: 2px solid #a5b4fc; padding: 1rem 0\">\n" +
                        "                <p>Copyright © 2022 Analytic Hierarchy Process <br> Todos los derechos reservados.</p>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>"

                ,
                u.getEmail());
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
