package nexp.com.app;

import lombok.extern.slf4j.Slf4j;
import nexp.com.app.model.Paquete;
import nexp.com.app.service.PaqueteService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AppApplicationTests {

	@Autowired
	PaqueteService paqueteService;

	@Test
	void probarEncontrarPaquete() {
//		assertEquals(2,2);
		assertEquals(paqueteService.encontrar(1), paqueteService.encontrar(1));
	}



}
