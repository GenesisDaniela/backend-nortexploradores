/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.model.Departamento;
import com.example.demo.security.entity.Usuario;
import com.example.demo.service.DepartamentoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/departamento")
@CrossOrigin(origins = "http://localhost:4200/")
public class DepartamentoRest {

    @Autowired
    DepartamentoService dptoser;

    @GetMapping
    public ResponseEntity<List<Departamento>> getDepartamento() {
        return ResponseEntity.ok(dptoser.listar());
    }

    @PostMapping
    public ResponseEntity<?> regitrarDpto(@RequestBody @Valid Departamento dpto, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        dptoser.guardar(dpto);
        return ResponseEntity.ok(dpto);

    }
}
