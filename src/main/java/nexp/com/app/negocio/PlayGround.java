package nexp.com.app.negocio;

import nexp.com.app.dao.TransporteDAO;
import nexp.com.app.model.Transporte;
import nexp.com.app.service.TransporteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PlayGround {

    @Autowired
    public static TransporteService pser;
    public static void main(String[] args) {
//        NorteXploradores norteXploradores =new NorteXploradores();
        System.out.println(new Date().getMonth());
        ;
        System.out.println(LocalDate.parse("2021-1-01")+"-------------");
        System.out.println(Calendar.getInstance().get(Calendar.YEAR));
        int mes = new Date().getMonth();
        int anio = new Date().getYear()+1900;
        int dia = 31;

        if(mes==1)
            dia=29;
        if(mes==3 || mes==5 || mes==10 || mes==8)
            dia=30;

        String fecha1=""+anio+"-"+(mes+1)+"-01";
        String fecha2=""+anio+"-"+(mes+1)+"-"+dia+"";

        System.out.println(fecha1);
        System.out.println(fecha2);

//        List<Transporte> prueba = (List)pser.listar();
//        Transporte prueba = pser.encontrar("VCA123").orElse(null);
//            String emailUsuarioEmisor="santiagopico2001@gmail.com";
//            String clave="u1232584846a.";
//            //Cambia el valor de la variable emailReceptor por el email que desee enviarle mensajes
//            Scanner teclado=new Scanner(System.in);
//            System.out.println("Digite una dirección de email:");
//            String emailReceptor=teclado.nextLine();
//            EmailService email=new EmailService(emailUsuarioEmisor, clave);
//            email.enviarEmail(emailReceptor, "Esto es un ejemplo", "Mi cuerpo del mensaje");
//            System.out.println("Se ha enviado email: "+emailReceptor);
//        String cuerpo = " <img src=\"https://raw.githubusercontent.com/SantiagoAndresSerrano/img-soka/master/LOGO-01.png\" style=\"width: 100px;height: auto;display: inline;\" alt=\"\" srcset=\"\">\n" +
//                "    <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\">" +
//                "Bienvenido </h1>\n" +
//                "    <p style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\">\n" +
//                "        te has registrado en el aplicativo web de NorteXploradores, estos son tus datos de inicio de sesion:\n" +
//                "    </p>" +
//                "<ul>" +
//                "                \"<li>Usuario:\"+usuario.getEmail()+\"</li>\" +\n" +
//                "                \"</ul>\" );";
//        System.out.println(cuerpo);
//
////        System.out.println(prueba.toString());
//        Date fechaSalida = new Date();
//        fechaSalida.setDate(4);
//        Date fechaReserva = new Date();
//
//        int milisegundospordia= 86400000;
//        System.out.println(fechaSalida);
//        System.out.println(fechaReserva);
//        int dias = (int) ((fechaReserva.getTime()-fechaSalida.getTime()) / milisegundospordia);
//        System.out.println(dias);
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
