package ws;

import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.ClienteDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author grimm
 */

@Path("cliente")
public class ClienteWS {
    
@Path("registrar")
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje registrarPaciente(String jsonParam) {
    Mensaje msj = new Mensaje();
    try {
        Gson gson = new Gson();
        Cliente cliente = gson.fromJson(jsonParam, Cliente.class);
        if (cliente != null ) {
            msj = ClienteDAO.registrarCliente(cliente);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    } catch (Exception e) {
        msj.setError(true);
        msj.setMensaje("Error: " + e.getMessage());
    }
    return msj;
}

    @GET
    @Path("clienteByEmail/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cliente getClientePorEmail(@PathParam("correo") String correo){
        Cliente cliente = null;
        SqlSession conexionBD=MyBatisUtil.getSession();
        if(conexionBD !=null){
            try{
                cliente=conexionBD.selectOne("cliente.clienteByEmail", correo);
            }catch(Exception e){
               e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        return cliente;
    }
    
    
   @Path("editar/{idCliente}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje editarCliente(@PathParam("idCliente") int idCliente, String jsonParam) {
        Mensaje msj = new Mensaje();
        try {
            Gson gson = new Gson();
            Cliente cliente = gson.fromJson(jsonParam, Cliente.class);
            cliente.setIdCliente(idCliente); 
            if (cliente != null) {
                msj = ClienteDAO.editarCliente(cliente);
            } else {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje("Error: " + e.getMessage());
        }
        return msj;
    }   
}
