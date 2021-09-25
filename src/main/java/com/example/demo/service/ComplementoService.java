package com.example.demo.service;

import com.example.demo.model.Complemento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ComplementoService {
    public void guardar(Complemento cargo);
    public Optional<Complemento> encontrar(int id);
    public List<Complemento> listar();
    public void eliminar(int id);
}
