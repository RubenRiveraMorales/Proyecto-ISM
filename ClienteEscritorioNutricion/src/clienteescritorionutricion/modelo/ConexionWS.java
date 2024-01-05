/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion.modelo;

import clienteescritorionutricion.modelo.pojo.RespuestaHTTP;
import clienteescritorionutricion.utils.Constantes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author grimm
 */
public class ConexionWS {
    public static RespuestaHTTP peticionGET(String url){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try{
            URL urlDestino = new URL(url); 
            HttpURLConnection conexionHttp=(HttpURLConnection)urlDestino.openConnection();
            conexionHttp.setRequestMethod("GET");
            int codigoRespuesta=conexionHttp.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if(codigoRespuesta==HttpURLConnection.HTTP_OK){
               respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
                    //convertir el stringbuffer en una cadena normal
            }else{
                respuesta.setContenido("Codigo de respuesta HTTP: "+codigoRespuesta);
                System.out.println("Codigo ws" + codigoRespuesta);
            }
        }catch(MalformedURLException e){      
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error en la direccion de conexion");
        } catch(IOException e){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error en la peticion");
        }
        return respuesta;
            
        }
    
    public static RespuestaHTTP peticionPOST(String url, String parametros){
         RespuestaHTTP respuesta = new RespuestaHTTP();
        try{
            URL urlDestino = new URL(url); 
            HttpURLConnection conexionHttp=(HttpURLConnection)urlDestino.openConnection();
            conexionHttp.setRequestMethod("POST");
            conexionHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexionHttp.setDoOutput(true);
            OutputStream os= conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
             int codigoRespuesta=conexionHttp.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if(codigoRespuesta==HttpURLConnection.HTTP_OK){
               respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
            }else{
                respuesta.setContenido("Codigo de respuesta HTTP: "+codigoRespuesta);
            }
                  
        }catch(MalformedURLException e){      
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error en la direccion de conexion");
        } catch(IOException e){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error no se puede realizar la peticion");
        }
        return respuesta;
    }
    
    
    public static RespuestaHTTP peticionPUT(String url, String parametros){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try{
            URL urlDestino = new URL(url); 
            HttpURLConnection conexionHttp=(HttpURLConnection)urlDestino.openConnection();
            conexionHttp.setRequestMethod("PUT");
            conexionHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexionHttp.setDoOutput(true);
            OutputStream os= conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
             int codigoRespuesta=conexionHttp.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if(codigoRespuesta==HttpURLConnection.HTTP_OK){
               respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
            }else{
                respuesta.setContenido("Codigo de respuesta HTTP: "+codigoRespuesta);
            }
                  
        }catch(MalformedURLException e){      
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error en la direccion de conexion");
        } catch(IOException e){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error no se puede realizar la peticion");
        }
        return respuesta;
    }
    
    public static RespuestaHTTP peticionDELETE(String url, String parametros) {
    RespuestaHTTP respuesta = new RespuestaHTTP();
    try {
        URL urlDestino = new URL(url);
        HttpURLConnection conexionHttp = (HttpURLConnection) urlDestino.openConnection();
        conexionHttp.setRequestMethod("DELETE");
        conexionHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexionHttp.setDoOutput(true);
        OutputStream os = conexionHttp.getOutputStream();
        os.write(parametros.getBytes());
        os.flush();
        os.close();
        int codigoRespuesta = conexionHttp.getResponseCode();
        respuesta.setCodigoRespuesta(codigoRespuesta);
        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
            respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
        } else {
            respuesta.setContenido("Código de respuesta HTTP: " + codigoRespuesta);
        }
    } catch (MalformedURLException e) {
        respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
        respuesta.setContenido("Error en la dirección de conexión");
    } catch (IOException e) {
        respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
        respuesta.setContenido("Error en la petición");
    }
    return respuesta;
}
 public static RespuestaHTTP peticionPOSTJSON(String url, String json){
         RespuestaHTTP respuesta = new RespuestaHTTP();
        try{
            URL urlDestino = new URL(url); 
            HttpURLConnection conexionHttp=(HttpURLConnection)urlDestino.openConnection();
            conexionHttp.setRequestMethod("POST");
            conexionHttp.setRequestProperty("Content-Type", "application/json");
            conexionHttp.setDoOutput(true);
            OutputStream os= conexionHttp.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();
             int codigoRespuesta=conexionHttp.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if(codigoRespuesta==HttpURLConnection.HTTP_OK){
               respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
            }else{
                respuesta.setContenido("Codigo de respuesta HTTP: "+codigoRespuesta);
            }
                  
        }catch(MalformedURLException e){      
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error en la direccion de conexion");
        } catch(IOException e){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error no se puede realizar la peticion");
        }
        return respuesta;
    }
    
    
  
    
        
    private static String obtenerContenidoWS(InputStream inputWS) throws IOException{
                BufferedReader in= new BufferedReader(new InputStreamReader(inputWS));
                String inputLine;
                StringBuffer respuestaEntrada = new StringBuffer();
                while((inputLine = in.readLine()) != null){
                    respuestaEntrada.append(inputLine);
                }
                in.close();
                return respuestaEntrada.toString();
                    //convertir el stringbuffer en una cadena normal
        }
    
    public static RespuestaHTTP peticionPUTIMAGEN(String url, byte[] img){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try{
            URL urlDestino = new URL(url); 
            HttpURLConnection conexionHttp=(HttpURLConnection)urlDestino.openConnection();
            conexionHttp.setRequestMethod("PUT");
            conexionHttp.setDoOutput(true);
            OutputStream os= conexionHttp.getOutputStream();
            os.write(img);
            os.flush();
            os.close();
             int codigoRespuesta=conexionHttp.getResponseCode();
            respuesta.setCodigoRespuesta(codigoRespuesta);
            if(codigoRespuesta==HttpURLConnection.HTTP_OK){
               respuesta.setContenido(obtenerContenidoWS(conexionHttp.getInputStream()));
            }else{
                respuesta.setContenido("Codigo de respuesta HTTP: "+codigoRespuesta);
            }
                  
        }catch(MalformedURLException e){      
            respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
            respuesta.setContenido("Error en la direccion de conexion");
        } catch(IOException e){
            respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
            respuesta.setContenido("Error no se puede realizar la peticion");
        }
        return respuesta;
    }

    public static RespuestaHTTP peticionPOST(String url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
 }

