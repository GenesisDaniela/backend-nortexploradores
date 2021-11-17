/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import java.util.List;
import java.util.Optional;
import nexp.com.app.model.Calificacion;


/**
 *
 * @author GenesisDanielaVJ
 */
public interface CalificacionService {
    
    public void guardar(Calificacion compra);
    public Optional<Calificacion> encontrar(int id);
    public List<Calificacion> listar();
    public void eliminar(int id);
}
