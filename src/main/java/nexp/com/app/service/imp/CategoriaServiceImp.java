package nexp.com.app.service.imp;

import nexp.com.app.dao.CategoriaDAO;
import nexp.com.app.model.Categoria;
import nexp.com.app.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImp implements CategoriaService {

    @Autowired
    CategoriaDAO categoriaDAO;

    @Override
    public void guardar(Categoria categoria) {
        Categoria act = categoriaDAO.save(categoria);
    }

    @Override
    public Optional<Categoria> encontrar(int id) {
        return categoriaDAO.findById(id);
    }

    @Override
    public List<Categoria> listar() {
        return categoriaDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        categoriaDAO.deleteById(id);
    }
}
