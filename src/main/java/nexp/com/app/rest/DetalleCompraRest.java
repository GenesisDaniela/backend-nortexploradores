/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Compra;
import nexp.com.app.model.DetalleCompra;
import nexp.com.app.model.Paquete;
import nexp.com.app.model.Pasajero;
import nexp.com.app.service.CompraService;
import nexp.com.app.service.DetalleCompraService;

import java.util.Date;
import java.util.List;

import nexp.com.app.service.PaqueteService;
import nexp.com.app.service.PasajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/detcompra")
@CrossOrigin(origins = "*")
public class DetalleCompraRest {

    @Autowired
    DetalleCompraService detser;

    @Autowired
    CompraService compraService;

    @Autowired
    PaqueteService paqueteService;

    @Autowired
    PasajeroService pasajeroService;
    
    @GetMapping
    public ResponseEntity<List<DetalleCompra>> getDetalleCompra() {
        return ResponseEntity.ok(detser.listar());
    }

    @PostMapping(path = "/detallescompra")
    public ResponseEntity<?>guardarDetallesCompra(@RequestBody @Valid List<DetalleCompra> detalleCompra, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        for(DetalleCompra det: detalleCompra){
            Compra c =compraService.encontrar(det.getCompra().getIdCompra()).orElse(null);
            Pasajero pasajero = det.getPasajero();

            det.setPasajero(pasajero);
            det.setCompra(c);
            det.setFecha(new Date());
            detser.guardar(det);
        }

        return ResponseEntity.ok(detalleCompra);
    }

    @PostMapping
    public ResponseEntity<?>guardarDetalleCompra(@RequestBody @Valid DetalleCompra detalleCompra, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        detser.guardar(detalleCompra);
        return ResponseEntity.ok(detalleCompra);
    }
}
