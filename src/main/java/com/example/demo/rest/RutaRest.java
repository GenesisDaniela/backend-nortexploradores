/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Tour;
import com.example.demo.model.Ruta;
import com.example.demo.service.RutaService;
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
@RequestMapping("/ruta")
@CrossOrigin(origins = "http://localhost:4200/")
public class RutaRest {
    
    @Autowired
    RutaService rser;
    
    @GetMapping
    public ResponseEntity<List<Ruta>> getRuta() {
        return ResponseEntity.ok(rser.listar());
    }
    
    @GetMapping(path = "/{id}/tours")
    public ResponseEntity<?> TourPorRuta(@PathVariable int id) {

        List<Tour> r = (List)(rser.encontrar(id).get().tourCollection());
         if (r == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(r);
    }
}
