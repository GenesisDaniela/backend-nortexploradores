/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.ClientePasajero;
import nexp.com.app.model.Pasajero;
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
 * @author Santi & GenesisDanielaVJ
 */
@RestController
@RequestMapping("/pasajero")
@CrossOrigin(origins = "*")
@Slf4j
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
        Usuario usuario = user.encontrar(idUsuario).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El usuario no existe"), HttpStatus.NOT_FOUND);
        }
        pser.guardar(p);
        ClientePasajero clientePasajero =  new ClientePasajero();
        clientePasajero.setPasajero(p);
        clientePasajero.setUsuario(usuario);
        clientePasajeroService.guardar(clientePasajero);
        return ResponseEntity.ok(p);
    }

    @GetMapping
    public ResponseEntity<List<Pasajero>> getPasajero() {
        return ResponseEntity.ok(pser.listar());
    }

    @GetMapping(path = "/clientes")
    public ResponseEntity<List<?>> getCliente() {
        List<Pasajero> pasajero = new ArrayList<>();
        List<Usuario> usuario = new ArrayList<>();
        List<Object> clientesUsuario = new ArrayList<>();

        for(Pasajero p: pser.listar()){
            if(p.getEsCotizante()){
                List<ClientePasajero> pasajeros = (List)p.clientePasajeroCollection();
                if(pasajeros.size()>0){
                    pasajero.add(p);
                    usuario.add(pasajeros.get(0).getUsuario());
                }
            }
        }
        clientesUsuario.add(pasajero);
        clientesUsuario.add(usuario);
        return ResponseEntity.ok(clientesUsuario);
    }


    @GetMapping(path = "/{idPasajero}")
    public ResponseEntity<Pasajero> encontrar(@PathVariable int idPasajero) {
        return ResponseEntity.ok(pser.encontrar(idPasajero).orElse(null));
    }

    @GetMapping(path = "/{idPasajero}/deshabilitar")
    public ResponseEntity<?> deshabilitar(@PathVariable int idPasajero) {
        Pasajero pasajero = pser.encontrar(idPasajero).orElse(null);

        for(ClientePasajero clientePasajero:pasajero.clientePasajeroCollection()){
            log.info(clientePasajero.getUsuario().getUsername());
            if(idPasajero == clientePasajero.getPasajero().getIdPasajero()){
               Usuario u= clientePasajero.getUsuario();
               u.setEstado(false);
               user.guardar(u);
                return ResponseEntity.ok(u);
            }
        }
        return (ResponseEntity<?>) ResponseEntity.notFound();
    }

    @GetMapping(path = "/{idPasajero}/habilitar")
    public ResponseEntity<?> habilitar(@PathVariable int idPasajero) {
        Pasajero pasajero = pser.encontrar(idPasajero).orElse(null);

        List<ClientePasajero> pasajeros = clientePasajeroService.listar();

        for(ClientePasajero clientePasajero : pasajeros){
            log.info(clientePasajero.toString()+"=====================");
            log.info(idPasajero+"=====================");
            log.info(clientePasajero.getPasajero().getIdPasajero()+"=====================");

            if(idPasajero == clientePasajero.getPasajero().getIdPasajero()){
                Usuario u= clientePasajero.getUsuario();
                u.setEstado(true);
                user.guardar(u);
                return ResponseEntity.ok(u);
            }
        }
        return new ResponseEntity("No encontrado",HttpStatus.NOT_FOUND);
    }
}
