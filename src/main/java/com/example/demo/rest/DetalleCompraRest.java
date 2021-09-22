/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.DetalleCompra;
import com.example.demo.service.DetalleCompraService;
import java.util.List;
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
@RequestMapping("/detcompra")
@CrossOrigin(origins = "http://localhost:4200/")
public class DetalleCompraRest {
    
    DetalleCompraService detser;
    
    @GetMapping
    public ResponseEntity<List<DetalleCompra>> getDetalleCompra() {
        return ResponseEntity.ok(detser.listar());
    }
}
