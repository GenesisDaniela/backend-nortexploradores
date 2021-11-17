/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nexp.com.app.service.imp;
import java.util.List;
import java.util.Optional;
import nexp.com.app.dao.AlojamientoDAO;
import nexp.com.app.model.Alojamiento;
import nexp.com.app.service.AlojamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GenesisDanielaVJ
 */
@Service
public class AlojamientoServiceImp implements AlojamientoService {

    @Autowired
    AlojamientoDAO aDAO;
    
    
    @Override
    @Transactional
    public void guardar(Alojamiento alojamiento) {
        aDAO.save(alojamiento);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Alojamiento> encontrar(int id) {
        return aDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alojamiento> listar() {
        return aDAO.findAll();
    }

    @Override
    public void eliminar(int id) {
        aDAO.deleteById(id);
    }
    
}
