/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Descuento;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author GenesisDanielaVJ
 */
public interface DescuentoService {
    
    public void guardar(Descuento descuento);
    public Optional<Descuento> encontrar(int id);
    public List<Descuento> listar();
    public void eliminar(int id);
}
