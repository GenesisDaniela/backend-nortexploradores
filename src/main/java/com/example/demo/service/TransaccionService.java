/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Transaccionp;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface TransaccionService {
    
    public void guardar(Transaccionp transaccion);
    public Transaccionp encontrar(int id);
    public List<Transaccionp> listar();
    public void eliminar(int id);
    
}
