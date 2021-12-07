/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Compra;
import nexp.com.app.model.Descuento;
import nexp.com.app.model.Devolucion;
import nexp.com.app.service.DescuentoService;
import nexp.com.app.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author GenesisDanielaVJ
 */
@RestController
@RequestMapping("/devolucion")
@CrossOrigin(origins = "*")
@Slf4j
public class DevolucionRest {

    @Autowired
    DevolucionService dser;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Devolucion d, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Compra compra = d.getCompra();
        if(compra ==  null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","la compra no existe"), HttpStatus.NOT_FOUND);
        }
        d.setCompra(compra);
        dser.guardar(d);
        return ResponseEntity.ok(d);
    }

    @GetMapping
    public ResponseEntity<List<Devolucion>> getDevolucion() {
        return ResponseEntity.ok(dser.listar());
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Devolucion d, BindingResult br){
        if(br.hasErrors()){
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Devolucion dev = dser.encontrar(d.getIdDevolucion()).orElse(null);
        if(dev == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","la devolucion no existe"), HttpStatus.NOT_FOUND);
        }
        dser.guardar(d);
        return ResponseEntity.ok(dser.encontrar(d.getIdDevolucion()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Devolucion> eliminarDevolucion(@PathVariable int id) {
        Devolucion d = dser.encontrar(id).orElse(null);
        dser.eliminar(id);
        return ResponseEntity.ok(d);
    }

    
}
