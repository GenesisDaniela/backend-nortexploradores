/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.dao.PersonaDAO;
import nexp.com.app.model.Persona;
import nexp.com.app.service.PersonaService;
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
public class PersonaServiceImp implements PersonaService {

    @Autowired
    PersonaDAO pDAO;
    
    
    @Override
    @Transactional
    public void guardar(Persona persona) {
        pDAO.save(persona);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Persona> encontrar(int id) {
        return pDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Persona> listar() {
        return pDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        pDAO.deleteById(id);
    }
    
}
