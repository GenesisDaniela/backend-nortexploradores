/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.model.Municipio;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface MunicipioService {
     public Optional<Municipio> encontrar(int id);
    public List<Municipio> listar();
}
