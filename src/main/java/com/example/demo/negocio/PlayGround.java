package com.example.demo.negocio;

import java.util.Date;

public class PlayGround {

    public static void main(String[] args) {
        String date = "2021.09.13 11:17:26";


        String fecha = date.split(" ")[0];
        String horaC = date.split(" ")[1];

        String vectorFecha[] = fecha.split("\\.");
        String anio = vectorFecha[0];
        String mes = vectorFecha[1];
        String dia = vectorFecha[2];

        System.out.println(anio);
        System.out.println(mes);
        System.out.println(dia);

        String vectorHora[] = horaC.split("\\:");
        String hora = vectorHora[0];
        String minuto = vectorHora[1];
        String segundo = vectorHora[2];

        System.out.println(hora);
        System.out.println(minuto);
        System.out.println(segundo);

        Date fechaCompletac;
    }

}
