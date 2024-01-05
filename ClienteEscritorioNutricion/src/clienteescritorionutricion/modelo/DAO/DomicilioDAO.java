/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion.modelo.DAO;

import clienteescritorionutricion.modelo.ConexionWS;
import clienteescritorionutricion.modelo.pojo.Domicilio;
import clienteescritorionutricion.modelo.pojo.Estado;
import clienteescritorionutricion.modelo.pojo.Mensaje;
import clienteescritorionutricion.modelo.pojo.Municipio;
import clienteescritorionutricion.modelo.pojo.Paciente;
import clienteescritorionutricion.modelo.pojo.RespuestaHTTP;
import clienteescritorionutricion.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author grimm
 */
public class DomicilioDAO {
    public static List<Estado>obtenerEstados(){
        List <Estado> estados= new ArrayList<>();
        String url =Constantes.URL_WS+"catalogo/obtenerEstados";
        RespuestaHTTP respuesta= ConexionWS.peticionGET(url);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
             Type tipoListaEstado = new TypeToken<List<Estado>>(){}.getType();
             Gson gson = new Gson();
             estados= gson.fromJson(respuesta.getContenido(), tipoListaEstado);
        }
        return estados;
    }
    
     public static List<Municipio>obtenerMunicipiosEstado(int idEstado){
        List <Municipio> municipios= new ArrayList<>();
        String url =Constantes.URL_WS+"catalogo/obtenerMunicipiosEstados/"+idEstado;
        RespuestaHTTP respuesta= ConexionWS.peticionGET(url);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
             Type tipoListaMunicipios = new TypeToken<List<Municipio>>(){}.getType();
             Gson gson = new Gson();
             municipios= gson.fromJson(respuesta.getContenido(), tipoListaMunicipios);
        }
        return municipios;
    }
     
    /* public static Mensaje registrarDomicilio(Domicilio domicilioPaciente){
        Mensaje msj = new Mensaje();
        String url =Constantes.URL_WS+"domicilio/registrar";
        Gson gson = new Gson();
        String parametros=gson.toJson(domicilioPaciente);
        RespuestaHTTP respuesta= ConexionWS.peticionPOSTJSON(url,parametros);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
             msj=gson.fromJson(respuesta.getContenido(), Mensaje.class);
         }else{
             msj.setError(true);
             msj.setMensaje("Los sentimos , por el momento no se puede guardar la informacion, intentelo mas tarde");
         }
         return msj;
    }*/
     
     public static Mensaje registrarDomicilio(Domicilio domicilioPaciente){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "domicilio/registrar";
        Gson gson = new Gson();
        String parametros = gson.toJson(domicilioPaciente);
        System.out.println(parametros);
        RespuestaHTTP respuesta = ConexionWS.peticionPOSTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){

            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            System.out.println(respuesta.getCodigoRespuesta());
            msj.setError(true);
            msj.setMensaje("Lo sentimos, por el momento no es posible guardar la información del domicilio.");
        }
        return msj;
    }
     
      public static Domicilio obtenerDomicilioPorIdPaciente(int idPaciente) {
        Domicilio domicilio = null;
        String url = Constantes.URL_WS + "domicilio/obtenerDomicilioPaciente/" + idPaciente; 
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            domicilio = gson.fromJson(respuesta.getContenido(), Domicilio.class);
        }
        return domicilio;
    }
    
}
