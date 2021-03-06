/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nexp.com.app.rest;


import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Actividad;
import nexp.com.app.model.Alojamiento;
import nexp.com.app.model.Municipio;
import nexp.com.app.service.AlojamientoService;
import nexp.com.app.service.MunicipioService;
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
@RequestMapping("/alojamiento")
@CrossOrigin(origins = "*")
@Slf4j
public class AlojamientoRest {

    @Autowired
    AlojamientoService aser;

    @Autowired
    MunicipioService mser;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Alojamiento a, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Municipio municipio = mser.encontrar(a.getMunicipio().getIdMuni()).orElse(null);
        if(municipio == null){
            return new ResponseEntity<>("El municipio no fue encontrado", HttpStatus.NOT_FOUND);
        }
        a.setMunicipio(municipio);
        aser.guardar(a);
        return ResponseEntity.ok(a);
    }

    @GetMapping
    public ResponseEntity<List<Alojamiento>> getAlojamiento() {
        List<Alojamiento> alojamientoActivos = new ArrayList<>();
        for(Alojamiento alojamiento: aser.listar()){
            if(alojamiento.getEstado() == true)
                alojamientoActivos.add(alojamiento);
        }
        return ResponseEntity.ok(alojamientoActivos);
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Alojamiento a, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Alojamiento alojamiento = aser.encontrar(a.getIdAlojamiento()).orElse(null);
        if(alojamiento == null){
            return new ResponseEntity<>("El alojamiento no fue encontrado", HttpStatus.NOT_FOUND);
        }
        aser.guardar(a);
        return ResponseEntity.ok(aser.encontrar(a.getIdAlojamiento()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Alojamiento> eliminarAlojamiento(@PathVariable int id) {
        Alojamiento a = aser.encontrar(id).orElse(null);
        aser.eliminar(id);
        return ResponseEntity.ok(a);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarAlojamiento(@PathVariable int id) {
        Alojamiento a = aser.encontrar(id).orElse(null);
        if (a == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(a);
    }

    @GetMapping(path = "/{idAlojamiento}/deshabilitar")
    public ResponseEntity<?> deshabilitar(@PathVariable int idAlojamiento) {
        Alojamiento alojamiento= aser.encontrar(idAlojamiento).orElse(null);
        if (alojamiento == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El alojamiento no existe"), HttpStatus.NOT_FOUND);
        }
        alojamiento.setEstado(false);
        aser.guardar(alojamiento);
        return ResponseEntity.ok(alojamiento);
    }

    @GetMapping(path = "/{idAlojamiento}/habilitar")
    public ResponseEntity<?> habilitar(@PathVariable int idAlojamiento) {
        Alojamiento alojamiento= aser.encontrar(idAlojamiento).orElse(null);
        if (alojamiento == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El alojamiento no existe"), HttpStatus.NOT_FOUND);
        }
        alojamiento.setEstado(true);
        aser.guardar(alojamiento);
        return ResponseEntity.ok(alojamiento);
    }
    
}
