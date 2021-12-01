/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.*;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.*;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Santi & GenesisDanielaVJ
 */

@RestController
@RequestMapping("/compra")
@Slf4j
@CrossOrigin(origins = "*")
public class CompraRest {
    @Autowired
    CompraService compraservice;

    @Autowired
    TourService tourService;

    @Autowired
    ReservaService reservaService;

    @Autowired
    DetalleCompraService detalleCompraService;

    @Autowired
    TransaccionService transaccionService;

    @Autowired
    UsuarioService usuarioService;
    
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
        int dias = (int) ((fechaSalida.getTime()-fechaReserva.getTime()) / milisegundospordia);

        if(dias<3){
            return new ResponseEntity("No se puede reservar menos de 3 días antes de la salida del paquete", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioService.encontrar(compra.getUsuario().getId_Usuario()).get();
        for(Compra c: usuario.compraCollection()){
            if(c.getTour().getIdTour() == t.getIdTour() && c.getEstado().equals("PAGADO")|| c.getEstado().equals("PAGO_PARCIAL")){
                return new ResponseEntity("No puedes comprar un mismo tour dos veces", HttpStatus.BAD_REQUEST);
            }
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

    @GetMapping(path = "/{id}/cancelarReserva")
    public ResponseEntity<?> cancelarReserva(@PathVariable int id) {
        Reserva reserva = reservaService.encontrar(id).get();
        if(reserva == null){
            return new ResponseEntity("RESERVA NO ENCONTRADA", HttpStatus.NOT_FOUND);
        }
        if(reserva.getEstado().equals("PENDIENTE")){
            Compra compra = ((List<Compra>)reserva.compraCollection()).get(0);
                for(DetalleCompra det: compra.detalleCompraCollection()) {
                    detalleCompraService.eliminar(det.getIdDetalle());
                }
                for(Transaccionp t: compra.transaccionpCollection()){
                        transaccionService.eliminar(t.getTransactionId());
                }
                compraservice.eliminar(compra.getIdCompra());
            return ResponseEntity.ok(reserva);
        }
        Compra compra = ((List<Compra>)reserva.compraCollection()).get(0);
        int cuposDisponibles = compra.getTour().getCantCupos() + compra.getCantidadPasajeros();
        Tour tour = compra.getTour();
        tour.setCantCupos(cuposDisponibles);
        reserva.setEstado("CANCELADA");
        compra.setEstado("CANCELADO");
        compraservice.guardar(compra);
        tourService.guardar(tour);
        reservaService.guardar(reserva);
        return ResponseEntity.ok(reserva);
    }

}
