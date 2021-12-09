package nexp.com.app.negocio.response;

import java.time.format.DateTimeFormatter;

public class PaqueteTotal {
    String nombre;
    int total;
    public PaqueteTotal() {
    }
    public PaqueteTotal(String nombre, int total) {
        this.nombre = nombre;
        this.total = total;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}