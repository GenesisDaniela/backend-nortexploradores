package nexp.com.app.negocio.response;

import java.util.Date;

public class PasajerosTour {
    int identificacion;
    String nombre;
    String correo;
    String username;
    Date fechaNac;

    public PasajerosTour() {}

    public PasajerosTour(int identificacion, String nombre, String correo, Date fechaNac, String username) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
        this.username = username;
        this.fechaNac = fechaNac;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
}
