/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.DescuentoDAO;
import com.example.demo.model.Descuento;
import com.example.demo.service.DescuentoService;
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
public class DescuentoServiceImp implements DescuentoService {

    @Autowired
    DescuentoDAO dDAO;
    
    
    @Override
    @Transactional
    public void guardar(Descuento descuento) {
        dDAO.save(descuento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Descuento> listar() {
        return dDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        dDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Descuento> encontrar(int id) {
        return dDAO.findById(id);
    }
    
}
