package modelo;


import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author grimm
 */
public class ClienteDAO {
      
    public static Mensaje registrarCliente(Cliente cliente) {
    Mensaje msj = new Mensaje();
    msj.setError(true);
    SqlSession conexionBD = MyBatisUtil.getSession();
    if (conexionBD != null) {
        try {
            Cliente clienteExistente = conexionBD.selectOne("cliente.clienteByEmail", cliente.getCorreo());
            if (clienteExistente != null) {
                msj.setMensaje("Este correo electrónico ya está en uso");
            } else {
                int filasAfectadas = conexionBD.insert("cliente.registrar", cliente);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Se han registrado sus datos correctamente");
                } else {
                    msj.setMensaje("Error al registrar al cliente");
                }
            }
        } catch (Exception e) {
            msj.setMensaje("Error: " + e.getMessage());
        } finally {
            conexionBD.close();
        }
    } else {
        msj.setMensaje("No hay conexión, por favor inténtelo más tarde");
    }

    return msj;
}
    
     public static Mensaje editarCliente(Cliente cliente) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("cliente.editar", cliente);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Se han actualizado los datos del cliente correctamente");
                } else {
                    msj.setMensaje("No se encontró al cliente para actualizar");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("No hay conexión, por favor inténtelo más tarde");
        }

        return msj;
    }
}
