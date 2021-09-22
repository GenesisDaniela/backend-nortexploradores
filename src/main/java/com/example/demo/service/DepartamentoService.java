/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Departamento;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface DepartamentoService {
    
    public Optional<Departamento> encontrar(int id);
    public List<Departamento> listar();
    public void guardar(Departamento dpto);
            
}
