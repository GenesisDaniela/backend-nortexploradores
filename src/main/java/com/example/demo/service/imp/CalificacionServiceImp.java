/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.CalificacionDAO;
import com.example.demo.model.Calificacion;
import com.example.demo.model.Compra;
import com.example.demo.service.CalificacionService;
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
public class CalificacionServiceImp implements CalificacionService {

    @Autowired
    CalificacionDAO cDAO;
    
    
    @Override
    @Transactional
    public void guardar(Calificacion calificacion) {
        cDAO.save(calificacion);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        cDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Calificacion> encontrar(int id) {
        return cDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Calificacion> listar() {
        return cDAO.findAll();
    }

}
