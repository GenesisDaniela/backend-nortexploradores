/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Empresa;
import nexp.com.app.model.Seguro;
import nexp.com.app.service.EmpresaService;
import nexp.com.app.service.SeguroService;
import java.util.List;
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
@RequestMapping("/seguro")
@CrossOrigin(origins = "*")
public class SeguroRest {
    
    @Autowired
    SeguroService sser;

    @Autowired
    EmpresaService eser;
    
    @GetMapping
    public ResponseEntity<List<Seguro>> getSeguro() {
        return ResponseEntity.ok(sser.listar());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Seguro s, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Empresa empresa = eser.encontrar(s.getEmpresa().getIdEmpresa()).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "La empresa no existe"), HttpStatus.NOT_FOUND);
        }
        s.setEmpresa(empresa);
        sser.guardar(s);
        return ResponseEntity.ok(s);
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Seguro s, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Seguro seguro = sser.encontrar(s.getIdSeguro()).orElse(null);
        if(seguro ==null){
            return new ResponseEntity<ObjectError>(new ObjectError("id", "Seguro no existe"), HttpStatus.NOT_FOUND);
        }
        sser.guardar(s);
        return ResponseEntity.ok(sser.encontrar(s.getIdSeguro()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarSeguro(@PathVariable int id) {
        Seguro seguro = sser.encontrar(id).orElse(null);
        if (seguro == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe seguro"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(seguro);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminarSeguro(@PathVariable int id) {
        Seguro s = sser.encontrar(id).orElse(null);
        if(s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe seguro"), HttpStatus.NOT_FOUND);
        }
        sser.eliminar(id);
        return ResponseEntity.ok(s);
    }

    @GetMapping(path = "/{idSeguro}/deshabilitar")
    public ResponseEntity<?> deshabilitarSeguro(@PathVariable int idSeguro) {
        Seguro seguro= sser.encontrar(idSeguro).orElse(null);
        if (seguro == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El seguro no existe"), HttpStatus.NOT_FOUND);
        }
        seguro.setEstado(false);
        sser.guardar(seguro);
        return ResponseEntity.ok(seguro);
    }

    @GetMapping(path = "/{idSeguro}/habilitar")
    public ResponseEntity<?> habilitarSeguro(@PathVariable int idSeguro) {
        Seguro seguro= sser.encontrar(idSeguro).orElse(null);
        if (seguro == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El seguro no existe"), HttpStatus.NOT_FOUND);
        }
        seguro.setEstado(true);
        sser.guardar(seguro);
        return ResponseEntity.ok(seguro);
    }

}
