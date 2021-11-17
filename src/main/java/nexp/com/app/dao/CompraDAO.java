/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.dao;

import nexp.com.app.model.Compra;
import nexp.com.app.model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author Santi & Dani
 */
public interface CompraDAO extends JpaRepository<Compra, Long> {
    @Query (value = "SELECT Paquete FROM Compra WHERE Compra.reserva = :id", nativeQuery = true)
    Paquete reservaPaquete(@Param("id") Integer id);

//    @Query (value = "SELECT Compra FROM Compra WHERE Compra.reserva = :id", nativeQuery = true)
//    List<Compra> comprasAprobadas()
}
