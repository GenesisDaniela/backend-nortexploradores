package nexp.com.app.negocio.response;

public class TotalPaquete {
    int VentaMes;
    String total;

    public TotalPaquete(int ventaMes, String total) {
        VentaMes = ventaMes;
        total = total;
    }
    public TotalPaquete(){}

    public int getVentaMes() {
        return VentaMes;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setVentaMes(int ventaMes) {
        VentaMes = ventaMes;
    }
}
