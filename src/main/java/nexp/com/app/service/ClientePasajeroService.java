package nexp.com.app.service;

import nexp.com.app.model.ClientePasajero;
import nexp.com.app.model.Compra;
import nexp.com.app.model.Paquete;

import java.util.List;
import java.util.Optional;

public interface ClientePasajeroService {
    public void guardar(ClientePasajero compra);
    public Optional<ClientePasajero> encontrar(int id);
    public List<ClientePasajero> listar();
}
