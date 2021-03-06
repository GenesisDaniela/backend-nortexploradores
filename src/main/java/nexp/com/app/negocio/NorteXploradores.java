package nexp.com.app.negocio;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.dao.CompraDAO;
import nexp.com.app.model.*;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
public class NorteXploradores {

    @Autowired
    CompraService compraService;

    @Autowired
    CompraDAO compraDAO;


    public List<Compra> paquetesComprados(List<Compra> compras){
        List<Compra> comprados = new ArrayList<>();
        for(Compra c: compras){
            if(c.getEstado().equals("PAGO_PARCIAL")){
                comprados.add(c);
            }
        }
        return comprados;
    }

    public List<Compra> paquetesReservados(List<Compra> compras){
        List<Compra> reservados = new ArrayList<>();
        for(Compra c: compras){
            if(c.getReserva() != null){
                reservados.add(c);
            }
        }
        return reservados;
    }
    public List<Compra> paquetesTotales(List<Compra> compras){
        List<Compra> reservados = new ArrayList<>();
        for(Compra c: compras){
            if(c.getReserva() == null){
                reservados.add(c);
            }
        }
        return reservados;
    }

    public int[] cantidadPaquetes() {
        List<Compra> compras = compraService.listar();
        int cantidadPaq[] = new int[12];
        LocalDate fechaActual = LocalDate.now();
        for (Compra c : compras) {
            if (c.getEstado().equals("PAGADO") && c.getFecha().getYear() == fechaActual.getYear()) {
                cantidadPaq[(c.getFecha().getMonth().getValue())-1] += c.getCantidadPasajeros();
            }
        }
        return cantidadPaq;
    }

    public List<Tour> tourComprados(List<Compra> compras){
        List<Tour> reservados = new ArrayList<>();
        for(Compra c: compras){
            log.info(c.getEstado());
            if(c.getEstado().equals("PAGADO")){
                reservados.add(c.getTour());
            }
        }
        return reservados;
    }

    public List<Compra> comprasCanceladas(List<Compra> paquetes){
        List<Compra> comprados = new ArrayList<>();

        for(Compra c: paquetes){
            if(c.getEstado().equals("CANCELADO")){
                comprados.add(c);
            }
        }
        return comprados;
    }

    public int diferenciaDias(Date fechaSalida, Date fechaReserva){
        int milisecondsByDay = 86400000;
        int dias = (int) ((fechaReserva.getTime()-fechaSalida.getTime()) / milisecondsByDay);
        return dias;
    }

    public Date convertirFecha(String date, String separador){
        String fecha = date.split(" ")[0];
        String horaC = date.split(" ")[1];

        String vectorFecha[] = fecha.split(separador);
        String anio = vectorFecha[0];
        String mes = vectorFecha[1];
        String dia = vectorFecha[2];

        String vectorHora[] = horaC.split("\\:");
        String hora = vectorHora[0];
        String minuto = vectorHora[1];
        String segundo = vectorHora[2];

        Date fechaCompleta=new Date();
        fechaCompleta.setDate(Integer.parseInt(dia));
        fechaCompleta.setMonth(Integer.parseInt(mes));
        fechaCompleta.setYear(Integer.parseInt(anio)-1900);
        fechaCompleta.setHours(Integer.parseInt(hora));
        fechaCompleta.setMinutes(Integer.parseInt(minuto));
        fechaCompleta.setSeconds(Integer.parseInt(segundo));

        return fechaCompleta;
    }

    public boolean existeCompra(List<Compra> compra, Compra compraTemp){
        boolean flag = false;
        for(Compra comprra: compra){
            if(compraTemp == comprra){
                flag = true;
            }
        }
        return flag;
    }

    public boolean existePasajero(List<Pasajero> pasajeros, Pasajero pasajero){
        for(Pasajero p: pasajeros){
            if(pasajero.getIdPasajero() == p.getIdPasajero()){
                return true;
            }
        }
        return false;
    }

//    public boolean existeUsuario(List<Usuario> usuarios, Usuario usuario){
//        boolean flag = false;
//        for(Usuario u: usuarios){
//            if(usuario.getId_Usuario() == u.getId_Usuario()){
//                return true;
//            }
//        }
//        return flag;
//    }

    public boolean existePersona(List<Persona> personas, Persona persona){
        boolean flag = false;
        for(Persona p: personas){
            if(persona.getIdPersona().equals(p.getIdPersona())){
                return true;
            }
        }
        return flag;
    }


    public boolean existeUsuario(Collection<ClientePasajero> clientePasajeroCollection, Usuario us) {
        boolean flag = false;
        for(ClientePasajero usuario: clientePasajeroCollection){
            if(usuario.getUsuario().getId_Usuario() == (us.getId_Usuario())){
                return true;
            }
        }
        return flag;
    }
}
