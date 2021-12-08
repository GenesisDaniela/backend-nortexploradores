package nexp.com.app;

import nexp.com.app.dao.ReservaDAO;
import nexp.com.app.dao.TourDAO;
import nexp.com.app.model.Devolucion;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AppApplicationTestsIT1 {

	@Autowired
	DevolucionService devolucionService;

	@Test
	void registrarDevoluciones() {
//		Compra compra =
//		Devolucion devolucion = new Devolucion(20000, LocalDate.now(), compraService.encontrar(44136804).get());
//		assertEquals(paqueteService.encontrar(1), paqueteService.encontrar(1));
	}


}
