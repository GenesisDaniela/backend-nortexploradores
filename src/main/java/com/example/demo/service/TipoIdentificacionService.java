/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.TipoIdentificacion;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface TipoIdentificacionService {
    
    public void guardar(TipoIdentificacion tipoIdentificacion);
    public void eliminar(int id);
    public Optional<TipoIdentificacion> encontrar(int id);
    public List<TipoIdentificacion> listar();
            
}
