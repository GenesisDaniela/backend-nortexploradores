/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.dao;

import nexp.com.app.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author Santi & Dani
 */
public interface TourDAO extends JpaRepository<Tour, Integer> {
    @Query("SELECT u FROM Tour u WHERE u.estado = 'ACTIVO' ")
    List<Tour> findAllTourActive();
}
