package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Actividad;
import nexp.com.app.model.Paquete;
import nexp.com.app.service.ActividadService;
import nexp.com.app.service.PaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/actividad")
@CrossOrigin(origins = "*")
@Slf4j
public class ActividadRest {

    @Autowired
    ActividadService actser;

    @Autowired
    PaqueteService paqueteService;




    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Actividad actividad, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Paquete paquete = paqueteService.encontrar(actividad.getPaquete().getIdPaq()).orElse(null);
        actividad.setPaquete(paquete);
        actser.guardar(actividad);
        return ResponseEntity.ok(actividad);
    }

    @GetMapping
    public ResponseEntity<List<Actividad>> getActividad() {
        return ResponseEntity.ok(actser.listar());
    }

    @GetMapping(path = "/{idActividad}/deshabilitar")
    public ResponseEntity<?> deshabilitarActividad(@PathVariable int idActividad) {
        Actividad actividad = actser.encontrar(idActividad).orElse(null);
        if (actividad == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La actividad no existe"), HttpStatus.NOT_FOUND);
        }
        actividad.setEstado(false);
        actser.guardar(actividad);
        return ResponseEntity.ok(actividad);
    }

    @GetMapping(path = "/{idActividad}/habilitar")
    public ResponseEntity<?> habilitarActividad(@PathVariable int idActividad) {
        Actividad actividad = actser.encontrar(idActividad).orElse(null);
        if (actividad == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La actividad no existe"), HttpStatus.NOT_FOUND);
        }
        actividad.setEstado(true);
        actser.guardar(actividad);
        return ResponseEntity.ok(actividad);
    }

    @PutMapping()
    public ResponseEntity<?> editarActividad(@RequestBody @Valid Actividad actividad, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Actividad a = actser.encontrar(actividad.getIdActividad()).orElse(null);
        if (a == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La actividad no existe"), HttpStatus.NOT_FOUND);
        }
        actser.guardar(actividad);
    return ResponseEntity.ok(actser.encontrar(actividad.getIdActividad()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarActividad(@PathVariable int id){
        Actividad a = actser.encontrar(id).orElse(null);
        if(a==null){
            return new ResponseEntity<>("Actividad con id:"+id+" no existe", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(a);
    }


}
