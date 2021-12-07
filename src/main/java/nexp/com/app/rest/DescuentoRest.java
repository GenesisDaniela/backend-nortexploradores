/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Descuento;
import nexp.com.app.service.DescuentoService;
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
@RequestMapping("/descuento")
@CrossOrigin(origins = "*")
@Slf4j
public class DescuentoRest {

    @Autowired
    DescuentoService dser;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Descuento u, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        dser.guardar(u);
        return ResponseEntity.ok(u);
    }

    @GetMapping
    public ResponseEntity<List<Descuento>> getDescuento() {
        return ResponseEntity.ok(dser.listar());
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Descuento d, BindingResult br){
        if(br.hasErrors()){
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Descuento dto = dser.encontrar(d.getIdDescuento()).orElse(null);
        if(dto == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","el descuento no existe"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dser.encontrar(d.getIdDescuento()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Descuento> eliminarDescuento(@PathVariable int id) {
        Descuento d = dser.encontrar(id).orElse(null);
        dser.eliminar(id);
        return ResponseEntity.ok(d);
    }

    @GetMapping(path = "/{id}/compra")
    public ResponseEntity<List<?>> compraPorDescuento(@PathVariable int id){
        return ResponseEntity.ok((List)dser.encontrar(id).get().compraCollection());
    }


    
    
}
