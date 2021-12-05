/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.security.controlador;

import nexp.com.app.negocio.EmailService;
import nexp.com.app.security.dto.JwtDto;
import nexp.com.app.security.dto.LoginUsuario;
import nexp.com.app.security.dto.NuevoUsuario;
import nexp.com.app.security.model.Rol;
import nexp.com.app.security.model.Rol.RolNombre;
import nexp.com.app.security.jwt.JwtProvider;
import nexp.com.app.security.servicio.RolService;
import nexp.com.app.security.servicio.UsuarioService;
import lombok.extern.slf4j.Slf4j;
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
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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

    @Value("${spring.mail.username}")
    String emailUsuarioEmisor;

    @Value("${spring.mail.password}")
    String clave;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
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
        usuario.setFecha(new Date());
        usuario.setImgUrl("https://yorktonrentals.com/wp-content/uploads/2017/06/usericon.png");
        usuario.setEstado(true);
        usuarioService.guardar(usuario);


        EmailService email=new EmailService(emailUsuarioEmisor, clave);
        email.enviarEmail(usuario.getEmail(), "Registro de sesión en el aplicativo web NorteXploradores", "" +
                "<h1>Bienvenido "+usuario.getUsername()+"</h1>" +
                "<p>te has registrado al aplicativo web NorteXploradores, estos son tus datos de ingreso de sesión:</p>" +
                "<ul>" +
                "<li>Usuario:"+usuario.getEmail()+"</li>" +
                "<li>Contraseña:"+nuevoUsuario.getPassword()+"</li>" +
                "</ul>" );


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
        EmailService email=new EmailService(emailUsuarioEmisor, clave);
        email.enviarEmail(usuario.getEmail(), "Registro de sesión en el aplicativo web NorteXploradores", "" +
                "<h1>Bienvenido "+usuario.getUsername()+"</h1>" +
                "<p>te has registrado al aplicativo web NorteXploradores, estos son tus datos de ingreso de sesión:</p>" +
                "<ul>" +
                "<li>Usuario:"+usuario.getEmail()+"</li>" +
                "</ul>" );

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
