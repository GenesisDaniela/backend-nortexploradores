/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.dao.EmpresaDAO;
import nexp.com.app.model.Empresa;
import nexp.com.app.service.EmpresaService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GenesisDanielaVJ
 */
@Service
public class EmpresaServiceImp implements EmpresaService {

    @Autowired
    EmpresaDAO eDAO;  
    
    @Override
    @Transactional
    public void guardar(Empresa empresa) {
        eDAO.save(empresa);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Empresa> encontrar(long id) {
        return eDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empresa> listar() {
        return eDAO.findAll();
    }

    @Override
    public void eliminar(long id) {
        eDAO.deleteById(id);
    }
    
}
