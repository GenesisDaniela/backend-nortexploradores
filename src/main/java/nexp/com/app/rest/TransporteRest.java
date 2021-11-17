/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.*;
import nexp.com.app.service.EmpresaService;
import nexp.com.app.service.TransporteService;

import java.util.ArrayList;
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
@Slf4j
@RequestMapping("/transporte")
@CrossOrigin(origins = "*")
public class TransporteRest {
    
    @Autowired
    TransporteService tser;

    @Autowired
    EmpresaService eser;


    @GetMapping
    public ResponseEntity<List<Transporte>> getTransporte() {
        return ResponseEntity.ok(tser.listar());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Transporte t, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Empresa empresa = eser.encontrar(t.getEmpresa().getIdEmpresa()).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "La empresa no existe"), HttpStatus.NOT_FOUND);
        }
        t.setEmpresa(empresa);
        tser.guardar(t);
        return ResponseEntity.ok(t);
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Transporte t, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Transporte transporte = tser.encontrar(t.getIdTransporte()).orElse(null);
        if(transporte ==null){
            return new ResponseEntity<ObjectError>(new ObjectError("id", "Seguro no existe"), HttpStatus.NOT_FOUND);
        }
        tser.guardar(t);
        return ResponseEntity.ok(tser.encontrar(t.getIdTransporte()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarTransporte(@PathVariable String id) {
        Transporte transporte = tser.encontrar(id).orElse(null);
        if (transporte == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe transporte"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(transporte);
    }

    @GetMapping(path = "/{idTransporte}/deshabilitar")
    public ResponseEntity<?> deshabilitarTransporte(@PathVariable String idtransporte) {
        Transporte transporte= tser.encontrar(idtransporte).orElse(null);
        if (transporte == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El transporte no existe"), HttpStatus.NOT_FOUND);
        }
        transporte.setEstado(false);
        tser.guardar(transporte);
        return ResponseEntity.ok(transporte);
    }

    @GetMapping(path = "/{idTransporte}/habilitar")
    public ResponseEntity<?> habilitarTransporte(@PathVariable String idtransporte) {
        Transporte transporte= tser.encontrar(idtransporte).orElse(null);
        if (transporte == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El transporte no existe"), HttpStatus.NOT_FOUND);
        }
        transporte.setEstado(true);
        tser.guardar(transporte);
        return ResponseEntity.ok(transporte);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminarTransporte(@PathVariable String id) {
        Transporte transporte = tser.encontrar(id).orElse(null);
        if(transporte == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe transporte"), HttpStatus.NOT_FOUND);
        }
        tser.eliminar(id);
        return ResponseEntity.ok(transporte);
    }
    @GetMapping(path = "/{idTransporte}/transportetour")
    public ResponseEntity<?> getTransporteTour(@PathVariable String idTransporte) {
        Transporte transporte= tser.encontrar(idTransporte).orElse(null);
        if (transporte == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(transporte.transporteTourCollection());
    }



}
