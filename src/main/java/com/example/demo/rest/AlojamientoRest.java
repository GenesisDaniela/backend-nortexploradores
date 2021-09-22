/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Alojamiento;
import com.example.demo.service.AlojamientoService;
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
@RequestMapping("/alojamiento")
@CrossOrigin(origins = "http://localhost:4200/")
@Slf4j
public class AlojamientoRest {
//Ola eso estodo o k, n0 también kiero decirte q...
    @Autowired
    AlojamientoService aser;

    @GetMapping
    public ResponseEntity<List<Alojamiento>> getAlojamiento() {
        return ResponseEntity.ok(aser.listar());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Alojamiento> eliminarAlojamiento(@PathVariable int id) {

        Alojamiento a = aser.encontrar(id).orElse(null);

        aser.eliminar(id);

        return ResponseEntity.ok(a);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarAlojamiento(@PathVariable int id) {

        Alojamiento a = aser.encontrar(id).orElse(null);

        if (a == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(a);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Alojamiento a, BindingResult br) {
    //Comentario
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        aser.guardar(a);
        return ResponseEntity.ok(a);
    }
    
    
}
