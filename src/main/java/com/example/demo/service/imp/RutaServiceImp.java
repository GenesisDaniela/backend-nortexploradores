/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.RutaDAO;
import com.example.demo.model.Ruta;
import com.example.demo.service.RutaService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Santi & Dani
 */
@Service
public class RutaServiceImp implements RutaService {

    @Autowired
    RutaDAO rDAO;
    
    
    @Override
    @Transactional
    public void guardar(Ruta ruta) {
        rDAO.save(ruta);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        rDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Ruta> encontrar(int id) {
        return rDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ruta> listar() {
        return rDAO.findAll();
    }
    
}
