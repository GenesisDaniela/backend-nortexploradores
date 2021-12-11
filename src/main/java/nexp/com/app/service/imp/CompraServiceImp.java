/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.model.Compra;
import nexp.com.app.model.Paquete;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import nexp.com.app.dao.CompraDAO;
import nexp.com.app.service.CompraService;
import org.hibernate.mapping.Array;
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
    public Integer comprasAprobadasFecha(LocalDate fecha1, LocalDate fecha2) {

        return cDAO.comprasAprobadasFecha(fecha1,fecha2);
    }

    @Override
    public Integer devolucionesFecha(LocalDate fecha1, LocalDate fecha2) {
        return cDAO.devolucionesFecha(fecha1,fecha2);
    }

    @Override
    public Integer comprasDePaquete(LocalDate fecha1, LocalDate fecha2, int idPaq) {
        return cDAO.comprasPorPaquete(fecha1,fecha2,idPaq);
    }

    @Override
    public Integer devDePaquete(LocalDate fecha1, LocalDate fecha2, int idPaq) {
        return cDAO.devPorPaquete(fecha1,fecha2,idPaq);
    }

    @Override
    public Integer comprasDeUsuarioFecha(LocalDate fecha1, LocalDate fecha2, int idUsuario) {
        return cDAO.comprasAprobadasFechaUsuario(fecha1,fecha2,idUsuario);
    }

    @Override
    public int[] cantidadCompras() {
        List<Compra> compras = cDAO.findAll();
        int cantidadPaq[] = new int[12];
        LocalDate fechaActual = LocalDate.now();
        for (Compra c : compras) {
            if (c.getEstado().equals("PAGADO") && c.getFecha().getYear() == fechaActual.getYear()) {
                cantidadPaq[(c.getFecha().getMonth().getValue())-1] += c.getCantidadPasajeros();
            }
        }
        return cantidadPaq;
    }
}
