/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Ruta;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface RutaService {
    
    public void guardar(Ruta ruta);
    public void eliminar(int id);
    public Optional<Ruta> encontrar(int id);
    public List<Ruta> listar();
            
}
