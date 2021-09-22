/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.EventoDAO;
import com.example.demo.model.Cargo;
import com.example.demo.model.Evento;
import com.example.demo.service.EventoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GenesisDanielaVJ
 */
@Service
public class EventoServiceImp implements EventoService {

    @Autowired
    EventoDAO eDAO;
    
    @Override
    @Transactional
    public void guardar(Evento evento) {
        eDAO.save(evento);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Evento> encontrar(int id) {
        return eDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evento> listar() {
        return eDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        eDAO.deleteById(id);
    }

    
}
