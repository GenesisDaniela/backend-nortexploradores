package nexp.com.app.negocio.response;
public class PaqueteCantidad {
    int cantidad;
    String mes;
    public PaqueteCantidad(int cantidad, String mes) {
        this.cantidad = cantidad;
        this.mes = mes;
    }
    public PaqueteCantidad(){}

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public String getMes() {
        return mes;
    }
    public void setMes(String mes) {
        this.mes = mes;
    }
}