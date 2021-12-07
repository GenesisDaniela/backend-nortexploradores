/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Devolucion;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author GenesisDanielaVJ
 */
public interface DevolucionService {
    
    public void guardar(Devolucion devolucion);
    public Optional<Devolucion> encontrar(int id);
    public List<Devolucion> listar();
    public void eliminar(int id);
}
