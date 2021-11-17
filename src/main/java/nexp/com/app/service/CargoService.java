/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;


import java.util.List;
import java.util.Optional;
import nexp.com.app.model.Cargo;

/**
 *
 * @author GenesisDanielaVJ
 */
public interface CargoService {
    
    public void guardar(Cargo cargo);
    public Optional<Cargo> encontrar(int id);
    public List<Cargo> listar();
    public void eliminar(int id);
}
