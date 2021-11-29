/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Municipio;
import nexp.com.app.model.Paquete;
import nexp.com.app.service.MunicipioService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Santi & GenesisDanielaVJ
 */
@RestController
@RequestMapping("/municipio")
@CrossOrigin(origins = "*")
public class MunicipioRest {
    
    @Autowired
    MunicipioService mser;

    @GetMapping
    public ResponseEntity<List<Municipio>> getMunicipio() {
        List<Municipio> municipiosActivos = new ArrayList<>();
        for(Municipio muni: mser.listar()){
            if(muni.getEstado() == true)
                municipiosActivos.add(muni);
        }
        return ResponseEntity.ok(municipiosActivos);
    }
    @GetMapping(path= "/listar")
    public ResponseEntity<List<Municipio>> getMunicipioTot() {
        return ResponseEntity.ok(mser.listar());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarMunicipio(@PathVariable int id) {
        Municipio m = mser.encontrar(id).orElse(null);
        if (m == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id del municipio"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(m);
    }

    @GetMapping(path = "/{idMunicipio}/deshabilitar")
    public ResponseEntity<?> deshabilitarMunicipio(@PathVariable int idMunicipio) {
        Municipio municipio= mser.encontrar(idMunicipio).orElse(null);
        if (municipio == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El municipio no existe"), HttpStatus.NOT_FOUND);
        }
        municipio.setEstado(false);
        mser.guardar(municipio);
        return ResponseEntity.ok(municipio);
    }

    @GetMapping(path = "/{idMunicipio}/habilitar")
    public ResponseEntity<?> habilitarMunicipio(@PathVariable int idMunicipio) {
        Municipio municipio= mser.encontrar(idMunicipio).orElse(null);
        if (municipio == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El municipio no existe"), HttpStatus.NOT_FOUND);
        }
        municipio.setEstado(true);
        mser.guardar(municipio);
        return ResponseEntity.ok(municipio);
    }

    

}
