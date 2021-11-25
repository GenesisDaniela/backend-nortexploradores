package nexp.com.app;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.dao.TourDAO;
import nexp.com.app.model.Paquete;
import nexp.com.app.model.Tour;
import nexp.com.app.service.EmpleadoService;
import nexp.com.app.service.PaqueteService;
import nexp.com.app.service.SeguroService;
import nexp.com.app.service.TourService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

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
	TourService tourService;

	@Autowired
	TourDAO tourDAO;

	@Test
	void probarEncontrarPaquete() {
//		assertEquals(2,2);
		assertEquals(paqueteService.encontrar(1), paqueteService.encontrar(1));
	}

	@Test
	void guardarTour() {

		Tour tourEsperado = new Tour();
		tourEsperado.setCantCupos(0);
		tourEsperado.setMaxCupos((short)10);
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


}
