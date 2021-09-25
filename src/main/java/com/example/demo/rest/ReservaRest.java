package com.example.demo.rest;

import com.example.demo.model.Reserva;
import com.example.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reserva")
public class ReservaRest {

    @Autowired
    ReservaService reservaService;

    @GetMapping
    public ResponseEntity<Reserva> listar(){
        return new ResponseEntity(reservaService.listar(), HttpStatus.OK);
    }


}
