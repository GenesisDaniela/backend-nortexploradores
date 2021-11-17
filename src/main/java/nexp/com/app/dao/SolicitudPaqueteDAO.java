package nexp.com.app.dao;

import nexp.com.app.model.SolicitudTour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudPaqueteDAO extends JpaRepository<SolicitudTour, Integer> {
}
