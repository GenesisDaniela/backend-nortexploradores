/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.PasajeroDAO;
import com.example.demo.model.Pasajero;
import com.example.demo.service.PasajeroService;
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
public class PasajeroServiceImp implements PasajeroService {

    @Autowired
    PasajeroDAO pDAO;
    
    
    @Override
    @Transactional
    public void guardar(Pasajero pasajero) {
        pDAO.save(pasajero);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        pDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Pasajero> encontrar(int id) {
        return pDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pasajero> listar() {
        return pDAO.findAll();
    }
    
}
