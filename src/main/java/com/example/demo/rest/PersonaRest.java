/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Persona;
import com.example.demo.service.PersonaService;
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
 * @author GenesisDanielaVJ
 */
@RestController
@RequestMapping("/persona")
@CrossOrigin(origins = "http://localhost:4200/")
@Slf4j
public class PersonaRest {

    @Autowired
    PersonaService pser;

    @GetMapping
    public ResponseEntity<List<Persona>> getPersona() {
        return ResponseEntity.ok(pser.listar());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Persona> eliminarPersona(@PathVariable int id) {

        Persona a = pser.encontrar(id).orElse(null);

        pser.eliminar(id);

        return ResponseEntity.ok(a);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarPersona(@PathVariable int id) {

        Persona a = pser.encontrar(id).orElse(null);

        if (a == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(a);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Persona a, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        pser.guardar(a);
        return ResponseEntity.ok(a);
    }
    
    
}
