/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Transporte;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface TransporteService {
    
    public Transporte guardar(Transporte transporte);
    public void eliminar(String id);
    public Optional<Transporte> encontrar(String id);
    public List<Transporte> listar();
            
}
