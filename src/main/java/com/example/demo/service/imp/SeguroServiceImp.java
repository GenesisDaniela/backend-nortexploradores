/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.SeguroDAO;
import com.example.demo.model.Seguro;
import com.example.demo.service.SeguroService;
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
public class SeguroServiceImp implements SeguroService {

    @Autowired
    SeguroDAO sDAO;
    
    
    @Override
    @Transactional
    public void guardar(Seguro seguro) {
        sDAO.save(seguro);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        sDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Seguro> encontrar(int id) {
        return sDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seguro> listar() {
        return sDAO.findAll();
    }
    
}
