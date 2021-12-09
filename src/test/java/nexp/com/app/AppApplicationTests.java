package nexp.com.app;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.dao.TourDAO;
import nexp.com.app.dao.ReservaDAO;
import nexp.com.app.model.*;
import nexp.com.app.rest.PersonaRest;
import nexp.com.app.security.dto.JwtDto;
import nexp.com.app.security.jwt.JwtProvider;
import nexp.com.app.security.model.Rol;
import nexp.com.app.security.model.Usuario;
import nexp.com.app.security.servicio.RolService;
import nexp.com.app.security.servicio.UsuarioService;
import nexp.com.app.service.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AppApplicationTests {

	@Autowired
	PaqueteService paqueteService;

	@Autowired
	EmpleadoService empleadoService;

	@Autowired
	SeguroService seguroService;

	@Autowired
	ActividadService actividadService;

	@Autowired
	TourService tourService;

	@Autowired
	MunicipioService municipioService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	TourDAO tourDAO;

	@Autowired
	ReservaDAO reservaDAO;

	@Autowired
	PersonaService personaService;

	@Autowired
	TipoIdentificacionService tIdservice;

	@Autowired
	CargoService cargoService;

	@Autowired
	PasajeroService pasajeroService;

	@Autowired
	CompraService compraService;

	@Autowired
	DetalleCompraService detalleCompraService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RolService rolService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	private JavaMailSender mailSender;


	@Test
	void probarEncontrarPaquete() {
		assertEquals(paqueteService.encontrar(1), paqueteService.encontrar(1));
	}



		@Test
	void guardarTour() {
		Tour tourEsperado = new Tour();
		tourEsperado.setCantCupos(0);
		tourEsperado.setMaxCupos((short)15);
		tourEsperado.setMinCupos((short)5);
		tourEsperado.setPaquete(paqueteService.encontrar(1).get());
		tourEsperado.setEstado("ACTIVO");
		tourEsperado.setEmpleado(empleadoService.encontrar(1).get());
		tourEsperado.setSeguro(seguroService.encontrar(1).get());
		tourEsperado.setFechaLlegada(new Date());
		tourEsperado.setFechaSalida(new Date());
		Tour tourValidar = tourDAO.save(tourEsperado);
		assertEquals(tourEsperado,tourValidar);
	}

	@Test
	void agregarPaquete(){
		Municipio municipio = municipioService.encontrar(15).get();
		Paquete paqueteEsperado = new Paquete(150000, "Paquete Argelia", municipio);

		Paquete paqueteValidado = paqueteService.guardar(paqueteEsperado);

		assertEquals(paqueteEsperado, paqueteValidado, "El paquete guardado debe tener asociado un " +
				"municipio único y de obligatoriedad tener registrados los atributos ingresados");
	}

	@Test
	void agregarActividad(){
		Paquete paquete = paqueteService.encontrar(1).get();
		Actividad actEsperada = new Actividad("Taller de arcilla", paquete);

		Actividad actValidada = actividadService.guardar(actEsperada);

		assertEquals(actEsperada, actValidada, "La actividad es agregada correctamente" +
				"con los datos a ingresar por obligatoriedad asociada a un paquete");
	}

	@Test
	void agregarPersona(){
		Date fecha = new Date();
		fecha.setMonth(5);
		fecha.setYear(2002);
		fecha.setDate(15);
		TipoIdentificacion tId = tIdservice.encontrar(1).get();
		Persona personaEsperada = new Persona(1192344523,"Genesis", "Vargas", fecha, tId);

		Persona personaValidada = personaService.guardar(personaEsperada);

		assertEquals(personaEsperada, personaValidada);
	}

	@Test
	void agregarEmpleado(){
		Persona persona = personaService.encontrar(1192344523).get();
		Cargo cargo = cargoService.encontrar(1).get();
		Date fecha = new Date();
		Empleado empleadoEsperado = new Empleado(fecha, cargo, persona);

		Empleado empleadoValidado = empleadoService.guardar(empleadoEsperado);

		assertEquals(empleadoEsperado, empleadoValidado);
	}

	@Test
	void agregarPasajero(){
		Persona persona = personaService.encontrar(1192344523).get();
		Pasajero pasajeroEsperado = new Pasajero(false, persona);

		Pasajero pasajeroValidado = pasajeroService.guardar(pasajeroEsperado);

		assertEquals(pasajeroEsperado, pasajeroValidado, "Agrega a un pasajero");
	}

	@Test
	void guardarCompra(){
		Compra compraEsperada = new Compra();
		compraEsperada.setReserva(reservaDAO.findById(1).get());
		compraEsperada.setFecha(LocalDateTime.now());
		compraEsperada.setTotalCompra(50000);
		compraEsperada.setIdCompra((long)2245667);
		compraEsperada.setCantidadPasajeros(2);
		compraEsperada.setEstado("PENDIENTE");
		compraEsperada.setTour(tourService.encontrar(1).get());
		compraEsperada.setUsuario(usuarioService.encontrar(1).get());
		compraService.guardar(compraEsperada);

		Compra compraGuardada = compraService.encontrar((long)2245667).get();
		assertEquals(compraEsperada,compraGuardada,"La compra guardada debe ser la misma a la esperada");
	}

	@Test
	void validarDetallesCompra(){
		Compra compraEsperada = new Compra();
		compraEsperada.setReserva(reservaDAO.findById(1).get());
		compraEsperada.setFecha(LocalDateTime.now());
		compraEsperada.setTotalCompra(170000);
		compraEsperada.setIdCompra((long)9931854);
		compraEsperada.setCantidadPasajeros(2);
		compraEsperada.setEstado("PENDIENTE");
		compraEsperada.setTour(tourService.encontrar(1).get());
		compraEsperada.setUsuario(usuarioService.encontrar(1).get());
		compraService.guardar(compraEsperada);

		Compra compraGuardada = compraService.encontrar((long)9931854).get();

		DetalleCompra detalleCompra = new DetalleCompra();
		detalleCompra.setCompra(compraGuardada);
		detalleCompra.setFecha(new Date());
		detalleCompra.setPasajero(pasajeroService.encontrar(1).get());
		detalleCompra.setValorUnit(80000);

		DetalleCompra detalleCompra2 = new DetalleCompra();
		detalleCompra2.setCompra(compraGuardada);
		detalleCompra2.setFecha(new Date());
		detalleCompra2.setPasajero(pasajeroService.encontrar(2).get());
		detalleCompra2.setValorUnit(90000);

		detalleCompraService.guardar(detalleCompra);
		detalleCompraService.guardar(detalleCompra2);

		assertEquals(detalleCompra.getValorUnit()+detalleCompra2.getValorUnit(),compraGuardada.getTotalCompra(),"El valor total de la compra debe ser igual a los dos totales de sus detalles compra");
	}


	@Test
	void editarPersona(){
		Persona personaEsperada = personaService.encontrar(1192123425).get();
		personaEsperada.setCorreo("genesisnuevo@gmail.com");

		Persona personaValidada = personaService.guardar(personaEsperada);
		assertEquals(personaValidada.getCorreo(), personaEsperada.getCorreo());
	}

	@Test
	void deshabilitarUsuario() {
		Usuario usuarioEsperado = usuarioService.encontrar(1).get();
		usuarioEsperado.setEstado(false);

		Usuario usuarioValidado = usuarioService.guardar(usuarioEsperado);

		assertEquals(false, usuarioValidado.getEstado(),"Deshabilita a un usuario");
	}

	@Test
	void habilitarUsuario() {
		Usuario usuarioEsperado = usuarioService.encontrar(1).get();
		usuarioEsperado.setEstado(true);

		Usuario usuarioValidado = usuarioService.guardar(usuarioEsperado);

		assertEquals(true, usuarioValidado.getEstado(),"Deshabilita a un usuario");
	}

	@Test
	void deshabilidarEmpleado(){
		Empleado empleadoEsperado = empleadoService.encontrar(1).get();
		empleadoEsperado.setEstado(false);

		Empleado empleadoValidado = empleadoService.guardar(empleadoEsperado);

		assertEquals(false, empleadoValidado.getEstado());
	}

	@Test
	void habilidarEmpleado(){
		Empleado empleadoEsperado = empleadoService.encontrar(1).get();
		empleadoEsperado.setEstado(true);

		Empleado empleadoValidado = empleadoService.guardar(empleadoEsperado);

		assertEquals(true, empleadoValidado.getEstado());
	}

	@Test
	void editarPaquete(){
		Paquete paqueteEsperado = paqueteService.encontrar(1).get();
		paqueteEsperado.setNombre("Paquete mejorado Pamplona");

		Paquete paqueteValidadado = paqueteService.guardar(paqueteEsperado);

		assertEquals("Paquete mejorado Pamplona", paqueteValidadado.getNombre());
	}

	@Test
	void editarTour(){
		Tour tourEsperado = tourService.encontrar(1).get();
		tourEsperado.setMaxCupos((short) 30);

		Tour tourValidadado = tourService.guardar(tourEsperado);

		assertEquals((short)30, tourValidadado.getMaxCupos());
	}

	@Test
	void cancelarCompra(){
		Compra compraEsperada = compraService.encontrar((long)2245667).get();
		compraEsperada.setEstado("CANCELADO");
		Compra compra = compraService.guardar(compraEsperada);
		assertEquals("CANCELADO", compraEsperada.getEstado());
	}

}
