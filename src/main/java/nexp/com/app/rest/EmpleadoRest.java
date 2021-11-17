/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Cargo;
import nexp.com.app.model.Empleado;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.service.CargoService;
import nexp.com.app.service.EmpleadoService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Persona;
import nexp.com.app.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author GenesisDanielaVJ
 */
@RestController
@RequestMapping("/empleado")
@CrossOrigin(origins = "*")
@Slf4j
public class EmpleadoRest {

    @Autowired
    EmpleadoService eser;

    @Autowired
    CargoService cser;
    
    @Autowired
    PersonaService pser;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Empleado e, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Cargo cargo = cser.encontrar(e.getCargo().getIdCargo()).orElse(null);
        if(cargo == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id", "el cargo no existe"), HttpStatus.NOT_FOUND);
        }
        e.setCargo(cargo);
        Persona persona = pser.encontrar(e.getPersona().getIdPersona()).get();
        e.setPersona(persona);
        eser.guardar(e);
        return ResponseEntity.ok(e);
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> getEmpleado() {
        return ResponseEntity.ok(eser.listar());
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Empleado e, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Empleado empleado = eser.encontrar(e.getIdEmpleado()).orElse(null);
        if(empleado ==null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","Empleado no existe"), HttpStatus.NOT_FOUND);
        }
        eser.guardar(e);

        return ResponseEntity.ok(eser.encontrar(e.getIdEmpleado()));
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

    @GetMapping(path = "/{idEmpleado}/deshabilitar")
    public ResponseEntity<?> deshabilitar(@PathVariable int idEmpleado) {
        Empleado empleado= eser.encontrar(idEmpleado).orElse(null);
        if (empleado == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        empleado.setEstado(false);
        eser.guardar(empleado);
        return ResponseEntity.ok(empleado);
    }

    @GetMapping(path = "/{idEmpleado}/habilitar")
    public ResponseEntity<?> habilitar(@PathVariable int idEmpleado) {
        Empleado empleado= eser.encontrar(idEmpleado).orElse(null);
        if (empleado == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        empleado.setEstado(true);
        eser.guardar(empleado);
        return ResponseEntity.ok(empleado);
    }

}
