/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.dao;

import nexp.com.app.model.Municipio;
import nexp.com.app.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author Santi & Dani
 */
public interface MunicipioDAO extends JpaRepository<Municipio, Integer> {
    @Query("SELECT u FROM Municipio u WHERE u.idDepto.idDepto=21")
    List<Municipio> listarMunicipioNS();

    @Query("SELECT u FROM Municipio u WHERE u.idDepto.idDepto=21 AND u.estado=true")
    List<Municipio> listarMunicipioNSActivo();
}
