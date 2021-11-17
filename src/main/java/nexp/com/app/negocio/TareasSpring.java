package nexp.com.app.negocio;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Compra;
import nexp.com.app.model.Tour;
import nexp.com.app.service.CompraService;
import nexp.com.app.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Slf4j
@Component
public class TareasSpring {

    @Autowired
    CompraService compraService;

    @Autowired
    TourService tourService;

    NorteXploradores norteXploradores = new NorteXploradores();

    //  @Scheduled(fixedRate = 100000)
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void revisarTiempoReserva() {
//        log.info("Ejecutanto labores de tiempos de reserva...");
//        for (Compra c : compraService.listar()) {
//
//            Date fechaReserva = c.getFecha();
//            Date fechaSalida = c.getTour().getFechaSalida();
//            Date fechaHoy = new Date();
//
//
//            if (!c.getEstado().equals("PAGADO")) {
//                int diasEntrefechas = norteXploradores.diferenciaDias(fechaHoy, fechaSalida);
//                if (diasEntrefechas < 3) {                                         //Diferencia de dias entre la fecha de acumulada(es decir desde que se reservo y la fecha de hoy) y la fecha de salida del paquete
//                    log.info("Reserva " + c.toString() + " cancelada, el viaje sale dentro de" + diasEntrefechas + ", la reserva fue realizada el " + fechaReserva + " y el viaje sale el" + fechaSalida);
//                    c.setEstado("CANCELADO");                             //Si no es está pagado y faltan menos de 3 dias para la salida del viaje, se cancela
//
//                    Tour t = c.getTour();
//                    int cupos = t.getCantCupos() + 1;
//                    t.setCantCupos(cupos);
//                    tourService.guardar(t);
//                    compraService.guardar(c);
//                }
//            }
//        }
//    }

}
