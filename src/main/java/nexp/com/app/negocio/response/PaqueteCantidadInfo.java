package nexp.com.app.negocio.response;

public class PaqueteCantidadInfo {
    String nombreTour;
    int totalPasajeros;
    int total;
    double porcentajeVentas;
    String tipoTour;
    public PaqueteCantidadInfo(String nombreTour, String tipoTour, int totalPasajeros, int total, double porcentajeVentas) {
        this.nombreTour = nombreTour;
        this.totalPasajeros = totalPasajeros;
        this.total = total;
        this.porcentajeVentas = porcentajeVentas;
        this.tipoTour = tipoTour;
    }

    public PaqueteCantidadInfo() {
    }

    public String getTipoTour() {
        return tipoTour;
    }

    public void setTipoTour(String tipoTour) {
        this.tipoTour = tipoTour;
    }

    public double getPorcentajeVentas() {
        return porcentajeVentas;
    }

    public void setPorcentajeVentas(double porcentajeVentas) {
        this.porcentajeVentas = porcentajeVentas;
    }

    public String getNombreTour() {
        return nombreTour;
    }

    public void setNombreTour(String nombreTour) {
        this.nombreTour = nombreTour;
    }

    public int getTotalPasajeros() {
        return totalPasajeros;
    }

    public void setTotalPasajeros(int totalPasajeros) {
        this.totalPasajeros = totalPasajeros;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
