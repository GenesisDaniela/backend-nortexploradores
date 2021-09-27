/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Compra;
import com.example.demo.model.Paquete;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface CompraService {
    
    public void guardar(Compra compra);
    public Optional<Compra> encontrar(int id);
    public List<Compra> listar();
    public Paquete paqueteDeReserva(Integer id);
}
