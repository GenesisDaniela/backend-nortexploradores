/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Compra;
import nexp.com.app.model.Paquete;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & Dani
 */
public interface CompraService {
    
    public void guardar(Compra compra);
    public Optional<Compra> encontrar(Long id);
    public List<Compra> listar();
    public Paquete paqueteDeReserva(Integer id);
    public List<Compra> comprasAprobadas(List<Compra> compras);
}
