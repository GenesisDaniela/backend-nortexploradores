/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.dao.ComplementoDAO;
import com.example.demo.model.Complemento;
import com.example.demo.model.Empleado;
import com.example.demo.service.ComplementoService;
import com.example.demo.service.EmpleadoService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GenesisDanielaVJ
 */
@RestController
@RequestMapping("/empleado")
@CrossOrigin(origins = "http://localhost:4200/")
@Slf4j
public class EmpleadoRest {

    @Autowired
    EmpleadoService eser;

    @GetMapping
    public ResponseEntity<List<Empleado>> getEmpleado() {
        return ResponseEntity.ok(eser.listar());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Empleado> eliminarEmpleado(@PathVariable int id) {

        Empleado e = eser.encontrar(id).orElse(null);

        eser.eliminar(id);

        return ResponseEntity.ok(e);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarEmpleado(@PathVariable int id) {

        Empleado e = eser.encontrar(id).orElse(null);

        if (e == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(e);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Empleado e, BindingResult br) {

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        eser.guardar(e);
        return ResponseEntity.ok(e);
    }


    public static class ComplementoServiceImp implements ComplementoService {

        @Autowired
        ComplementoDAO complementoDAO;

        @Override
        @Transactional
        public void guardar(Complemento complementoService) {
        complementoDAO.save(complementoService);
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<Complemento> encontrar(int id) {
        return complementoDAO.findById(id);
        }

        @Override
        @Transactional(readOnly = true)
        public List<Complemento> listar() {
        return complementoDAO.findAll();
        }

        @Override
        @Transactional
        public void eliminar(int id) {
        complementoDAO.deleteById(id);
        }
    }
}
