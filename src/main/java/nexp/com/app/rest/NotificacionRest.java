/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Notificacion;
import nexp.com.app.model.Pasajero;
import nexp.com.app.model.Persona;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.NotificacionService;
import nexp.com.app.service.PasajeroService;
import nexp.com.app.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/notificacion")
@CrossOrigin(origins = "*")
public class NotificacionRest {
    
    @Autowired
    NotificacionService nser;

    @Autowired
    UsuarioService user;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Notificacion n, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = user.encontrar(n.getIdNotificacion()).orElse(null);
        if(usuario == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id del usuario"), HttpStatus.NOT_FOUND);
        }
        n.setUsuario(usuario);
        return ResponseEntity.ok(n);
    }

    @GetMapping
    public ResponseEntity<List<Notificacion>> getNotificacion() {
        return ResponseEntity.ok(nser.listar());
    }

}
