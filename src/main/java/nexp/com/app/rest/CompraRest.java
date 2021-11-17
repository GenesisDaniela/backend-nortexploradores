/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.*;
import nexp.com.app.service.CompraService;

import java.util.Date;
import java.util.List;

import nexp.com.app.service.ReservaService;
import nexp.com.app.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Santi & Dani
 */

@RestController
@RequestMapping("/compra")
@CrossOrigin(origins = "*")
public class CompraRest {
    @Autowired
    CompraService compraservice;

    @Autowired
    TourService tourService;

    @Autowired
    ReservaService reservaService;
    
    @GetMapping
    public ResponseEntity<List<Compra>> getCompra() {
        return ResponseEntity.ok(compraservice.listar());
    }

    @GetMapping(path = "/{idCompra}")
    public ResponseEntity<Compra> encontrarCompra(@PathVariable Long idCompra) {
        Compra compra = compraservice.encontrar(idCompra).orElse(null);

        if(compra ==null){
            return new ResponseEntity("COMPRA NO ENCONTRADA", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(compra);
    }

    @PostMapping
    public ResponseEntity<Compra> crearCompra(@RequestBody @Valid Compra compra){
        compraservice.guardar(compra);
        return ResponseEntity.ok(compra);
    }


    @PostMapping(path = "/compraReservada/{idtour}")
    public ResponseEntity<Compra> crearCompraReservada(@RequestBody @Valid Compra compra, @PathVariable int idtour){

        Tour t = tourService.encontrar(idtour).get();
        Date fechaSalida = t.getFechaSalida();
        Date fechaReserva = new Date();

        int milisegundospordia= 86400000;
        int dias = (int) ((fechaReserva.getTime()-fechaSalida.getTime()) / milisegundospordia);

        if(dias<3){
            return new ResponseEntity("No se puede reservar menos de 3 días antes de la salida del paquete", HttpStatus.BAD_REQUEST);
        }

        Reserva reserva = new Reserva();
        reserva.setFecha(new Date());
        reserva.setEstado("PENDIENTE");
        reservaService.guardar(reserva);
        compra.setFecha(new Date());
        compra.setReserva(reserva);
        compra.setEstado("PENDIENTE");
        compraservice.guardar(compra);
        return ResponseEntity.ok(compra);
    }

    @GetMapping(path = "/{id}/transacciones")
    public ResponseEntity<List<Transaccionp>> transaccionPorCompra(@PathVariable Long id) {

        return ResponseEntity.ok((List)compraservice.encontrar(id).get().transaccionpCollection());
    }
}
