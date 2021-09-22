/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Transaccionp;
import com.example.demo.service.TransaccionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author santi
 */
@RestController
@RequestMapping(value = "/transaccion")
public class TransaccionRestP {
    @Autowired
    public TransaccionService pser;
    
//    @Autowired
//  public UsuarioService user;
    
    
    @GetMapping
    public ResponseEntity<List<Transaccionp>> transacciones(){
       return new ResponseEntity(pser.listar(), HttpStatus.OK);
    }
}
