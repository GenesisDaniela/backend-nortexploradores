/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Alojamiento;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author GenesisDanielaVJ
 */
public interface AlojamientoService {
    
    public void guardar(Alojamiento alojamiento);
    public Optional<Alojamiento> encontrar(int id);
    public List<Alojamiento> listar();
    public void eliminar(int id);
}
