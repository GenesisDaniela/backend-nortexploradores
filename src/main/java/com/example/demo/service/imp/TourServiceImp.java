/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.TourDAO;
import com.example.demo.model.Tour;
import com.example.demo.service.TourService;
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
public class TourServiceImp implements TourService {

    @Autowired
    TourDAO tDAO;
    
    
    @Override
    @Transactional
    public void guardar(Tour tour) {
        tDAO.save(tour);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        tDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Tour> encontrar(int id) {
        return tDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tour> listar() {
        return tDAO.findAll();
    }
    
}
