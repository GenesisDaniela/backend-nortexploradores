package nexp.com.app.rest;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.*;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/solicitudtour")
@CrossOrigin(origins = "*")
@Slf4j
public class SolicitudTourRest {

    @Autowired
    SolicitudTourService spaqser;

    @Autowired
    UsuarioService user;

    @Autowired
    PaqueteService pser;

    @Autowired
    NotificacionService nser;

    @Autowired
    TourService tourService;

    @Autowired
    MunicipioService municipioService;

    @Autowired
    PaqueteService paqueteService;


    @GetMapping(path = "/total")
    public ResponseEntity<Integer> cantidadSolicitudes() {
        int total = 0;
      for(SolicitudTour solicitudTour: spaqser.listar()) {
          if(!solicitudTour.getTour().getPaquete().getEstado().equals("ACEPTADO") && !solicitudTour.getTour().getPaquete().getEstado().equals("RECHAZADO")){
              total++;
          }

      }
      return ResponseEntity.ok(total);
    }


    @PostMapping(path = "/{idMunicipio}")
    public ResponseEntity<?> guardar(@RequestBody @Valid SolicitudTour solicitudTour, BindingResult br, @PathVariable int idMunicipio){

        Municipio municipio = municipioService.encontrar(idMunicipio).orElse(null);

        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Tour tour = solicitudTour.getTour();
        if (tour == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El tour no existe"), HttpStatus.NOT_FOUND);
        }
        tour.setEstado("PENDIENTE");


        Paquete paquete = new Paquete();
        paquete.setNombre("Paquete "+municipio.getNombre());
        paquete.setEstado("PENDIENTE");
        paquete.setMunicipio(municipio);

        pser.guardar(paquete);
        tour.setPaquete(paquete);
        tourService.guardar(tour);

        solicitudTour.setFecha(new Date());
        spaqser.guardar(solicitudTour);

        Notificacion notificacion = new Notificacion();
        notificacion.setDescripcion("Has recibo una solicitud de paquete personalizado de: " + solicitudTour.getUsuario().getUsername());
        notificacion.setUsuario(solicitudTour.getUsuario());
        notificacion.setEstado((short)0);
        notificacion.setFecha(solicitudTour.getFecha());
        notificacion.setSolicitudTour(solicitudTour);
        nser.guardar(notificacion);

        return ResponseEntity.ok(solicitudTour);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudTour>> getSolicitudPaquete() {
        return ResponseEntity.ok(spaqser.listar());
    }

    @GetMapping(path = "/{id}/rechazar")
    public ResponseEntity<?> rechazarSolicitud(@PathVariable int id) {
        SolicitudTour s = spaqser.encontrar(id).orElse(null);
        Tour t = s.getTour();
        spaqser.eliminar(s.getIdSolicitud());
        paqueteService.eliminar(t.getPaquete().getIdPaq());
        tourService.eliminar(t.getIdTour());

        return ResponseEntity.ok("");
    }

    @PutMapping()
    public ResponseEntity<?> editar(@RequestBody @Valid SolicitudTour solicitud, BindingResult br){
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        SolicitudTour s = spaqser.encontrar(solicitud.getIdSolicitud()).orElse(null);
        if (s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La solicitud no existe"), HttpStatus.NOT_FOUND);
        }
        spaqser.guardar(solicitud);
    return ResponseEntity.ok(spaqser.encontrar(solicitud.getIdSolicitud()));
    }

    @GetMapping(path = "/{id}/aceptar")
    public ResponseEntity<?> aceptarSolicitudTour(@PathVariable int id) {
        SolicitudTour s = spaqser.encontrar(id).orElse(null);
        if (s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La solicitud no existe"), HttpStatus.NOT_FOUND);
        }
        Tour tour = tourService.encontrar(s.getTour().getIdTour()).orElse(null);
        if (tour == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El tour no existe"), HttpStatus.NOT_FOUND);
        }
        Paquete paquete = pser.encontrar(s.getTour().getPaquete().getIdPaq()).orElse(null);
        if (paquete == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","El paquete no existe"), HttpStatus.NOT_FOUND);
        }
        tour.setEstado("PERSONALIZADO");
        paquete.setEstado("PERSONALIZADO");
    return ResponseEntity.ok(s);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> rechazarSolicitudTour(@PathVariable int id) {
        SolicitudTour s = spaqser.encontrar(id).orElse(null);
        if (s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La solicitud no existe"), HttpStatus.NOT_FOUND);
        }
        tourService.eliminar(s.getTour().getIdTour());
        pser.eliminar(s.getTour().getPaquete().getIdPaq());
        spaqser.eliminar(id);
        return ResponseEntity.ok(s);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarSolicitudPaquete(@PathVariable int id){
        SolicitudTour s = spaqser.encontrar(id).orElse(null);
        if (s == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id","La solicitud no existe"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(s);
    }


}
