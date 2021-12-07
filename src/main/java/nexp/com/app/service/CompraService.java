/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Compra;
import nexp.com.app.model.Paquete;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Santi & GenesisDanielaVJ
 */
public interface CompraService {
    
    public Compra guardar(Compra compra);
    public Optional<Compra> encontrar(Long id);
    public List<Compra> listar();
    public Paquete paqueteDeReserva(Integer id);
    public void eliminar(Long id);
    public List<Compra> comprasPagadas(int id);
    public Integer comprasAprobadasFecha(Date fecha1, Date fecha2);
    public Integer devolucionesFecha(Date fecha1, Date fecha2);
    public Integer comprasDePaquete(Date fecha1, Date fecha2, int idPaq);
    public Integer devDePaquete(Date fecha1, Date fecha2, int idPaq);

}
