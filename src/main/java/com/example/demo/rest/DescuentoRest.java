/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Descuento;
import com.example.demo.service.DescuentoService;
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
@RequestMapping("/descuento")
@CrossOrigin(origins = "http://localhost:4200/")
@Slf4j
public class DescuentoRest {

    @Autowired
    DescuentoService dser;

    @GetMapping
    public ResponseEntity<List<Descuento>> getDescuento() {
        return ResponseEntity.ok(dser.listar());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Descuento> eliminarDescuento(@PathVariable int id) {

        Descuento d = dser.encontrar(id).orElse(null);

        dser.eliminar(id);

        return ResponseEntity.ok(d);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Descuento u, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        dser.guardar(u);
        return ResponseEntity.ok(u);
    }
    
    @GetMapping(path = "/{id}/compra")
    public ResponseEntity<List<?>> compraPorDescuento(@PathVariable int id){
        return ResponseEntity.ok((List)dser.encontrar(id).get().compraCollection());
    } 
    
    
}
