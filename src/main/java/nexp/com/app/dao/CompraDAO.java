/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.dao;

import nexp.com.app.model.Compra;
import nexp.com.app.model.Paquete;
import nexp.com.app.model.Tour;
import nexp.com.app.negocio.response.ReservaTabla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Date;
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

    @Query ("SELECT sum(c.totalCompra) FROM Compra c WHERE c.estado='PAGADO' and c.fecha between :fecha and :fecha2")
    Integer comprasAprobadasFecha(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2);

    @Query ("SELECT sum(c.cantidadPasajeros) FROM Compra c WHERE c.estado='PAGADO' and c.tour.paquete.idPaq=:idPaq and c.fecha between :fecha and :fecha2")
    Integer pasajerosPorPaquete(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2, @Param("idPaq") int idPaq);

    @Query ("SELECT sum(c.totalCompra) FROM Compra c WHERE c.estado='PAGADO' and c.fecha between :fecha and :fecha2")
    Integer comprasPorPaquetesMes(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2);

    @Query ("SELECT sum(d.cantidad) FROM Devolucion d WHERE d.compra.estado='PAGADO' and d.compra.fecha between :fecha and :fecha2")
    Integer devPorPaquetesMes(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2);

    @Query ("SELECT sum(d.cantidad) FROM Devolucion d WHERE d.compra.estado='PAGADO' and d.compra.fecha between :fecha and :fecha2")
    Integer devolucionesFecha(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2);

    @Query ("SELECT sum(c.totalCompra) FROM Compra c WHERE c.estado='PAGADO' and c.tour.paquete.idPaq=:idPaq and c.fecha between :fecha and :fecha2")
    Integer comprasPorPaquete(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2, @Param("idPaq") int idPaq);

    @Query ("SELECT sum(d.cantidad) FROM Devolucion d WHERE d.compra.estado='PAGADO' and d.compra.tour.paquete.idPaq=:idPaq and d.compra.fecha between :fecha and :fecha2")
    Integer devPorPaquete(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2, @Param("idPaq") int idPaq);

    @Query ("SELECT count(c) FROM Compra c WHERE c.estado='PAGADO' and c.usuario.id_Usuario=:idUsuario and c.fecha between :fecha and :fecha2")
    Integer comprasAprobadasFechaUsuario(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2, @Param("idUsuario") int idUsuario);


    @Query ("SELECT count(c.tour) FROM Compra c WHERE c.estado='PAGADO' and c.fecha between :fecha and :fecha2")
    Integer totalToursVendidos(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2);

    @Query ("SELECT c.tour.paquete FROM Compra c WHERE c.estado ='PAGADO' and c.fecha between :fecha and :fecha2 GROUP by c.tour.paquete ORDER by sum(c.totalCompra)")
    List<Paquete> toursVendidos(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2);

    @Query ("SELECT c FROM Compra c WHERE c.reserva IS NOT NULL and c.reserva.fecha between :fecha and :fecha2")
    List<Compra> reservasFecha(@Param("fecha") LocalDate fecha, @Param("fecha2") LocalDate fecha2);

}
