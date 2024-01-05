/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion.modelo.DAO;

import clienteescritorionutricion.modelo.ConexionWS;
import clienteescritorionutricion.modelo.pojo.Mensaje;
import clienteescritorionutricion.modelo.pojo.Paciente;
import clienteescritorionutricion.modelo.pojo.RespuestaHTTP;
import clienteescritorionutricion.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author grimm
 */
 public class PacientesDAO {
    
    
    public static HashMap<String, Object> obtenerPacientePorMedico(int idMedico){
        HashMap<String, Object> respService = new LinkedHashMap();
        List<Paciente> pacientes = null;
        String url = Constantes.URL_WS+"pacientes/pacientesByMedico/" + idMedico;
        System.out.println("URL" + url);
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaPaciente = new TypeToken<List<Paciente>>(){}.getType();
            pacientes = gson.fromJson(respuesta.getContenido(), tipoListaPaciente);
            respService.put("error", false);
            respService.put("pacientes", pacientes);
        }else{
            respService.put("error", true);
            respService.put("mensaje", "Hubo un error en la petición, por el momento no se puede cargar la información de los pacientes");
        }
        return respService;
    }
    
    public static Mensaje registrarPaciente(Paciente pacienteNuevo){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"pacientes/registrar";
        String parametros = String.format("nombre=%s&apellidoPaterno=%s&apellidoMaterno=%s&fechaNacimiento=%s&sexo=%s&peso=%s&estatura=%s&tallaInicial=%s&email=%s&telefono=%s&contraseña=%s&idMedico=%d",
                  pacienteNuevo.getNombre(),
                  pacienteNuevo.getApellidoPaterno(),
                  pacienteNuevo.getApellidoMaterno(),
                  pacienteNuevo.getFechaNacimiento(),
                  pacienteNuevo.getSexo(),
                  pacienteNuevo.getPeso(),
                  pacienteNuevo.getEstatura(),
                  pacienteNuevo.getTallaInicial(),
                  pacienteNuevo.getEmail(),
                  pacienteNuevo.getTelefono(),
                  pacienteNuevo.getContraseña(),
                  pacienteNuevo.getIdMedico());
            RespuestaHTTP respuesta =ConexionWS.peticionPOST(url, parametros);
            if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje("Error al enviar la informacion del registro de paciente "+ "Porfavor intentalo mas tarde");
            }
            return msj;
    }

   public static Mensaje editarPaciente(Paciente pacienteEditado) {
    Mensaje msj = new Mensaje();
    String url = Constantes.URL_WS + "pacientes/modificar";
    String parametros = String.format("idPaciente=%d&nombre=%s&apellidoPaterno=%s&apellidoMaterno=%s&fechaNacimiento=%s&sexo=%s&peso=%s&estatura=%s&tallaInicial=%s&telefono=%s&contraseña=%s&idMedico=%d",
                  pacienteEditado.getIdPaciente(),
                  pacienteEditado.getNombre(),
                  pacienteEditado.getApellidoPaterno(),
                  pacienteEditado.getApellidoMaterno(),
                  pacienteEditado.getFechaNacimiento(),
                  pacienteEditado.getSexo(),
                  pacienteEditado.getPeso(),
                  pacienteEditado.getEstatura(),
                  pacienteEditado.getTallaInicial(),
                  pacienteEditado.getTelefono(),
                  pacienteEditado.getContraseña(),
                  pacienteEditado.getIdMedico()); 
    RespuestaHTTP respuesta = ConexionWS.peticionPUT(url, parametros); 
    if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
        Gson gson = new Gson();
        msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
    } else {
        msj.setError(true);
        msj.setMensaje("Error al editar al paciente. Por favor, inténtalo más tarde.");
    }
    return msj;
}



    
public static Mensaje eliminarPaciente(int idPaciente) {
    Mensaje msj = new Mensaje();
    String url = Constantes.URL_WS + "pacientes/eliminar";
    String parametros = "idPaciente=" + idPaciente;
    RespuestaHTTP respuesta = ConexionWS.peticionDELETE(url, parametros);
    if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
        Gson gson = new Gson();
        msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
    } else {
        msj.setError(true);
        msj.setMensaje("Error al eliminar al paciente. Por favor, inténtalo más tarde.");
    }
    return msj;
}

public static Mensaje subirFotografiaPaciente(int idPaciente, byte[] fotografia){
    Mensaje msj = new Mensaje();
    String url= Constantes.URL_WS +"pacientes/subirFoto/"+ idPaciente;
    RespuestaHTTP  respuesta = ConexionWS.peticionPUTIMAGEN(url, fotografia);
    if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
        Gson gson = new Gson();
        msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
    }else{
        msj.setError(true);
        msj.setMensaje("Error al enviar la foto del paciente, porfavor intentelo mas tarde");
    }
    return msj;
}

public static Paciente obtenerFotografiaPaciente(int idPaciente){
    Paciente paciente =null;
    String url = Constantes.URL_WS+"pacientes/obtenerFoto/"+idPaciente;
    RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
    if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK ){
       Gson gson = new Gson();
       paciente = gson.fromJson(respuesta.getContenido(), Paciente.class);
    }
    return paciente;
}


    
 
    
    
}