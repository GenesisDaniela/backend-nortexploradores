package nexp.com.app.negocio.response;

public class TotalClientes {
    int totalClientes;

    public TotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }
    public TotalClientes(){}
    public int getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }
}
