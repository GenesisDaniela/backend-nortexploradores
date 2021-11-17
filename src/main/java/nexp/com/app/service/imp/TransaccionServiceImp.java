/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service.imp;

import nexp.com.app.dao.TransaccionDAO;
import nexp.com.app.model.Transaccionp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nexp.com.app.service.TransaccionService;

/**
 *
 * @author santi
 */
@Service
public class TransaccionServiceImp implements TransaccionService{

    @Autowired
    public TransaccionDAO pdao;
    
    @Override
    public void guardar(Transaccionp payu) {
        pdao.save(payu);
    }

    @Override
    public List<Transaccionp> listar() {
        return (List)pdao.findAll();
    }

    @Override
    public void eliminar(String id) {
        pdao.deleteById(id);
    }

    @Override
    public Transaccionp encontrar(String id) {
        return pdao.findById(id).get();
    }

    
}
