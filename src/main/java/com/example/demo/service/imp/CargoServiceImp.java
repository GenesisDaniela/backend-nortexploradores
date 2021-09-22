/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.CargoDAO;
import com.example.demo.model.Cargo;
import com.example.demo.service.CargoService;
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
public class CargoServiceImp implements CargoService {

    @Autowired
    CargoDAO cDAO;
    
    
    @Override
    @Transactional
    public void guardar(Cargo cargo) {
        cDAO.save(cargo);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Cargo> encontrar(int id) {
        return cDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cargo> listar() {
        return cDAO.findAll();
    }
    
    @Override
    @Transactional
    public void eliminar(int id) {
        cDAO.deleteById(id);
    }
}
