/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.service;

import nexp.com.app.model.Compra;
import nexp.com.app.model.Paquete;
import nexp.com.app.model.Tour;
import nexp.com.app.negocio.response.ReservaTabla;
import org.hibernate.mapping.Array;

import java.time.LocalDate;
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
    public Integer comprasAprobadasFecha(LocalDate fecha1, LocalDate fecha2);
    public Integer devolucionesFecha(LocalDate fecha1, LocalDate fecha2);
    public Integer comprasDePaquete(LocalDate fecha1, LocalDate fecha2, int idPaq);
    public Integer pasajerosDePaquete(LocalDate fecha1, LocalDate fecha2, int idPaq);

    public Integer totalPaquetes(LocalDate fecha1, LocalDate fecha2);
    public Integer totalPaquetesDev(LocalDate fecha1, LocalDate fecha2);

    public Integer totalTours(LocalDate fecha1, LocalDate fecha2);
    public List<Paquete> tourMasVendido(LocalDate fecha1, LocalDate fecha2);

    public Integer devDePaquete(LocalDate fecha1, LocalDate fecha2, int idPaq);
    public Integer comprasDeUsuarioFecha(LocalDate fecha1, LocalDate fecha2, int idUsuario);
    public int[] cantidadCompras();

    public List<Compra> reservasFecha(LocalDate fecha1, LocalDate fecha2);

}
