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

import java.lang.reflect.Array;
import java.util.List;

/**
 *
 * @author GenesisDanielaVJ
 */
public interface CompraDAO extends JpaRepository<Compra, Long> {
    @Query (value = "SELECT Paquete FROM Compra WHERE Compra.reserva = :id", nativeQuery = true)
    Paquete reservaPaquete(@Param("id") Integer id);

    @Query ("SELECT c FROM Compra c WHERE c.usuario.id_Usuario=:id and c.estado='PAGADO'")
    List<Compra> comprasAprobadas(@Param("id") Integer id);

//    @Query("SELECT COUNT (c.idCompra) FROM Compra c WHERE c.estado ='PAGADO' and c.fecha BETWEEN '20210101 00:00:00' AND '20210101 00:00:00'")
//    Array[] paquetesVendidosM();
}
