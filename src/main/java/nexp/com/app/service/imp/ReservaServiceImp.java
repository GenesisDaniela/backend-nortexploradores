package nexp.com.app.service.imp;

import nexp.com.app.dao.ReservaDAO;
import nexp.com.app.model.Reserva;
import nexp.com.app.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImp implements ReservaService {

    @Autowired
    ReservaDAO reservaDAO;


    @Override
    public void guardar(Reserva reserva) {
        reservaDAO.save(reserva);
    }


    @Override
    public Optional<Reserva> encontrar(int id) {
        return reservaDAO.findById(id);
    }

    @Override
    public List<Reserva> listar() {
        return reservaDAO.findAll();
    }
}
