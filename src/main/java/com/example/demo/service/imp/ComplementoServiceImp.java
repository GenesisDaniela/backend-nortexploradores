package com.example.demo.service.imp;

import com.example.demo.dao.ComplementoDAO;

import com.example.demo.model.Complemento;
import com.example.demo.service.ComplementoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ComplementoServiceImp implements ComplementoService {

    @Autowired
    public ComplementoDAO complementoDAO;

    @Override
    @Transactional
    public void guardar(Complemento ruta) {
        complementoDAO.save(ruta);

    }

    @Override
    @Transactional
    public void eliminar(int id) {
        complementoDAO.deleteById(id);
    }

    @Override
    @Transactional( readOnly = true)
    public Optional<Complemento> encontrar(int id) {
        return complementoDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Complemento> listar() {
        return complementoDAO.findAll();
    }
}
