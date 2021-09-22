package com.example.demo.security.service;

import com.example.demo.security.entity.Rol;
import com.example.demo.security.enums.RolNombre;
import com.example.demo.security.repository.RolRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    @Transactional(readOnly = true)
    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }
        
    @Transactional
    public void guardar(Rol rol) {
        rolRepository.save(rol);
    }

    @Transactional
    public void eliminar(int id) {
        rolRepository.deleteById(id);
    }

    @Transactional(readOnly = true )
    public Optional<Rol> encontrar(int id) {
        return rolRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Rol> listar() {
        return rolRepository.findAll();
    }
    
    
}
