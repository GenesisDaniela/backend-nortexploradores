/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Pasajero;
import com.example.demo.service.PasajeroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/pasajero")
@CrossOrigin(origins = "http://localhost:4200/")
public class PasajeroRest {
    
    @Autowired
    PasajeroService pser;
    
    @GetMapping
    public ResponseEntity<List<Pasajero>> getPasajero() {
        return ResponseEntity.ok(pser.listar());
    }
}
