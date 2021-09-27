/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.dao;

import com.example.demo.model.Compra;
import com.example.demo.model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Santi & Dani
 */
public interface CompraDAO extends JpaRepository<Compra, Integer> {
    @Query (value = "SELECT Paquete FROM Compra WHERE Compra.reserva = :id", nativeQuery = true)
    Paquete reservaPaquete(@Param("id") Integer id);
}
