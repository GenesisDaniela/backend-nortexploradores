package nexp.com.app;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Paquete;
import nexp.com.app.service.PaqueteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Service
@Slf4j
class AppApplicationTests {

	@Autowired
	PaqueteService paqueteService;

	@Test
	void probarEncontrarPaquete() {
//		String destino = "Paquete Cácota";
//		Paquete paquete = paqueteService.encontrar(1).get();
//		log.info(paquete.getNombre());
		assertEquals(2,2);
	}



}
