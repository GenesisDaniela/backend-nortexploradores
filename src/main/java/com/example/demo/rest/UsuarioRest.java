/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Pasajero;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.service.UsuarioService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200/")
@Slf4j
public class UsuarioRest {

    @Autowired
    UsuarioService user;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
        return ResponseEntity.ok(user.listar());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable int id) {

        Usuario u = user.encontrar(id).orElse(null);

        user.eliminar(id);

        return ResponseEntity.ok(u);
    }
    
    
   /* @GetMapping(path = "/{id}/pasajeros")
    public ResponseEntity<List<Pasajero>> pasajerosPorUsuario(@PathVariable int id){
        return ResponseEntity.ok((List)user.encontrar(id).get().pasajeroCollection());
    } */
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarUsuario(@PathVariable int id) {

        Usuario u = user.encontrar(id).orElse(null);

        if (u == null) {

            //return new ResponseEntity<String>("El usuario con id: " + id + " no existe", HttpStatus.NOT_FOUND);
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(u);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Usuario u, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        user.guardar(u);
        return ResponseEntity.ok(u);
    }
    
    
}
