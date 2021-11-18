package nexp.com.app.dao;

import nexp.com.app.model.ClientePasajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientePasajeroDAO extends JpaRepository<ClientePasajero, Integer> {
}
