/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion;

import clienteescritorionutricion.modelo.ConexionWS;
import clienteescritorionutricion.modelo.DAO.InicioSesionDAO;
import clienteescritorionutricion.modelo.pojo.Medico;
import clienteescritorionutricion.modelo.pojo.RespuestaHTTP;
import clienteescritorionutricion.modelo.pojo.RespuestaLogin;
import clienteescritorionutricion.utils.Constantes;
import clienteescritorionutricion.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author grimm
 */
public class FXMLInicioSesionController implements Initializable {
    
    @FXML
    private TextField tfNumeroP;
    @FXML
    private PasswordField tfContraseña;
    @FXML
    private Label lbErrorPersonal;
    @FXML
    private Label lbErrorPassword;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void iniciarSesion(ActionEvent event) {
       String numeroPersonal = tfNumeroP.getText();
       String contraseña = tfContraseña.getText();
       lbErrorPersonal.setText("");
       lbErrorPassword.setText("");
       boolean isValido=true;
       if(numeroPersonal.isEmpty()){
           lbErrorPersonal.setText("El numero personal es obligatirio");
           isValido=false;
       }
       if(contraseña.isEmpty()){
           lbErrorPassword.setText("La contraseña es obligatoria");
           isValido= false;
       }
       if(isValido){
           verificarCredenciales(numeroPersonal,contraseña);
       }
    }
    
    private void verificarCredenciales(String numeroPersonal, String contraseña){
        RespuestaLogin respuesta = InicioSesionDAO.iniciarSesion(numeroPersonal, contraseña);
        if(respuesta.getError()==false){
            Utilidades.mostrarAlertaSimple("Credenciales correctas", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            irPantallaPrincipal(respuesta.getMedicoSesion());
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
        
    }
    
    private void irPantallaPrincipal(Medico medico){
        Stage stageActual = (Stage)tfNumeroP.getScene().getWindow();
        try{
        FXMLLoader loadMain= new FXMLLoader(getClass().getResource("FXMLHome.fxml"));
        Parent vista = loadMain.load();
        FXMLHomeController controladorHome = loadMain.getController();
        controladorHome.inicializarInformacionMedico(medico);
        Scene escena = new Scene(vista);
        stageActual.setScene(escena);
        stageActual.setTitle("Home");
        stageActual.show();
    }catch(IOException ex){
        ex.printStackTrace();
    }
 }
    
}
