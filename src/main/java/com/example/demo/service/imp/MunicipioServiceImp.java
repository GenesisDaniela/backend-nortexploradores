/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service.imp;

import com.example.demo.dao.MunicipioDAO;
import com.example.demo.model.Municipio;
import com.example.demo.service.MunicipioService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Santi & Dani
 */
@Service
public class MunicipioServiceImp implements MunicipioService {

    @Autowired
    MunicipioDAO mDao;


    @Override
    @Transactional(readOnly = true )
    public Optional<Municipio> encontrar(int id) {
        return mDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Municipio> listar() {
        return mDao.findAll();
    }
    
}
