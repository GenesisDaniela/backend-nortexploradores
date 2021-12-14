package nexp.com.app.negocio.response;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import java.time.LocalDate;
import java.util.Date;

@SqlResultSetMapping(
        name = "ReservaTabla",
        entities = {
                @EntityResult(
                        entityClass = ReservaTabla.class, // nombre de la clase actual
                        fields = {
                                @FieldResult(name = "idReserva", column = "id_reserva"),
                                @FieldResult(name = "totalCompra", column = "total_compra"),
                                @FieldResult(name = "fecha", column = "fecha"),
                                @FieldResult(name = "email", column = "email"),
                                @FieldResult(name = "cantidadPasajeros", column = "cantidad_pasajeros"),
                                @FieldResult(name = "estado", column = "estado"),

                        }
                )
        }
)
public class ReservaTabla {
    int idReserva;
    int totalCompra;
    LocalDate fecha;
    String email;
    int cantidadPasajeros;
    String estado;


    public ReservaTabla() {
    }

    public ReservaTabla(int idReserva, int totalCompra, LocalDate fecha, String email, int cantidadPasajeros, String estado) {
        this.idReserva = idReserva;
        this.totalCompra = totalCompra;
        this.fecha = fecha;
        this.email = email;
        this.cantidadPasajeros = cantidadPasajeros;
        this.estado = estado;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(int totalCompra) {
        this.totalCompra = totalCompra;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
