/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.model.Compra;
import nexp.com.app.model.Paquete;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import nexp.com.app.dao.CompraDAO;
import nexp.com.app.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Santi & GenesisDanielaVJ
 */
@Service
public class CompraServiceImp implements CompraService {

    @Autowired
    CompraDAO cDAO;
    
    
    @Override
    @Transactional
    public Compra guardar(Compra compra) {
        Compra comp = cDAO.save(compra);
        return comp;
    }

    @Override
    @Transactional(readOnly = true )
    public Optional<Compra> encontrar(Long id) {
        return cDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> listar() {
        return cDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Paquete paqueteDeReserva(Integer id){
       return cDAO.reservaPaquete(id);
    }


    @Override
    @Transactional
    public void eliminar(Long id){ cDAO.deleteById(id); }

    @Override
    public List<Compra> comprasPagadas(int id) {
        return cDAO.comprasAprobadas(id);
    }

    @Override
    public Integer comprasAprobadasFecha(Date fecha1, Date fecha2) {

        return cDAO.comprasAprobadasFecha(fecha1,fecha2);
    }

    @Override
    public Integer devolucionesFecha(Date fecha1, Date fecha2) {
        return cDAO.devolucionesFecha(fecha1,fecha2);
    }
}
