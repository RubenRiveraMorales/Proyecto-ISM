/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion.modelo.DAO;

import clienteescritorionutricion.modelo.ConexionWS;
import clienteescritorionutricion.modelo.pojo.RespuestaHTTP;
import clienteescritorionutricion.modelo.pojo.RespuestaLogin;
import clienteescritorionutricion.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author grimm
 */
public class InicioSesionDAO {
    public static RespuestaLogin iniciarSesion(String numeroPersonal, String contraseña){
    RespuestaLogin respuesta = new RespuestaLogin();
        String url= Constantes.URL_WS+"autenticacion/loginEscritorio";
        String parametros=String.format("numeroPersonal=%s&contraseña=%s", numeroPersonal, contraseña);
        RespuestaHTTP respuestaPeticion= ConexionWS.peticionPOST(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            respuesta =gson.fromJson(respuestaPeticion.getContenido(), RespuestaLogin.class);
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Hubo un error al  procesar la solicitud :(");
        }
       
        return respuesta;
    }
           
    
}
