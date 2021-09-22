/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Evento;
import com.example.demo.service.EventoService;
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
@RequestMapping("/evento")
@CrossOrigin(origins = "http://localhost:4200/")
@Slf4j
public class EventoRest {

    @Autowired
    EventoService eser;

    @GetMapping
    public ResponseEntity<List<Evento>> getEvento() {
        return ResponseEntity.ok(eser.listar());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Evento> eliminarEvento(@PathVariable int id) {

        Evento a = eser.encontrar(id).orElse(null);

        eser.eliminar(id);

        return ResponseEntity.ok(a);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarEvento(@PathVariable int id) {

        Evento a = eser.encontrar(id).orElse(null);

        if (a == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(a);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Evento a, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        eser.guardar(a);
        return ResponseEntity.ok(a);
    }
    
    
}
