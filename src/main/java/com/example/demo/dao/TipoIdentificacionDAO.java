/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.dao;

import com.example.demo.model.TipoIdentificacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Santi & Dani
 */
public interface TipoIdentificacionDAO extends JpaRepository<TipoIdentificacion, Integer>{
    
}
