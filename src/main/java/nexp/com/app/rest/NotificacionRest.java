/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;

import nexp.com.app.model.Notificacion;
import nexp.com.app.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/notificacion")
@CrossOrigin(origins = "*")
public class NotificacionRest {
    
    @Autowired
    NotificacionService nser;

    @GetMapping
    public ResponseEntity<List<Notificacion>> getNotificacion() {
        return ResponseEntity.ok(nser.listar());
    }

}
