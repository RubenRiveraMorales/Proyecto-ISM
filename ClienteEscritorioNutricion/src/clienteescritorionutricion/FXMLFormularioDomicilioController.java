/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion;

import clienteescritorionutricion.interfaz.IRespuesta;
import clienteescritorionutricion.modelo.DAO.DomicilioDAO;
import clienteescritorionutricion.modelo.pojo.Domicilio;
import clienteescritorionutricion.modelo.pojo.Estado;
import clienteescritorionutricion.modelo.pojo.Mensaje;
import clienteescritorionutricion.modelo.pojo.Municipio;
import clienteescritorionutricion.modelo.pojo.Paciente;
import clienteescritorionutricion.utils.Utilidades;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author grimm
 */
public class FXMLFormularioDomicilioController implements Initializable {
    
    private Domicilio domicilio;
    private IRespuesta observador;  
    private int idPaciente;
    private int idDomicilio;

    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private ComboBox<Estado> cdEstado;
    @FXML
    private ComboBox<Municipio> cdMunicipio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarEstados();

    }    
    

public void cargarDatosDomicilio(int idPaciente) {
    Domicilio domicilio = DomicilioDAO.obtenerDomicilioPorIdPaciente(idPaciente); 
    if (domicilio != null) {
        tfCalle.setText(domicilio.getCalle());
        tfNumero.setText(domicilio.getNumero());
        tfColonia.setText(domicilio.getColonia());
        tfCodigoPostal.setText(String.valueOf(domicilio.getCodigoPostal()));
    } else {
        tfCalle.setText("");
        tfNumero.setText("");
        tfColonia.setText("");
        tfCodigoPostal.setText("");
        Utilidades.mostrarAlertaSimple("Domicilio no encontrado", "No se encontró el domicilio para este paciente.", Alert.AlertType.WARNING);
    }
}

 /*private void cargarEstados() {
        List<Estado> listaEstados = DomicilioDAO.obtenerEstados();
        if (!listaEstados.isEmpty()) {
            cdEstado.getItems().addAll(listaEstados);
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se pudieron cargar los estados.", Alert.AlertType.ERROR);
        }
    }*/

private void cargarEstados() {
        List<Estado> listaDeEstados = DomicilioDAO.obtenerEstados(); // Llama a tu método para obtener estados

        ObservableList<Estado> estados = FXCollections.observableArrayList(listaDeEstados);
        cdEstado.setItems(estados);
        cdEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarMunicipiosByEstado(newValue.getIdEstado());
            } else {
                cdMunicipio.getItems().clear(); 
            }
        });
    }
 
/* private void agregarListenerEstado() {
        cdEstado.setOnAction(event -> {
            Estado estadoSeleccionado = cdEstado.getValue();
            if (estadoSeleccionado != null) {
                int idEstado = estadoSeleccionado.getIdEstado(); 
                cargarMunicipiosByEstado(idEstado);
            }
        });
    }*/
 
 /*private void cargarMunicipiosByEstado(int idEstado) {
        List<Municipio> municipios = DomicilioDAO.obtenerMunicipiosEstado(idEstado); 
        if (!municipios.isEmpty()) {
            cdMunicipio.getItems().clear(); 
            cdMunicipio.getItems().addAll(municipios);
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se pudieron cargar los municipios.", Alert.AlertType.ERROR);
        }
    }
 */
 
 private void cargarMunicipiosByEstado(int idEstado) {
        List<Municipio> municipiosDelEstado = DomicilioDAO.obtenerMunicipiosEstado(idEstado); // Llama a tu método para obtener municipios

        ObservableList<Municipio> municipios = FXCollections.observableArrayList(municipiosDelEstado);
        cdMunicipio.setItems(municipios);
    }
 


/*
  @FXML
    private void btnGuardar(ActionEvent event) {
        if (idPaciente != 0) {
            Domicilio domicilioExistente = DomicilioDAO.obtenerDomicilioPorIdPaciente(idPaciente);
            if (domicilioExistente != null) {
                Utilidades.mostrarAlertaSimple("Información", "El paciente ya tiene un domicilio registrado.", Alert.AlertType.INFORMATION);
            } else {
                Domicilio nuevoDomicilio = new Domicilio();
                nuevoDomicilio.setCalle(tfCalle.getText());
                nuevoDomicilio.setNumero(tfNumero.getText());
                nuevoDomicilio.setColonia(tfColonia.getText());
                nuevoDomicilio.setCodigoPostal(Integer.parseInt(tfCodigoPostal.getText())); 

                Estado estadoSeleccionado = cdEstado.getValue();
                if (estadoSeleccionado != null) {
                    int idEstado = estadoSeleccionado.getIdEstado();
                    nuevoDomicilio.setIdEstado(idEstado);
                }

                Municipio municipioSeleccionado = cdMunicipio.getValue();
                if (municipioSeleccionado != null) {
                    int idMunicipio = municipioSeleccionado.getIdMunicipio();
                    nuevoDomicilio.setIdMunicipio(idMunicipio);
                }
                guardarDomicilio(nuevoDomicilio);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No se seleccionó ningún paciente.", Alert.AlertType.ERROR);
        }
    }*/
 
 @FXML
private void btnGuardar(ActionEvent event) {
    String calle = tfCalle.getText();
    String numero = tfNumero.getText();
    String colonia = tfColonia.getText();
    String codigoPostal = tfCodigoPostal.getText();
    Estado estadoSeleccionado = cdEstado.getValue();
    Municipio municipioSeleccionado = cdMunicipio.getValue();
    if (estadoSeleccionado != null && municipioSeleccionado != null) {
        Domicilio domicilioNuevo = new Domicilio();
        domicilioNuevo.setCalle(calle);
        domicilioNuevo.setNumero(numero);
        domicilioNuevo.setColonia(colonia);
        domicilioNuevo.setCodigoPostal(Integer.parseInt(codigoPostal));
        domicilioNuevo.setIdMunicipio(municipioSeleccionado.getIdMunicipio());

        guardarDomicilio(domicilioNuevo);
    } else {
        Utilidades.mostrarAlertaSimple("Seleccionar estado o municipio", 
                "Debes seleccionar un estado y municipio para poder registrar el domicilio", Alert.AlertType.INFORMATION);
    }
}


    private void guardarDomicilio(Domicilio domicilio){
        Mensaje msj = DomicilioDAO.registrarDomicilio(domicilio);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Paciente registrado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardadoPaciente(domicilio.getCalle());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
         
            
        }
    }
    
  
    
    
    @FXML
    private void btnCancelar(ActionEvent event) {
    }
    
    
   
      
      
    private void cerrarVentana(){
        Stage scenario =(Stage) tfCalle.getScene().getWindow();
        scenario.close();
    }
    
    
}
