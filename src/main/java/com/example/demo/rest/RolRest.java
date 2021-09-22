/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.security.entity.Usuario;
import com.example.demo.security.entity.Rol;
import com.example.demo.security.service.RolService;
import java.util.List;
import javax.validation.Valid;
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
 * @author GenesisDanielaVJ
 */

@RestController
@RequestMapping("/rol")
@CrossOrigin(origins = "http://localhost:4200/")
public class RolRest {
    
    @Autowired
    RolService rser;
    
    @GetMapping
    public ResponseEntity<List<Rol>> getRol() {
        return ResponseEntity.ok(rser.listar());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Rol> eliminarRol(@PathVariable int id) {

        Rol r = rser.encontrar(id).orElse(null);

        rser.eliminar(id);

        return ResponseEntity.ok(r);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarRol(@PathVariable int id) {

        Rol r = rser.encontrar(id).orElse(null);

        if (r == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<?> guardarRol(@RequestBody @Valid Rol r, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        rser.guardar(r);
        return ResponseEntity.ok(r);
    }
    
   /* @GetMapping(path = "/{id}/usuarios")
    public ResponseEntity<?> usuariosPorRol(@PathVariable int id) {

        List<Usuario> u = (List)(rser.encontrar(id).get().usuarioCollection());
         if (u == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
         
        return ResponseEntity.ok(u);
    }*/
    
}
