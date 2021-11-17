/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Empleado;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author GenesisDanielaVJ
 */
public interface EmpleadoService {
    
    public void guardar(Empleado empleado);
    public Optional<Empleado> encontrar(int id);
    public List<Empleado> listar();
    public void eliminar(int id);
}
