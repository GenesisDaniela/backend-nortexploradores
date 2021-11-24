/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.*;
import nexp.com.app.negocio.NorteXploradores;
import nexp.com.app.service.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author GenesisDanielaVJ
 */
@RestController
@RequestMapping("/paquete")
@CrossOrigin(origins = "*")
@Slf4j
public class PaqueteRest {
    
    @Autowired
    PaqueteService pser;

    @Autowired
    AlojamientoService aser;

    @Autowired
    CompraService cser;

    @Autowired
    NotificacionService nser;

    @Autowired
    ActividadService actividadService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Paquete p, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        if(p.getAlojamiento() != null){
            Alojamiento a = aser.encontrar(p.getAlojamiento().getIdAlojamiento()).orElse(null);
            if (a == null) {
                return new ResponseEntity<ObjectError>(new ObjectError("id", "El alojamiento no existe"), HttpStatus.NOT_FOUND);
            }
            p.setAlojamiento(a);
        }
        pser.guardar(p);
//        if(p.getEstado().equals("PENDIENTE")){
//            Notificacion notificacion = new Notificacion();
//            List<DetalleCompra> detCompra = (List)p.detalleCompraCollection();
//            Usuario usuario = detCompra.get(0).getCompra().getUsuario();
//            notificacion.setDescripcion("Has recibo una solicitud de paquete personalizado de: " + usuario.getUsername());
//            notificacion.setUsuario(usuario);
//            notificacion.setEstado(true);
//            nser.guardar(notificacion);
//        }

        return ResponseEntity.ok(p);
    }

    @GetMapping
    public ResponseEntity<List<Paquete>> getPaquete() {
        return ResponseEntity.ok(pser.listar());
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Paquete p, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Paquete paquete = pser.encontrar(p.getIdPaq()).orElse(null);
        if(paquete == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El paquete no existe"), HttpStatus.NOT_FOUND);
        }
        pser.guardar(p);
        return ResponseEntity.ok(pser.encontrar(p.getIdPaq()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Paquete> eliminarPaquete(@PathVariable int id) {
        Paquete p = pser.encontrar(id).orElse(null);
        pser.eliminar(id);
        return ResponseEntity.ok(p);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarPaquete(@PathVariable int id) {
        Paquete p = pser.encontrar(id).orElse(null);
        if (p == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(p);
    }

    @GetMapping(path = "/{id}/compras")
    public ResponseEntity<?> compraPorPaquete(@PathVariable int id) {
        Paquete paq = pser.encontrar(id).orElse(null);
         if (paq == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el paquete"), HttpStatus.NOT_FOUND);
         }
        List<Compra> comprasTot = new ArrayList<>();
        NorteXploradores nxs = new NorteXploradores();
        for(Tour t: paq.tourCollection()){
            for (Compra c: t.compraCollection())
            if(!nxs.existeCompra(comprasTot, c)){
                comprasTot.add(c);
            }
        }
        return ResponseEntity.ok(comprasTot);
    }

    @GetMapping(path = "/{id}/actividades")
    public ResponseEntity<?> actividadesPorPaquete(@PathVariable int id) {
        List<Actividad> c = (List)(pser.encontrar(id).get().actividadCollection());
        if (c == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(c);
    }

    @GetMapping(path = "/{idPaquete}/deshabilitar")
    public ResponseEntity<?> deshabilitarPaquete(@PathVariable int idPaquete) {
        Paquete paquete= pser.encontrar(idPaquete).orElse(null);
        if (paquete == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        paquete.setEstado("INACTIVO");
        pser.guardar(paquete);
        return ResponseEntity.ok(paquete);
    }

    @GetMapping(path = "/{idPaquete}/habilitar")
    public ResponseEntity<?> habilitarPaquete(@PathVariable int idPaquete) {
        Paquete paquete= pser.encontrar(idPaquete).orElse(null);
        if (paquete == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        paquete.setEstado("ACTIVO");
        pser.guardar(paquete);
        return ResponseEntity.ok(paquete);
    }

    @PostMapping(path = "/{idPaquete}/guardarActividades")
    public ResponseEntity<?> actividadesPorPaquete(@PathVariable int idPaquete, @RequestBody List<Actividad> actividades) {
        Paquete paquete = pser.encontrar(idPaquete).orElse(null);
        for(Actividad a: actividades){
            a.setPaquete(paquete);
            actividadService.guardar(a);
        }
        return ResponseEntity.ok(paquete);
    }


}
