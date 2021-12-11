package nexp.com.app;

import nexp.com.app.dao.ReservaDAO;
import nexp.com.app.dao.TourDAO;
import nexp.com.app.model.Compra;
import nexp.com.app.model.Devolucion;
import nexp.com.app.model.Reserva;
import nexp.com.app.model.Tour;
import nexp.com.app.security.jwt.JwtProvider;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.RolService;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AppApplicationTestsIT1 {

	@Autowired
	DevolucionService devolucionService;

	@Autowired
	CompraService compraService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	TourService tourService;


	@Test
	void registrarCompra() {
		Usuario usuario = usuarioService.encontrar(1).get();
		Tour tour = tourService.encontrar(1).get();
		LocalDate fechaAc = LocalDate.now();
		Compra compra = new Compra((long)333221,5, 250000,"PAGADO", fechaAc ,usuario,tour );
		compraService.guardar(compra);
		assertEquals(compra,compraService.encontrar((long)333221).get() );
	}

	@Test
	void registrarDevolucion() {
		Compra compra = compraService.encontrar((long)3139333).orElse(null);
		Devolucion devolucion = new Devolucion(20000, LocalDate.now(), compra);
		Devolucion devolucionGuardada = devolucionService.guardar(devolucion);
		assertEquals(devolucion,devolucionGuardada);
	}

	//El sistema deberá generar tablas y gráficos estadísticos (diagrama de barras y tortas)
	// a partir de la cantidad de paquetes vendidos mensualmente.
//	@Test
//	void paquetesVendidos() {}

	//El sistema deberá permitir generar un reporte del estado (CANCELADAS ó PAGADAS)
	// de las reservas realizadas en el mes.
//	@Test
//	void obtenerReservas() {
//	List<Reserva> reservas;
//	}

}
