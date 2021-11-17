/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.model.Transporte;
import java.util.List;
import java.util.Optional;
import nexp.com.app.dao.TransporteDAO;
import nexp.com.app.service.TransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GenesisDanielaVJ
 */
@Service
public class TransporteServiceImp implements TransporteService {

    @Autowired
    TransporteDAO tDAO;
    
    @Override
    @Transactional
    public Transporte guardar(Transporte transporte) {
        return tDAO.save(transporte);
    }

    @Override
    @Transactional
    public void eliminar(String id) {
        tDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Transporte> encontrar(String id) { return tDAO.findById(id);}

    @Override
    @Transactional(readOnly = true)
    public List<Transporte> listar() {
        return tDAO.findAll();
    }
    
}
