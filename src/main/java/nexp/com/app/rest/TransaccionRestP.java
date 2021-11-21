/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Transaccionp;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.TransaccionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author santi
 */
@RestController
@RequestMapping(value = "/transaccion")
@CrossOrigin(origins = "*")
public class TransaccionRestP {
    @Autowired
    public TransaccionService transaccionService;
    
    @Autowired
    public UsuarioService user;
    
    
    @GetMapping
    public ResponseEntity<List<Transaccionp>> transacciones(){
       return new ResponseEntity(transaccionService.listar(), HttpStatus.OK);
    }

    @GetMapping(path = "/{idTransaccion}")
    public ResponseEntity<?> encontrar(@PathVariable String idTransaccion){
       Transaccionp transaccionp = this.transaccionService.encontrar(idTransaccion);
        if (transaccionp == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "No existe el id"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(transaccionp, HttpStatus.OK);
    }
}
