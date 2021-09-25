package com.example.demo.dao;

import com.example.demo.model.Complemento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplementoDAO extends JpaRepository<Complemento, Integer> {
}
