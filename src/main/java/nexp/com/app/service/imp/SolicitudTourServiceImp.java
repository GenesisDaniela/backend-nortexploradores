package nexp.com.app.service.imp;

import nexp.com.app.dao.SolicitudPaqueteDAO;
import nexp.com.app.model.SolicitudTour;
import nexp.com.app.service.SolicitudTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudTourServiceImp implements SolicitudTourService {

    @Autowired
    SolicitudPaqueteDAO solicitudDAO;

    @Override
    public void guardar(SolicitudTour actividad) {
        solicitudDAO.save(actividad);
    }

    @Override
    public Optional<SolicitudTour> encontrar(int id) {
        return solicitudDAO.findById(id);
    }

    @Override
    public List<SolicitudTour> listar() {
        return solicitudDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        solicitudDAO.deleteById(id);
    }
}
