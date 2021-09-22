/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Paquete;
import com.example.demo.model.Tour;
import com.example.demo.service.TourService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/tour")
@CrossOrigin(origins = "http://localhost:4200/")
public class TourRest {
    
    @Autowired
    TourService tser;
    
    @GetMapping
    public ResponseEntity<List<Tour>> getTour() {
        return ResponseEntity.ok(tser.listar());
    }
    
    @GetMapping(path = "/{id}/paquetes")
    public ResponseEntity<?> paquetePorRuta(@PathVariable int id) {

        List<Paquete> p = (List)(tser.encontrar(id).get().paqueteCollection());
         if (p == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(p);
    }
}
