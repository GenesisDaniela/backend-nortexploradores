/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.TransaccionDAO;
import com.example.demo.model.Transaccionp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.service.TransaccionService;
import java.util.Optional;

/**
 *
 * @author santi
 */
@Service
public class TransaccionServiceImp implements TransaccionService{

    @Autowired
    public TransaccionDAO pdao;
    
    @Override
    public void guardar(Transaccionp payu) {
        pdao.save(payu);
    }

    @Override
    public List<Transaccionp> listar() {
        return (List)pdao.findAll();
    }

    @Override
    public void eliminar(int id) {
        pdao.deleteById(id);
    }

    @Override
    public Transaccionp encontrar(int id) {
        return pdao.findById(id).get();
    }

    
}
