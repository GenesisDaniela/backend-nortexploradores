/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Transporte;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface TransporteService {
    
    public void guardar(Transporte transporte);
    public void eliminar(String id);
    public Optional<Transporte> encontrar(String id);
    public List<Transporte> listar();
            
}
