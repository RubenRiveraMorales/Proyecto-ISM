package modelo.pojo;

public class RespuestaLoginApp {
    private Boolean error;
    private String Mensaje;
    private Cliente clienteSesion;

    public RespuestaLoginApp() {
    }

    public RespuestaLoginApp(Boolean error, String Mensaje, Cliente clienteSesion) {
        this.error = error;
        this.Mensaje = Mensaje;
        this.clienteSesion = clienteSesion;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public Cliente getClienteSesion() {
        return clienteSesion;
    }

    public void setClienteSesion(Cliente clienteSesion) {
        this.clienteSesion = clienteSesion;
    } 
     
}
