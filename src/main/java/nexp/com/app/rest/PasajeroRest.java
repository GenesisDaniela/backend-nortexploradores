/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.ClientePasajero;
import nexp.com.app.model.Pasajero;
import nexp.com.app.model.Persona;
import nexp.com.app.model.Tour;
import nexp.com.app.negocio.NorteXploradores;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.ClientePasajeroService;
import nexp.com.app.service.PasajeroService;

import java.util.ArrayList;
import java.util.List;

import nexp.com.app.service.PersonaService;
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
@RequestMapping("/pasajero")
@CrossOrigin(origins = "*")
public class PasajeroRest {

    @Autowired
    PasajeroService pser;

    @Autowired
    UsuarioService user;

    @Autowired
    PersonaService perser;

    @Autowired
    ClientePasajeroService clientePasajeroService;

    @PostMapping(path = "/{idUsuario}")
    public ResponseEntity<?> guardar(@RequestBody @Valid Pasajero p, BindingResult br, @PathVariable int idUsuario) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Persona validarP = p.getPersona();

        if(validarP.getApellido()!=null){
            perser.guardar(validarP);
        }

        Persona persona = perser.encontrar(p.getPersona().getIdPersona()).orElse(null);
        if (persona == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "La persona no existe"), HttpStatus.NOT_FOUND);
        }

        p.setPersona(persona);
        Usuario usuario = user.encontrar(idUsuario).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El usuario no existe"), HttpStatus.NOT_FOUND);
        }
//        p.setUsuario(usuario);
        pser.guardar(p);
        return ResponseEntity.ok(p);
    }

    @GetMapping
    public ResponseEntity<List<Pasajero>> getPasajero() {
        return ResponseEntity.ok(pser.listar());
    }

    @GetMapping(path = "/clientes")
    public ResponseEntity<List<?>> getCliente() {
        List<Pasajero> pasajerosClientes = new ArrayList<>();
        NorteXploradores nxs = new NorteXploradores();
        for(Pasajero p: pser.listar()){
            if(p.getEsCotizante() == true ){
                pasajerosClientes.add(p);
            }
        }
        return ResponseEntity.ok(pasajerosClientes);
    }


    @GetMapping(path = "/{idPasajero}")
    public ResponseEntity<Pasajero> encontrar(@PathVariable int idPasajero) {
        return ResponseEntity.ok(pser.encontrar(idPasajero).orElse(null));
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Pasajero p, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Pasajero pasajero = pser.encontrar(p.getIdPasajero()).orElse(null);
        if(pasajero == null){
            return new ResponseEntity("La persona no existe",HttpStatus.NOT_FOUND);
        }
        pser.guardar(pasajero);
        return ResponseEntity.ok(pser.encontrar(p.getIdPasajero()));
    }



}
