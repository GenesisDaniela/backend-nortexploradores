/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Municipio;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & GenesisDanielaVJ
 */
public interface MunicipioService {
//     public Municipio guardar();
    public Optional<Municipio> encontrar(int id);
    public List<Municipio> listar();
}
