package nexp.com.app.negocio;

import nexp.com.app.dao.TransporteDAO;
import nexp.com.app.model.Transporte;
import nexp.com.app.service.TransporteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlayGround {

    @Autowired
    public static TransporteService pser;
    public static void main(String[] args) {
//        NorteXploradores norteXploradores =new NorteXploradores();

        List<Transporte> prueba = (List)pser.listar();
//        Transporte prueba = pser.encontrar("VCA123").orElse(null);

        System.out.println(prueba.toString());

//        System.out.println(norteXploradores.convertirFecha("2021-09-13 23:56:49","-"));
//
//        Date fechaSalida = norteXploradores.convertirFecha("2021-09-15 23:56:49","-");
//        Date fechaReserva = new Date();
//
//        int milisecondsByDay = 86400000;
//        int dias = (int) ((fechaReserva.getTime()-fechaSalida.getTime()) / milisecondsByDay);
//
//        System.out.println("diferencia de dias"+dias);

//        Date diaHoy = new Date();
//
//        int diasAcu =2;
//
//        Date currentDate = new Date();
//        System.out.println("Current date: "+currentDate);
//
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(diaHoy);
//
//        cal.add(Calendar.DAY_OF_MONTH, diasAcu);
//
//        Date datePlus1 = cal.getTime();
//        System.out.println("Adding "+diasAcu+" days to current date: "+datePlus1);
//


    }
}
