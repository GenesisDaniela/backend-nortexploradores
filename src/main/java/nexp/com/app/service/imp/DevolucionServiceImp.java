/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.dao.DevolucionDAO;
import nexp.com.app.model.Devolucion;
import nexp.com.app.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author GenesisDanielaVJ
 */
@Service
public class DevolucionServiceImp implements DevolucionService {

    @Autowired
    DevolucionDAO dDAO;

    @Override
    @Transactional
    public Devolucion guardar(Devolucion devolucion) {
        Devolucion dev = dDAO.save(devolucion);
        return dev;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Devolucion> listar() {
        return dDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        dDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Devolucion> encontrar(int id) {
        return dDAO.findById(id);
    }
    
}
