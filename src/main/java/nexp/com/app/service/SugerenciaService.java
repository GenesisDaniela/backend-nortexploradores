package nexp.com.app.service;

import nexp.com.app.model.Sugerencia;

import java.util.List;
import java.util.Optional;

public interface SugerenciaService {
    public void guardar(Sugerencia segurencia);
    public Optional<Sugerencia> encontrar(int id);
    public List<Sugerencia> listar();
    public void eliminar(int id);
}
