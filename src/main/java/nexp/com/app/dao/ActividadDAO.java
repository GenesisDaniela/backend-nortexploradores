package nexp.com.app.dao;

import nexp.com.app.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadDAO extends JpaRepository<Actividad, Integer> {
}
