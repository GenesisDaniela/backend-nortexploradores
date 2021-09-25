package com.example.demo.rest;

import com.example.demo.model.Complemento;
import com.example.demo.service.ComplementoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/complemento")
@CrossOrigin(origins = "*")
@Slf4j
public class ComplementoRest {

    @Autowired
    ComplementoService complementoService;

    @GetMapping
    public ResponseEntity<Complemento> listar(){
    return new ResponseEntity(complementoService.listar(), HttpStatus.OK);
    }


}
