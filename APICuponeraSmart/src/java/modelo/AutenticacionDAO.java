package modelo;

import java.util.HashMap;
import modelo.pojo.Cliente;
import modelo.pojo.RespuestaLoginApp;
import modelo.pojo.RespuestaLoginEscritorio;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class AutenticacionDAO {
    
    public static RespuestaLoginEscritorio verificarInicioSesionEscritorio (String nombreUsuario, String password){
        
        RespuestaLoginEscritorio respuesta = new RespuestaLoginEscritorio();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("nombreUsuario", nombreUsuario);
                parametros.put("password", password);
                Usuario usuarioSesion = conexionBD.selectOne("autenticacion.loginEscritorio", parametros);
                
                if(usuarioSesion != null){
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenido(a) " + usuarioSesion.getNombreUsuario() + " al sistema");
                    respuesta.setUsuarioSesion(usuarioSesion);
                }else{
                    respuesta.setMensaje("Nombre de usuario y/o contraseña incorrectos, favor de volver a intentarlo");
                }
            }catch (Exception e){
                respuesta.setMensaje("Error: "+e.getMessage());
            }
        }else{
            respuesta.setMensaje("Error de conexion con la base de datos");
        }
        return respuesta;
    }
    
    public static RespuestaLoginApp verificarInicioSesionApp(String correo, String password){
        RespuestaLoginApp respuesta = new RespuestaLoginApp();
        SqlSession conexionBD= MyBatisUtil.getSession();
        respuesta.setError(true);
        if(conexionBD !=null){
            try{
                HashMap<String, String> parametros = new HashMap();
                parametros.put("correo", correo);
                parametros.put("password", password);
                Cliente clienteSesion = conexionBD.selectOne("autenticacion.loginApp", parametros);
                if(clienteSesion!=null){
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenido  "+ clienteSesion.getNombre()+"  al sistema");
                    respuesta.setClienteSesion(clienteSesion);
                }else{
                    
                    respuesta.setMensaje("Credenciales incorrectas");
                }
            }catch(Exception e){
                respuesta.setMensaje("Error"+ e.getMessage());
            }
        }else{
            respuesta.setMensaje("Error de conexion");
        }
        return respuesta;
    }
}
