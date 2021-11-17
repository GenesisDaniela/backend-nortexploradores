package nexp.com.app.dao;

import nexp.com.app.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaDAO extends JpaRepository<Reserva, Integer> {
}
