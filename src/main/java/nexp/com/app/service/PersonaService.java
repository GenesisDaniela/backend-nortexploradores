/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Persona;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & GenesisDanielaVJ
 */
public interface PersonaService {
        
    public Persona guardar(Persona persona);
    public void eliminar(int id);
    public Optional<Persona> encontrar(int id);
    public List<Persona> listar();
//    public Persona editar(Persona persona);
}
