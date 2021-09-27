package com.example.demo.rest;

import com.example.demo.model.Paquete;
import com.example.demo.model.Reserva;
import com.example.demo.service.CompraService;
import com.example.demo.service.PaqueteService;
import com.example.demo.service.ReservaService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reserva")
@Slf4j
public class ReservaRest {

    @Autowired
    ReservaService reservaService;

    @Autowired
    CompraService compraService;

    @Autowired
    PaqueteService paqueteService;
    @GetMapping
    public ResponseEntity<Reserva> listar(){
        return new ResponseEntity(reservaService.listar(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/paquete")
    public ResponseEntity<Paquete> paqueteDeReserva(@PathVariable int id){
        Paquete paquete = compraService.paqueteDeReserva(id);
        paquete = paqueteService.encontrar(paquete.getIdPaq()).get();

        return ResponseEntity.ok(paquete);
    }


}
