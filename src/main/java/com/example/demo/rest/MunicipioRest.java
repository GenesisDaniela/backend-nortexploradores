/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Evento;
import com.example.demo.model.Municipio;
import com.example.demo.model.Ruta;
import com.example.demo.service.MunicipioService;
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
@RequestMapping("/municipio")
@CrossOrigin(origins = "http://localhost:4200/")
public class MunicipioRest {
    
    @Autowired
    MunicipioService mser;
    
    @GetMapping
    public ResponseEntity<List<Municipio>> getMunicipio() {
        return ResponseEntity.ok(mser.listar());
    }
    
    @GetMapping(path = "/{id}/eventos")
    public ResponseEntity<?> eventosPorMunicipio(@PathVariable int id) {

        List<Evento> e = (List)(mser.encontrar(id).get().eventoCollection());
         if (e == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(e);
    }
    
    @GetMapping(path = "/{id}/rutas")
    public ResponseEntity<?> rutasPorMunicipio(@PathVariable int id) {

        List<Ruta> e = (List)(mser.encontrar(id).get().rutaCollection());
         if (e == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(e);
    }
}
