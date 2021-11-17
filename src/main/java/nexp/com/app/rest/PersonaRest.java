/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Persona;
import nexp.com.app.model.TipoIdentificacion;
import nexp.com.app.model.Transporte;
import nexp.com.app.security.model.Usuario;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.service.PersonaService;
import nexp.com.app.service.TipoIdentificacionService;
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
@RequestMapping("/persona")
@CrossOrigin(origins = "*")
@Slf4j
public class PersonaRest {

    @Autowired
    PersonaService pser;

    @Autowired
    TipoIdentificacionService tser;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Persona p, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        TipoIdentificacion TId = tser.encontrar(p.getIdTipo().getIdTipo()).orElse(null);
        if (TId == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El tipo de identificacion no existe"), HttpStatus.NOT_FOUND);
        }
        p.setIdTipo(TId);
        pser.guardar(p);
        return ResponseEntity.ok(p);
    }

    @GetMapping
    public ResponseEntity<List<Persona>> getPersona() {
        return ResponseEntity.ok(pser.listar());
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Persona p, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Persona persona = pser.encontrar(p.getIdPersona()).orElse(null);
        if(persona == null){
            return new ResponseEntity("La persona no existe",HttpStatus.NOT_FOUND);
        }
        pser.guardar(p);
        return ResponseEntity.ok(pser.encontrar(p.getIdPersona()));
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarPersona(@PathVariable int id) {
        Persona a = pser.encontrar(id).orElse(null);
        if (a == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(a);
    }


    
    
}
