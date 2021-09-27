package com.example.demo.negocio;

import com.example.demo.model.Compra;
import com.example.demo.model.Transaccionp;

import java.util.ArrayList;
import java.util.List;

public class NorteXploradores {

    public List<Compra> paquetesComprados(List<Compra> compras){
        List<Compra> comprados = new ArrayList<>();
        int pago=0;
        for(Compra c: compras){
            for(Transaccionp t: c.transaccion()){
                if (t.getResponseMessagePol().equals("APPROVED")){
                    pago+=t.getValue();
                }
            }

            if(pago == c.getTotalCompra()){
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
