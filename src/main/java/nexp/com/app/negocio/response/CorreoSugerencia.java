package nexp.com.app.negocio.response;

public class CorreoSugerencia {
    String correo;
    String respsugerencia;
    int idSugerencia;

    public CorreoSugerencia(String correo, String sugerencia, int idSugerencia) {
        this.correo = correo;
        this.idSugerencia =idSugerencia;
        this.respsugerencia = sugerencia;
    }

    public CorreoSugerencia() {
    }

    public int getIdSugerencia() {
        return idSugerencia;
    }

    public void setIdSugerencia(int idSugerencia) {
        this.idSugerencia = idSugerencia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRespsugerencia() {
        return respsugerencia;
    }

    public void setRespsugerencia(String respsugerencia) {
        this.respsugerencia = respsugerencia;
    }
}
