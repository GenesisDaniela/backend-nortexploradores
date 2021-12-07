/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.dao.DescuentoDAO;
import nexp.com.app.model.Descuento;
import nexp.com.app.service.DescuentoService;
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
public class DescuentoServiceImp implements DescuentoService {

    @Autowired
    DescuentoDAO dDAO;

    @Override
    @Transactional
    public void guardar(Descuento descuento) {
        dDAO.save(descuento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Descuento> listar() {
        return dDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        dDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Descuento> encontrar(int id) {
        return dDAO.findById(id);
    }
    
}
