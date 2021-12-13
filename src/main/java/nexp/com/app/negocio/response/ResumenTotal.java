package nexp.com.app.negocio.response;

import nexp.com.app.model.Paquete;

public class ResumenTotal {
    int totalTours;
    int totalVentas;
    int totalDevoluciones;
    String paqueteMasVendido;
    double porcentajeMasVendido;
    String paqueteMenosVendido;

    public ResumenTotal(){}
    public ResumenTotal(int totalTours, int totalVentas, int totalDevoluciones, String paqueteMasVendido, double porcentajeMasVendido, String paqueteMenosVendido) {
        this.totalTours = totalTours;
        this.totalVentas = totalVentas;
        this.totalDevoluciones = totalDevoluciones;
        this.paqueteMasVendido = paqueteMasVendido;
        this.porcentajeMasVendido = porcentajeMasVendido;
        this.paqueteMenosVendido = paqueteMenosVendido;
    }

    public int getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(int totalVentas) {
        this.totalVentas = totalVentas;
    }

    public int getTotalDevoluciones() {
        return totalDevoluciones;
    }

    public void setTotalDevoluciones(int totalDevoluciones) {
        this.totalDevoluciones = totalDevoluciones;
    }

    public String getPaqueteMasVendido() {
        return paqueteMasVendido;
    }

    public void setPaqueteMasVendido(String paqueteMasVendido) {
        this.paqueteMasVendido = paqueteMasVendido;
    }

    public double getPorcentajeMasVendido() {
        return porcentajeMasVendido;
    }

    public void setPorcentajeMasVendido(double porcentajeMasVendido) {
        this.porcentajeMasVendido = porcentajeMasVendido;
    }

    public String getPaqueteMenosVendido() {
        return paqueteMenosVendido;
    }

    public void setPaqueteMenosVendido(String paqueteMenosVendido) {
        this.paqueteMenosVendido = paqueteMenosVendido;
    }

    public int getTotalTours() {
        return totalTours;
    }

    public void setTotalTours(int totalTours) {
        this.totalTours = totalTours;
    }
}
