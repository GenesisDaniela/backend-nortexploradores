/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.*;
import nexp.com.app.service.EmpresaService;

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
@RequestMapping("/empresa")
@CrossOrigin(origins = "*")
public class EmpresaRest {
    
    @Autowired
    EmpresaService eser;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Empresa e, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        eser.guardar(e);
        return ResponseEntity.ok(e);
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> getEmpresa() {
        return ResponseEntity.ok(eser.listar());
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Empresa e, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Empresa empresa = eser.encontrar(e.getIdEmpresa()).orElse(null);
        if(empresa == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id", "La empresa no existe"), HttpStatus.NOT_FOUND);
        }
        eser.guardar(e);
        return ResponseEntity.ok(eser.encontrar(e.getIdEmpresa()));
    }

    @DeleteMapping(path = "/{id}")
        public ResponseEntity<?> eliminarEmpresa(@PathVariable int id) {
        Empresa e = eser.encontrar(id).orElse(null);
        if (e == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe la empresa"), HttpStatus.NOT_FOUND);
        }
        eser.eliminar(id);
        return ResponseEntity.ok(e);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarEmpresa(@PathVariable int id) {
        Empresa e = eser.encontrar(id).orElse(null);
        if (e == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe la empresa"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(e);
    }

    @GetMapping(path = "/{id}/seguro")
    public ResponseEntity<?> seguroPorEmpresa(@PathVariable int id) {
        List<Seguro> s = (List)(eser.encontrar(id).get().seguroCollection());
        if (s == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id del seguro"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(s);
    }

    @GetMapping(path = "/{id}/transporte")
    public ResponseEntity<?> transportePorEmpresa(@PathVariable int id) {
        List<Transporte> t = (List)(eser.encontrar(id).get().transporteCollection());
        if (t == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id del transporte"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(t);
    }

    @GetMapping(path = "/{idEmpresa}/deshabilitar")
    public ResponseEntity<?> deshabilitarEmpresa(@PathVariable int idEmpresa) {
        Empresa empresa= eser.encontrar(idEmpresa).orElse(null);
        if (empresa == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        empresa.setEstado(false);
        eser.guardar(empresa);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping(path = "/{idEmpresa}/habilitar")
    public ResponseEntity<?> habilitarEmpresa(@PathVariable int idEmpresa) {
        Empresa empresa= eser.encontrar(idEmpresa).orElse(null);
        if (empresa == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        empresa.setEstado(true);
        eser.guardar(empresa);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping(path = "/transportes")
    public ResponseEntity<?> transportes() {
        List<Empresa> empresasTransportes = new ArrayList<>();
        for(Empresa e :eser.listar()){
            if(e.seguroCollection().size()==0 && e.transporteCollection().size()>0){
                empresasTransportes.add(e);
            }
        }

        return ResponseEntity.ok(empresasTransportes);
    }

    @GetMapping(path = "/seguros")
    public ResponseEntity<?> seguros() {
        List<Empresa> empresasSeguro = new ArrayList<>();
        for(Empresa e :eser.listar()){
            if(e.seguroCollection().size()>0 && e.transporteCollection().size()==0){
                empresasSeguro.add(e);
            }
        }

        return ResponseEntity.ok(empresasSeguro);
    }

}
