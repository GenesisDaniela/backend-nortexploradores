/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.TipoIdentificacionDAO;
import com.example.demo.model.TipoIdentificacion;
import com.example.demo.service.TipoIdentificacionService;
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
public class TipoIdentificacionServiceImp implements TipoIdentificacionService {

    @Autowired
    TipoIdentificacionDAO tDAO;
    
    
    @Override
    @Transactional
    public void guardar(TipoIdentificacion tipoIdentificacion) {
        tDAO.save(tipoIdentificacion);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        tDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<TipoIdentificacion> encontrar(int id) {
        return tDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoIdentificacion> listar() {
        return tDAO.findAll();
    }
    
}
