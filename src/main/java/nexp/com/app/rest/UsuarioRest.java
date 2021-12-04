/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.*;
import nexp.com.app.negocio.NorteXploradores;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import nexp.com.app.service.ClientePasajeroService;
import nexp.com.app.service.CompraService;
import nexp.com.app.service.PasajeroService;
import nexp.com.app.service.PersonaService;
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
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@Slf4j
public class UsuarioRest {

    @Autowired
    UsuarioService user;

    @Autowired
    CompraService compraService;

    @Autowired
    PasajeroService pasajeroService;

    @Autowired
    PersonaService personaService;

    @Autowired
    ClientePasajeroService clientePasajeroService;

    NorteXploradores nexp = new NorteXploradores();

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
        return ResponseEntity.ok(user.listar());
    }

    //revisar
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable int id) {

        Usuario u = user.encontrar(id).orElse(null);
        if(u == null){
            return new ResponseEntity("El complemento no fue encontrado", HttpStatus.NOT_FOUND);
        }
        user.eliminar(id);

        return ResponseEntity.ok(u);
    }

    @GetMapping(path = "/{username}/username")
    public ResponseEntity<?> usuarioPorUsername(@PathVariable String username){
        Usuario u = user.getByNombreUsuario(username).orElse(null);

        if(u==null){
            return new ResponseEntity<>("Usuario no encontrado",HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(u);
    }

    @GetMapping(path = "/{idUsuario}/deshabilitar")
    public ResponseEntity<?> deshabilitarUsuario(@PathVariable int idUsuario) {
        Usuario usuario= user.encontrar(idUsuario).orElse(null);
        if (usuario == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        usuario.setEstado(false);
        user.guardar(usuario);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping(path = "/{idUsuario}/habilitar")
    public ResponseEntity<?> habilitarUsuario(@PathVariable int idUsuario) {
        Usuario usuario= user.encontrar(idUsuario).orElse(null);
        if (usuario == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        usuario.setEstado(true);
        user.guardar(usuario);
        return ResponseEntity.ok(usuario);
    }


    @GetMapping(path = "/{id}/paquetes")
    public ResponseEntity<List<Compra>> paquetesPorUsuario(@PathVariable int id){
        return ResponseEntity.ok((List)user.encontrar(id).get().compraCollection());
    }

    @GetMapping(path = "/{id}/paquetesComprados")
    public ResponseEntity<?> paquetesCompradosPorUsuario(@PathVariable int id){
        List<Compra> comprasTotales = (List)user.encontrar(id).get().compraCollection();
        if(comprasTotales == null){
            return new ResponseEntity<>("El usuario no tiene compras asociadas",HttpStatus.NOT_FOUND);
        }
        List<Compra> compraDeUsuario = nexp.paquetesComprados(comprasTotales);

        return ResponseEntity.ok(compraDeUsuario);
    }

    @PostMapping(path = "/{idUsuario}/pasajeros")
    public ResponseEntity<?> guardarPasajerosDeUsuario(@RequestBody List<Pasajero> pasajeros,@PathVariable int idUsuario){
        Usuario us = user.encontrar(idUsuario).get();
        List<Persona> personas = personaService.listar();
        List<Pasajero> pasajerosAdd = new ArrayList<>();
        List<Pasajero> pasajerosAgregados = new ArrayList<>();
        int x=0;
        for(Pasajero p: pasajeros){
            Persona personaP = personaService.encontrar(p.getPersona().getIdPersona()).orElse(null);
            if(personaP==null){
                personaService.guardar(p.getPersona()); // guardo a la persona no existente
                Pasajero pasajero = pasajeroService.guardar(p);
                pasajerosAdd.add(pasajero); // guardo al pasasajero tampoco existente
                pasajerosAgregados.add(pasajero);

                ClientePasajero clientePasajero = new ClientePasajero(); // lo asocio al cliente
                clientePasajero.setPasajero(p);
                clientePasajero.setUsuario(us);
                clientePasajeroService.guardar(clientePasajero);
            }else{
                Pasajero pasajeroAsociadoAPersona = personaP.pasajero();
                if(p.getIdPasajero()==null && pasajeroAsociadoAPersona==null){
                    Pasajero pasajero = pasajeroService.guardar(p);
                    pasajerosAdd.add(pasajero);
                    pasajerosAgregados.add(pasajero);
                    ClientePasajero clientePasajero = new ClientePasajero(); // lo asocio al cliente
                    clientePasajero.setPasajero(p);
                    clientePasajero.setUsuario(us);
                    clientePasajeroService.guardar(clientePasajero);
                }else{
                    if(p.getIdPasajero()==null){
                        p = pasajeroService.encontrar(pasajeroAsociadoAPersona.getIdPasajero()).get();
                        List<ClientePasajero> pc =(List) p.clientePasajeroCollection();
                            if(p.getEsCotizante() && x>0){
                                return new ResponseEntity<ObjectError>(new ObjectError("Pasajero","No puedes llevar clientes registrados"), HttpStatus.NOT_FOUND);
                            }

                            if(p.getEsCotizante()) x++;

                            if(!nexp.existeUsuario(pc, us)){ //esta asociado a otro cliente
                                ClientePasajero clientePasajero = new ClientePasajero(); // lo asocio al cliente
                                clientePasajero.setPasajero(p);
                                clientePasajero.setUsuario(us);
                                clientePasajeroService.guardar(clientePasajero);
                                pasajerosAgregados.add(p);
                            }
                    }
                }
            }
        }
        return ResponseEntity.ok(pasajerosAgregados);
    }


    @GetMapping(path = "/{id}/pasajeros")
    public ResponseEntity<List<Pasajero>> pasajerosPorUsuario(@PathVariable int id){
        List<Pasajero> pasajeros = new ArrayList<>();
        for(ClientePasajero p: user.encontrar(id).get().clientePasajeroCollection()){
            pasajeros.add(p.getPasajero());
        }
        return ResponseEntity.ok((pasajeros));
    }

    @GetMapping(path = "/{id}/pasajerospersonas")
    public ResponseEntity<List<Persona>> pasajerosPersonaPorUsuario(@PathVariable int id){
        List<Persona> personas = new ArrayList<>();
        for(ClientePasajero p: user.encontrar(id).get().clientePasajeroCollection()){
            personas.add(p.getPasajero().getPersona());
        }

        return ResponseEntity.ok(personas);
    }

//    @GetMapping(path = "/{idPasajero}/deshabilitar")
//    public ResponseEntity<?> deshabilitarCliente(@PathVariable int id) {
//        ClientePasajero clientePasajero = new ClientePasajero();
//
//    }


        @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarUsuario(@PathVariable int id) {
        Usuario u = user.encontrar(id).orElse(null);
        if (u == null) {
            //return new ResponseEntity<String>("El usuario con id: " + id + " no existe", HttpStatus.NOT_FOUND);
            return new ResponseEntity<ObjectError>(new ObjectError("id","No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(u);
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Usuario u, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = user.encontrar(u.getId_Usuario()).orElse(null);
        if(usuario ==null){
            return new ResponseEntity("Usuario no existe",HttpStatus.NOT_FOUND);
        }
        user.guardar(u);
        return ResponseEntity.ok(user.encontrar(u.getId_Usuario()));
    }

    @GetMapping(path = "/{idUsuario}/comprasReservadas")
    public ResponseEntity<?> comprasReservadas(@PathVariable int idUsuario){
        Usuario u = user.encontrar(idUsuario).get();
        List<Compra> paquetesReservado = nexp.paquetesReservados((List)u.compraCollection());

        if(paquetesReservado==null || paquetesReservado.size()==0){
            return new ResponseEntity("No tiene reservas",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(paquetesReservado);
    }

    @GetMapping(path = "/{idUsuario}/tourscomprados")
    public ResponseEntity<?> tourComprado(@PathVariable int idUsuario){
        Usuario u = user.encontrar(idUsuario).get();
        List<Tour> tourComprados = nexp.tourComprados((List)u.compraCollection());
        return ResponseEntity.ok(tourComprados);
    }
    }
