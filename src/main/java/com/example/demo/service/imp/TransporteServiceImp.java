/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.TransporteDAO;
import com.example.demo.model.Transporte;
import com.example.demo.service.TransporteService;
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
public class TransporteServiceImp implements TransporteService {

    @Autowired
    TransporteDAO tDAO;
    
    
    @Override
    @Transactional
    public void guardar(Transporte transporte) {
        tDAO.save(transporte);
    }

    @Override
    @Transactional
    public void eliminar(String id) {
        tDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Transporte> encontrar(String id) {
        return tDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transporte> listar() {
        return tDAO.findAll();
    }
    
}
