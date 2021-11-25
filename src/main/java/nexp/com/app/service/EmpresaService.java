/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Empresa;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface EmpresaService {
        
    public void guardar(Empresa empresa);
    public Optional<Empresa> encontrar(long id);
    public List<Empresa> listar();
    public void eliminar(long id);
}
