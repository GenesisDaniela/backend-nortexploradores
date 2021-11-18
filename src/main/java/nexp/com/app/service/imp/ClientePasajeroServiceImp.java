package nexp.com.app.service.imp;

import nexp.com.app.dao.ClientePasajeroDAO;
import nexp.com.app.model.ClientePasajero;
import nexp.com.app.service.ClientePasajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientePasajeroServiceImp implements ClientePasajeroService {

    @Autowired
    public ClientePasajeroDAO clientePasajeroDAO;

    @Override
    public void guardar(ClientePasajero clientePasajero) {
        clientePasajeroDAO.save(clientePasajero);
    }

    @Override
    public Optional<ClientePasajero> encontrar(int id) {
        return clientePasajeroDAO.findById(id);
    }

    @Override
    public List<ClientePasajero> listar() {
        return clientePasajeroDAO.findAll();
    }
}

