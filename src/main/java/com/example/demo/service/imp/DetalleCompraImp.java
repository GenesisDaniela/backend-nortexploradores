/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.DetalleCompraDAO;
import com.example.demo.model.DetalleCompra;
import com.example.demo.service.DetalleCompraService;
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
public class DetalleCompraImp implements DetalleCompraService{
    
    @Autowired
    DetalleCompraDAO dcDAO;
    
    
    @Override
    @Transactional
    public void guardar(DetalleCompra detalleCompra) {
        dcDAO.save(detalleCompra);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<DetalleCompra> encontrar(int id) {
        return dcDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleCompra> listar() {
        return dcDAO.findAll();
    }
    
}
