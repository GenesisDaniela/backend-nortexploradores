/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.dao.NotificacionDAO;
import nexp.com.app.model.Notificacion;
import nexp.com.app.service.NotificacionService;
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
public class NotificacionServiceImp implements NotificacionService {

    @Autowired
    NotificacionDAO nDAO;
    
    @Override
    @Transactional
    public void guardar(Notificacion notificacion) {
        nDAO.save(notificacion);
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Notificacion> encontrar(int id) {
        return nDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listar() {
        return nDAO.findAll();
    }

    @Override
    public List<Notificacion> listarActivos() {
        return nDAO.findAllNotificacionActiva();
    }

    @Override
    public void eliminar(int id) {
        nDAO.deleteById(id);
    }
    
}
