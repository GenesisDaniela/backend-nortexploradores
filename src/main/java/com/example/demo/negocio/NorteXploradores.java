package com.example.demo.negocio;

import com.example.demo.model.Compra;
import com.example.demo.model.Paquete;

import java.util.ArrayList;
import java.util.List;

public class NorteXploradores {

    public List<Compra> paquetesComprados(List<Compra> paquetes){
        List<Compra> comprados = new ArrayList<>();

        for(Compra c: paquetes){
            if(c.reserva()!=null){
                if(c.reserva().getEstado().equals("PAGADO") && c.transaccion()!=null)
                    comprados.add(c);
            }
        }
        return comprados;
    }

    public List<Compra> comprasCanceladas(List<Compra> paquetes){
        List<Compra> comprados = new ArrayList<>();

        for(Compra c: paquetes){
            if(c.reserva()!=null || c.reserva().getEstado().equals("CANCELADO")){
                comprados.add(c);
            }
        }
        return comprados;
    }

    public List<Compra> comprasEspera(List<com.example.demo.model.Compra> paquetes){
        List<com.example.demo.model.Compra> comprados = new ArrayList<>();

        for(com.example.demo.model.Compra c: paquetes){
            if(c.reserva()!=null || c.reserva().getEstado().equals("ESPERA")){
                comprados.add(c);
            }
        }
        return comprados;
    }

}
