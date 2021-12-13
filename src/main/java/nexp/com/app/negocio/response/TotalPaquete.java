package nexp.com.app.negocio.response;

public class TotalPaquete {
    int VentaMes;

    public TotalPaquete(int ventaMes) {
        VentaMes = ventaMes;
    }
    public TotalPaquete(){};
    public int getVentaMes() {
        return VentaMes;
    }

    public void setVentaMes(int ventaMes) {
        VentaMes = ventaMes;
    }
}
