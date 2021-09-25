package com.example.demo.service.imp;

import com.example.demo.dao.ReservaDAO;
import com.example.demo.model.Reserva;
import com.example.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ReservaServiceImp implements ReservaService{

    @Autowired
    public ReservaDAO reservaDAO;

    @Override
    @Transactional
    public void guardar(Reserva ruta) {
        reservaDAO.save(ruta);

    }

    @Override
    @Transactional
    public void eliminar(int id) {
        reservaDAO.deleteById(id);
    }

    @Override
    @Transactional( readOnly = true)
    public Optional<Reserva> encontrar(int id) {
        return reservaDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> listar() {
        return reservaDAO.findAll();
    }
}
