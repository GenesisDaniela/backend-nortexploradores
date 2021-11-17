package nexp.com.app.service;

import nexp.com.app.model.Persona;
import nexp.com.app.model.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaService {
    public void guardar(Reserva reserva);
    public Optional<Reserva> encontrar(int id);
    public List<Reserva> listar();
}
