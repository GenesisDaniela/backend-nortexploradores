package com.example.demo.service;

import com.example.demo.model.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaService {

    public void guardar(Reserva ruta);
    public void eliminar(int id);
    public Optional<Reserva> encontrar(int id);
    public List<Reserva> listar();

}
