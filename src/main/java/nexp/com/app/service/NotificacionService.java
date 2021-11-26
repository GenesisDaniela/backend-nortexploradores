package nexp.com.app.service;

import nexp.com.app.model.Notificacion;

import java.util.List;
import java.util.Optional;

public interface NotificacionService {
    public void guardar(Notificacion notificacion);
    public Optional<Notificacion> encontrar(int id);
    public List<Notificacion> listar();
    public List<Notificacion> listarActivos();
    public void eliminar(int id);
}
