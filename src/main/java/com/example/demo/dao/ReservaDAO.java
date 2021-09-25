package com.example.demo.dao;

import com.example.demo.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaDAO extends JpaRepository<Reserva, Integer> {
}
