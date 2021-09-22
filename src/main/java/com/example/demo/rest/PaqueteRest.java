/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Calificacion;
import com.example.demo.model.Compra;
import com.example.demo.model.Paquete;
import com.example.demo.model.Empleado;
import com.example.demo.model.Paquete;
import com.example.demo.service.PaqueteService;
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
@RequestMapping("/paquete")
@CrossOrigin(origins = "http://localhost:4200/")
public class PaqueteRest {
    
    @Autowired
    PaqueteService pser;
    
    @GetMapping
    public ResponseEntity<List<Paquete>> getPaquete() {
        return ResponseEntity.ok(pser.listar());
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Paquete> eliminarPaquete(@PathVariable int id) {

        Paquete p = pser.encontrar(id).orElse(null);

        pser.eliminar(id);

        return ResponseEntity.ok(p);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarPaquete(@PathVariable int id) {

        Paquete p = pser.encontrar(id).orElse(null);

        if (p == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Paquete p, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        pser.guardar(p);
        return ResponseEntity.ok(p);
    }
    
    @GetMapping(path = "/{id}/compras")
    public ResponseEntity<?> compraPorPaquete(@PathVariable int id) {

        List<Compra> c = (List)(pser.encontrar(id).get().compraCollection());
         if (c == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(c);
    }
    
    @GetMapping(path = "/{id}/calificaciones")
    public ResponseEntity<?> calificacionPorPaquete(@PathVariable int id) {

        List<Calificacion> c = (List)(pser.encontrar(id).get().calificacionCollection());
         if (c == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(c);
    }
}
