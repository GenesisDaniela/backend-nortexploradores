/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.dao;

import nexp.com.app.model.Notificacion;
import nexp.com.app.model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author Santi & GenesisDanielaVJ
 */
public interface PaqueteDAO extends JpaRepository<Paquete, Integer> {
    @Query("SELECT p FROM Paquete p WHERE p.estado = 'ACTIVO'")
    List<Paquete> findAllPaqueteActivo();

}
