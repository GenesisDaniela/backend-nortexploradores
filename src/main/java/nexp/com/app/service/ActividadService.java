package nexp.com.app.service;

import nexp.com.app.model.Actividad;

import java.util.List;
import java.util.Optional;

public interface ActividadService {
    public Actividad guardar(Actividad actividad);
    public Optional<Actividad> encontrar(int id);
    public List<Actividad> listar();
    public void eliminar(int id);
}
