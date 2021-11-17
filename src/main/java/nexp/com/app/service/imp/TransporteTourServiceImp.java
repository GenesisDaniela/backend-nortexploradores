package nexp.com.app.service.imp;

import nexp.com.app.dao.TransporteTourDAO;
import nexp.com.app.service.TransporteTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class TransporteTourServiceImp implements TransporteTourService {
    @Autowired
    TransporteTourDAO pDAO;


    @Override
    @Transactional
    public void guardar(nexp.com.app.model.TransporteTour TransporteTour) {
        pDAO.save(TransporteTour);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<nexp.com.app.model.TransporteTour> encontrar(int id) {
        return pDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<nexp.com.app.model.TransporteTour> listar() {
        return pDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        pDAO.deleteById(id);
    }
}
