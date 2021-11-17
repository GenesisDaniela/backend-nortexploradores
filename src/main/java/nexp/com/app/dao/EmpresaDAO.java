/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.dao;

import nexp.com.app.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Santi & Dani
 */
public interface EmpresaDAO extends JpaRepository<Empresa, Integer>{
    
}
