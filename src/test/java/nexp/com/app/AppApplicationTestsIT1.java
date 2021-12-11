package nexp.com.app;

import nexp.com.app.dao.ReservaDAO;
import nexp.com.app.dao.TourDAO;
import nexp.com.app.model.*;
import nexp.com.app.negocio.NorteXploradores;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

	@Autowired
	ReservaService reservaService;

	@Autowired
	PersonaService personaService;


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
	@Test
	void paquetesVendidos() {
		int arrayEsperado[] = {1,0,0,0,0,0,0,0,0,0,11,21};
		NorteXploradores nxs = new NorteXploradores();
		assertArrayEquals(arrayEsperado, nxs.cantidadPaquetes());
	}

	@Test
	void obtenerReservas() {
	List<Reserva> reservas = reservaService.listar();
	List<Reserva> reservasM = new ArrayList<>();
		for(Reserva r: reservas){
			if(r.getFecha().getMonth().getValue() == LocalDate.now().getMonth().getValue() && r.getFecha().getYear()
					== LocalDate.now().getYear()){
				reservasM.add(r);
			}
		}
		assertEquals(57, reservasM.size());
	}

	@Test
	void validarDescuentoCumpleanios(){
		Usuario usuario= usuarioService.encontrar(22).get();
		Persona persona = personaService.encontrar(100493456).get();
		Compra compraDescuentoCumpleanios = compraService.encontrar((long)10242409).get();

		int totalReal = compraDescuentoCumpleanios.getTour().getPaquete().getPrecio()
				* compraDescuentoCumpleanios.getCantidadPasajeros();

		int totalEsperado = (compraDescuentoCumpleanios.getTour().getPaquete().getPrecio()
				* compraDescuentoCumpleanios.getCantidadPasajeros())/2;
		assertEquals(persona.getFechaNac().getMonth()+1,compraDescuentoCumpleanios.getFecha().getMonth().getValue());
		assertEquals(persona.getFechaNac().getDate()+1,compraDescuentoCumpleanios.getFecha().getDayOfMonth());
		assertEquals(compraDescuentoCumpleanios.getTotalCompra(),totalEsperado);

	}

	@Test
	void validarDescuentoUsuarioFrecuente(){
		Usuario usuario= usuarioService.encontrar(22).get();
		Integer total = compraService.comprasDeUsuarioFecha(
				LocalDate.parse("2021-12-01"),
				LocalDate.parse("2021-12-31"),
				usuario.getId_Usuario());
		Compra compra = compraService.encontrar((long)3632215).get();
		double totalEsperado = (compra.getTour().getPaquete().getPrecio())-(compra.getTour().getPaquete().getPrecio())*0.1;
		double totalReal = compra.getTotalCompra();
		assertTrue(total>3);
		assertEquals(totalEsperado,totalReal);

	}

	@Test
	void validarDescuentoInteres(){
		Compra compra = compraService.encontrar((long)28384627).get();
		int cantPasajeros = compra.getCantidadPasajeros();
		int valorUnit = compra.getTour().getPaquete().getPrecio();
		double totalEsperado = (valorUnit*cantPasajeros)-(valorUnit*cantPasajeros)*0.08;
		double totalReal = compra.getTotalCompra();
		assertTrue(cantPasajeros>3);
		assertEquals(totalEsperado,totalReal);

	}

}
