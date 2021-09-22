/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Tour;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface TourService {
    
    public void guardar(Tour tour);
    public void eliminar(int id);
    public Optional<Tour> encontrar(int id);
    public List<Tour> listar();
            
}
