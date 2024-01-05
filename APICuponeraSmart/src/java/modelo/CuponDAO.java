package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;
import modelo.pojo.Cupon;
import modelo.pojo.Sucursal;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class CuponDAO {
    
    public static List<Cupon> obtenerCupon(Integer idCupon){
        List<Cupon> cupon = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                cupon = conexionBD.selectList("cupon.obtenerCupones", idCupon);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        return cupon;
    }
    
    public static List<Cupon> obtenerCuponPromo(Integer idUsuario){
       
        List<Cupon> cupon = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                System.out.println("ID de Usuario recibido: " + idUsuario);
                Usuario usuario = conexionBD.selectOne("usuario.obtenerUsuarioIdRol", idUsuario);

                if (usuario != null) {
                    System.out.println("ID de Rol del Usuario: " + usuario.getIdRol());

                    if (usuario.getIdRol() == 1) {
                        cupon = conexionBD.selectList("cupon.obtenerCupones");
                    } else if (usuario.getIdRol() == 2 && usuario.getIdPromocion() != null) {
                        cupon = conexionBD.selectList("cupon.obtenerCuponesPromocion", usuario.getIdPromocion());
                    }
                } else {
                    System.out.println("No se encontró un usuario con el ID proporcionado.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return cupon;
    }
}



