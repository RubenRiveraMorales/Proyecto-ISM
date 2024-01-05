/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion;

import clienteescritorionutricion.modelo.pojo.Medico;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author grimm
 */
public class FXMLHomeController implements Initializable {

    @FXML
    private Label lbNombreMedico;
    @FXML
    private Label lbCedula;
    @FXML
    private Label lbNumPersonal;
    
    private Medico medicoSesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacionMedico(Medico medicoSesion){
        this.medicoSesion=medicoSesion;
        lbNombreMedico.setText(medicoSesion.getNombre()+" "+medicoSesion.getApellidoPaterno()+""+medicoSesion.getApellidoMaterno());
        lbCedula.setText("Cedula Profesional: "+medicoSesion.getNumeroCedula());
        lbNumPersonal.setText("Numero de Personal: "+medicoSesion.getNumeroPersonal());
    }

    @FXML
    private void btGestionarPaciente(ActionEvent event) {
         try{
            FXMLLoader loadvista=new FXMLLoader(getClass().getResource("FXMLAdminPaciente.fxml"));
            Parent vista=loadvista.load();
            
            FXMLAdminPacienteController controller = loadvista.getController();
            controller.inicializarInfo(medicoSesion.getIdMedico());
            
             Scene escenaAdmin = new Scene(vista);
             Stage escenarioAdmin = new Stage();
             escenarioAdmin.setScene(escenaAdmin);
             escenarioAdmin.setTitle("Pacientes");
             escenarioAdmin.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
