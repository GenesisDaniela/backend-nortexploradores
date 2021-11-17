/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Alojamiento;
import nexp.com.app.model.Cargo;
import nexp.com.app.model.Empleado;
import nexp.com.app.service.CargoService;
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
@RequestMapping("/cargo")
@CrossOrigin(origins = "*")
@Slf4j
public class CargoRest {

    @Autowired
    CargoService cser;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Cargo c, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        cser.guardar(c);
        return ResponseEntity.ok(c);
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> getCargo() {
        return ResponseEntity.ok(cser.listar());
    }

    @PutMapping()
    public ResponseEntity<?> editar(@RequestBody @Valid Cargo c, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Cargo cargo = cser.encontrar(c.getIdCargo()).orElse(null);
        if(cargo == null){
            return new ResponseEntity("El cargo no existe", HttpStatus.NOT_FOUND);
        }
        cser.guardar(c);
        return ResponseEntity.ok(cser.encontrar(c.getIdCargo()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Cargo> eliminarCargo(@PathVariable int id) {
        Cargo c = cser.encontrar(id).orElse(null);
        cser.eliminar(id);
        return ResponseEntity.ok(c);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarCargo(@PathVariable int id) {
        Cargo c = cser.encontrar(id).orElse(null);
        if (c == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(c);
    }

    @GetMapping(path = "/{id}/empleados")
    public ResponseEntity<?> empleadosPorCargo(@PathVariable int id) {
        List<Empleado> e = (List)(cser.encontrar(id).get().empleadoCollection());
         if (e == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(e);
    }

    @GetMapping(path = "/{idCargo}/deshabilitar")
    public ResponseEntity<?> deshabilitarCargo(@PathVariable int idCargo) {
        Cargo cargo= cser.encontrar(idCargo).orElse(null);
        if (cargo == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El cargo no existe"), HttpStatus.NOT_FOUND);
        }
        cargo.setEstado(false);
        cser.guardar(cargo);
        return ResponseEntity.ok(cargo);
    }

    @GetMapping(path = "/{idCargo}/habilitar")
    public ResponseEntity<?> habilitarCargo(@PathVariable int idCargo) {
        Cargo cargo= cser.encontrar(idCargo).orElse(null);
        if (cargo == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El cargo no existe"), HttpStatus.NOT_FOUND);
        }
        cargo.setEstado(true);
        cser.guardar(cargo);
        return ResponseEntity.ok(cargo);
    }



    
}
