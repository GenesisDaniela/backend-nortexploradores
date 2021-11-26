package nexp.com.app.service.imp;

import nexp.com.app.dao.ActividadDAO;
import nexp.com.app.model.Actividad;
import nexp.com.app.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadServiceImp implements ActividadService {

    @Autowired
    ActividadDAO actividadDAO;

    @Override
    public Actividad guardar(Actividad actividad) {
        Actividad act = actividadDAO.save(actividad);
        return act;
    }

    @Override
    public Optional<Actividad> encontrar(int id) {
        return actividadDAO.findById(id);
    }

    @Override
    public List<Actividad> listar() {
        return actividadDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        actividadDAO.deleteById(id);
    }
}
