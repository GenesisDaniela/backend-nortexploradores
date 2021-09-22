/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.PaqueteDAO;
import com.example.demo.model.Paquete;
import com.example.demo.service.PaqueteService;
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
public class PaqueteServiceImp implements PaqueteService {

    @Autowired
    PaqueteDAO pDAO;
    
    
    @Override
    @Transactional
    public void guardar(Paquete paquete) {
        pDAO.save(paquete);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        pDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Paquete> encontrar(int id) {
        return pDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paquete> listar() {
        return pDAO.findAll();
    }
    
}
