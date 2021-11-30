/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.rest;
import nexp.com.app.model.*;
import nexp.com.app.negocio.NorteXploradores;
import nexp.com.app.service.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Santi & Dani
 */
@RestController
@RequestMapping("/tour")
@CrossOrigin(origins = "*")
public class TourRest {

    @Autowired
    TourService tser;

    @Autowired
    SeguroService sser;

    @Autowired
    EmpleadoService eser;

    @Autowired
    PaqueteService pser;

    @Autowired
    TransporteTourService transporteTourService;

    @Autowired
    TourService tourService;

    @Autowired
    TransporteService transporteService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody @Valid Tour t, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Seguro s = sser.encontrar(t.getSeguro().getIdSeguro()).orElse(null);
        if (s == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El seguro no existe"), HttpStatus.NOT_FOUND);
        }
        t.setSeguro(s);

        Empleado e = eser.encontrar(t.getEmpleado().getIdEmpleado()).orElse(null);
        if (e == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        t.setEmpleado(e);
        Paquete p = pser.encontrar(t.getPaquete().getIdPaq()).orElse(null);
        if(p!=null){
            t.setPaquete(p);
        }

        tser.guardar(t);
        return ResponseEntity.ok(t);
    }

    @GetMapping
    public ResponseEntity<List<Tour>> getTour() {
        return ResponseEntity.ok(tser.listar());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> encontrarTour(@PathVariable int id) {
        Tour tour = tser.encontrar(id).orElse(null);
        if (tour == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "No existe el id"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tour);
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody @Valid Tour t, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<List<ObjectError>>(br.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Tour tour = tser.encontrar(t.getIdTour()).orElse(null);
        if (tour == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El tour no existe"), HttpStatus.NOT_FOUND);
        }
        tser.guardar(t);
        return ResponseEntity.ok(tser.encontrar(t.getIdTour()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminarTour(@PathVariable int id) {
        Tour t = tser.encontrar(id).orElse(null);
        if (t == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "No existe la ruta"), HttpStatus.NOT_FOUND);
        }
        tser.eliminar(id);
        return ResponseEntity.ok(t);
    }

    @GetMapping(path = "/activos")
    public ResponseEntity<?> toursActivos() {
        return ResponseEntity.ok(this.tser.listarActivos());
    }

    @GetMapping(path = "/{idTour}/" +
            "")
    public ResponseEntity<?> deshabilitarTour(@PathVariable int idTour) {
        Tour tour = tser.encontrar(idTour).orElse(null);
        if (tour == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        tour.setEstado("INACTIVO");
        tser.guardar(tour);
        return ResponseEntity.ok(tour);
    }

    @GetMapping(path = "/{idTour}/habilitar")
    public ResponseEntity<?> habilitarTour(@PathVariable int idTour) {
        Tour tour = tser.encontrar(idTour).orElse(null);
        if (tour == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        tour.setEstado("ACTIVO");
        tser.guardar(tour);
        return ResponseEntity.ok(tour);
    }

    @GetMapping(path = "/estadia")
    public ResponseEntity<?> obtenerPaqEstadia() {
        List<Tour> tours = (tser.listar());
        List<Tour> tourEstadia = new ArrayList<>();
        for (Tour t: tours) {
            if( t.getEstado().equals("ACTIVO") && t.getPaquete().getEstado().equals("ACTIVO") && t.getFechaLlegada().getDay() != t.getFechaSalida().getDay() ){
                tourEstadia.add(t);
            }
        }
        return ResponseEntity.ok(tourEstadia);
    }

    @GetMapping(path = "/pasadia")
    public ResponseEntity<?> obtenerPaqPasadia() {
        List<Tour> tours = (tser.listar());
        List<Tour> tourPasadia = new ArrayList<>();
        for (Tour t: tours) {
            if(t.getEstado().equals("ACTIVO") && t.getPaquete().getEstado().equals("ACTIVO") && t.getFechaLlegada().getDay() == t.getFechaSalida().getDay() ){
                tourPasadia.add(t);
            }
        }
        return ResponseEntity.ok(tourPasadia);
    }

    @GetMapping(path = "/{idTour}/transportetour")
    public ResponseEntity<?> getTransporteTour(@PathVariable int idTour) {
        Tour tour = tser.encontrar(idTour).orElse(null);

        if (tour == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El empleado no existe"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tour.transporteTourCollection());
    }

    @GetMapping(path = "/tourDelMes")
    public ResponseEntity<?> obtenerTourMes() {
        List<Tour> tours = tser.listar();
        Tour masVendido = tours.get(0);
        Date fechaActual = new Date();
        for (Tour t : tours) {
            if (masVendido != null && t.getFechaSalida().getYear() == fechaActual.getYear() && (fechaActual.getMonth() - 1 == t.getFechaSalida().getMonth())) {
                if (t.getCantCupos() != null && masVendido.getCantCupos() != null && t.getCantCupos() > masVendido.getCantCupos())
                    masVendido = t;
            }
        }
        if(masVendido == null){
            return new ResponseEntity<ObjectError>(new ObjectError("id", "No existe tour del mes, tours vacios"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(masVendido);
    }

    @GetMapping(path = "/{idTour}/pasajeros")
    public ResponseEntity<?> pasajerosDeTour(@PathVariable int idTour) {
        Tour tour = tser.encontrar(idTour).orElse(null);

        List<Pasajero> pasajeros = new ArrayList<>();

        for (Compra c : tour.compraCollection()) {
            for (DetalleCompra dc : c.detalleCompraCollection()) {
                pasajeros.add(dc.getPasajero());
            }
        }
        return ResponseEntity.ok(pasajeros);
    }

    @GetMapping(path = "/{idTour}/transporte")
    public ResponseEntity<?>transporteDeTour(@PathVariable int idTour){
        Tour tour = tser.encontrar(idTour).orElse(null);
        List<TransporteTour> tours=(List) tour.transporteTourCollection();

        return ResponseEntity.ok(tours.get(tours.size()-1).getTransporte());
    }

    @PostMapping(path = "/{idTour}/{idTransporte}")
    public ResponseEntity<?> guardarTransporteTour(@PathVariable int idTour, @PathVariable String idTransporte) {
        Tour tour = tourService.encontrar(idTour).orElse(null);
        if (tour == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "El tour no existe"), HttpStatus.NOT_FOUND);
        }
        Transporte transporte = transporteService.encontrar(idTransporte).orElse(null);
        if (transporte == null) {
            return new ResponseEntity<ObjectError>(new ObjectError("id", "No existe transporte"), HttpStatus.NOT_FOUND);
        }
        TransporteTour transporteTour = new TransporteTour();
        transporteTour.setTransporte(transporte);
        transporteTour.setTour(tour);
        transporteTourService.guardar(transporteTour);
        return ResponseEntity.ok(transporteTourService.listar());
    }


}
