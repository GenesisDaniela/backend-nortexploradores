/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.dao;


import nexp.com.app.model.Notificacion;
import nexp.com.app.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *

 * @author Santi & Dani
 */
public interface NotificacionDAO extends JpaRepository<Notificacion, Integer> {
    @Query("SELECT u FROM Notificacion u WHERE u.estado = 0 ORDER BY u.fecha DESC")
    List<Notificacion> findAllNotificacionActiva();

    @Query("SELECT u FROM Notificacion u ORDER BY u.fecha DESC")
    List<Notificacion> findAllNotificacion();
    
}
