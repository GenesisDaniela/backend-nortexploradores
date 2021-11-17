package nexp.com.app.rest;

import nexp.com.app.model.Tour;
import nexp.com.app.model.Transporte;
import nexp.com.app.model.TransporteTour;
import nexp.com.app.service.TourService;
import nexp.com.app.service.TransporteService;
import nexp.com.app.service.TransporteTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transportetour")
@CrossOrigin(origins = "*")
public class TransporteTourRest {

    @Autowired
    TransporteTourService transporteTourService;

    @Autowired
    TourService tourService;

    @Autowired
    TransporteService transporteService;

    @GetMapping
    public ResponseEntity<List<TransporteTour>> getTransporteTour() {
        return ResponseEntity.ok(transporteTourService.listar());
    }


}
