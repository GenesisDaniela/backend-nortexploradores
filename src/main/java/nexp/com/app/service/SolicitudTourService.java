package nexp.com.app.service;

import nexp.com.app.model.SolicitudTour;

import java.util.List;
import java.util.Optional;

public interface SolicitudTourService {
    public void guardar(SolicitudTour solictud);
    public Optional<SolicitudTour> encontrar(int id);
    public List<SolicitudTour> listar();
    public void eliminar(int id);
}
