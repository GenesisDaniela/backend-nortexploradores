/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Departamento;
import nexp.com.app.service.DepartamentoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/departamento")
@CrossOrigin(origins = "*")
public class DepartamentoRest {

    @Autowired
    DepartamentoService dptoser;

    @PostMapping
    public ResponseEntity<?> regitrarDpto(@RequestBody @Valid Departamento dpto, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        dptoser.guardar(dpto);
        return ResponseEntity.ok(dpto);
    }

    @GetMapping
    public ResponseEntity<List<Departamento>> getDepartamento() {
        return ResponseEntity.ok(dptoser.listar());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarDepartamento(@PathVariable int id) {
        Departamento d = dptoser.encontrar(id).orElse(null);
        if (d == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(d);
    }
}
