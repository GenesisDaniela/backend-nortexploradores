/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Pasajero;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface PasajeroService {
        
    public void guardar(Pasajero pasajero);
    public void eliminar(int id);
    public Optional<Pasajero> encontrar(int id);
    public List<Pasajero> listar();
}
