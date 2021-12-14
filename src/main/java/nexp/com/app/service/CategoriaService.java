package nexp.com.app.service;

import nexp.com.app.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    public void guardar(Categoria categoria);
    public Optional<Categoria> encontrar(int id);
    public List<Categoria> listar();
    public void eliminar(int id);
}
