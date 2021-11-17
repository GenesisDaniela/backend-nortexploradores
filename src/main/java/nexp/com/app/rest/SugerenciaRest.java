/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.*;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.NotificacionService;
import nexp.com.app.service.PasajeroService;
import nexp.com.app.service.SugerenciaService;
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
    NotificacionService nser;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Sugerencia s, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
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
        notificacion.setFecha(s.getFecha());
        nser.guardar(notificacion);
        return ResponseEntity.ok(s);
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
