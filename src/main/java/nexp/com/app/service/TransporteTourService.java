package nexp.com.app.service;

import nexp.com.app.model.TransporteTour;

import java.util.List;
import java.util.Optional;

public interface TransporteTourService {
    public void guardar(nexp.com.app.model.TransporteTour transporteTour);
    public void eliminar(int id);
    public Optional<nexp.com.app.model.TransporteTour> encontrar(int id);
    public List<nexp.com.app.model.TransporteTour> listar();
}
