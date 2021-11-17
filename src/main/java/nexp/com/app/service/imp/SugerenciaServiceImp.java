package nexp.com.app.service.imp;

import nexp.com.app.dao.SugerenciaDAO;
import nexp.com.app.model.Sugerencia;
import nexp.com.app.service.SugerenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SugerenciaServiceImp implements SugerenciaService {

    @Autowired
    SugerenciaDAO segurenciaDAO;

    @Override
    public void guardar(Sugerencia segurencia) {
        segurenciaDAO.save(segurencia);
    }

    @Override
    public Optional<Sugerencia> encontrar(int id) {
        return segurenciaDAO.findById(id);
    }

    @Override
    public List<Sugerencia> listar() {
        return segurenciaDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        segurenciaDAO.deleteById(id);
    }
}
