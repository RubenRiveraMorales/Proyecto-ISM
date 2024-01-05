/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion;

import clienteescritorionutricion.interfaz.IRespuesta;
import clienteescritorionutricion.modelo.DAO.PacientesDAO;
import clienteescritorionutricion.modelo.pojo.Domicilio;
import clienteescritorionutricion.modelo.pojo.Mensaje;
import clienteescritorionutricion.modelo.pojo.Paciente;
import clienteescritorionutricion.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author grimm
 */
public class FXMLAdminPacienteController implements Initializable,IRespuesta {
    private int idMedico;
    private int idPaciente;
    private int idDomicilio;
    private ObservableList<Paciente>pacientes;

    @FXML
    private TableView<Paciente> tvPaciente;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colFechaNacimiento;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TextField tfBusquedaPaciente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     pacientes = FXCollections.observableArrayList();
     configurarTabla();
    }    
    
    public void inicializarInfo(int idMedico){
    this.idMedico=idMedico;
    System.out.println(idMedico);
    descargarPacientes();
   inicializarBusquedaPaciente();
    }
    

        private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory("fechaNacimiento"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
    }
        
        private void descargarPacientes(){
        HashMap<String, Object> respuesta = PacientesDAO.obtenerPacientePorMedico(idMedico);
        if(!(boolean) respuesta.get("error")){
            List<Paciente> listaWS = (List<Paciente>) respuesta.get("pacientes");
            pacientes.addAll(listaWS);
            tvPaciente.setItems(pacientes);
        }else{
            Utilidades.mostrarAlertaSimple("Error ", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
        
        
        @FXML
        private void btnAgregar(ActionEvent event) {
            abrirFormularioPaciente(null);
        }
        
        

    private void abrirFormularioPaciente(Paciente paciente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRegistroPaciente.fxml"));
            Parent root = loader.load();
            FXMLRegistroPacienteController editarPacienteController = loader.getController();
            editarPacienteController.inizializarInfo(paciente, idMedico,idPaciente,  this);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar Paciente");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        
        /*
    @FXML
    private void btnAgregar(ActionEvent event) {
        Agregar(null);
    }

*/
    @FXML
    private void btnEditar(ActionEvent event) {
    Paciente pacienteSeleccionado = tvPaciente.getSelectionModel().getSelectedItem();
    if (pacienteSeleccionado != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRegistroPaciente.fxml"));
            Parent root = loader.load();
            FXMLRegistroPacienteController editarPacienteController = loader.getController();
            editarPacienteController.inicializarPaciente(pacienteSeleccionado,idMedico,this);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editar Paciente");
            stage.setScene(scene);
            stage.showAndWait(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        Utilidades.mostrarAlertaSimple("Error", "Selecciona un paciente para editar.", Alert.AlertType.ERROR);
    }
}


    @FXML
    private void btnEliminar(ActionEvent event) {
        Paciente pacienteSeleccionado = tvPaciente.getSelectionModel().getSelectedItem();
        if(pacienteSeleccionado !=null){
            Optional<ButtonType> respuesta=Utilidades.mostrarAlertaConInformacion("Confirmar eliminacion", "Estas seguro de elimar al paciente: "+pacienteSeleccionado.getNombre()+" Esta accion no se puede revertir");
            if(respuesta.get() == ButtonType.OK){
                eliminarPaciente(pacienteSeleccionado.getIdPaciente());
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Selecciona un paciente para su elimiacion.", Alert.AlertType.ERROR);
        }
        
    }
    
   
    

    @Override
    public void notificarGuardadoPaciente(String nombrePaciente) {
        System.out.println("Nombre paciente: "+ nombrePaciente);
        pacientes.clear();
        descargarPacientes();
    }
    
    public void eliminarPaciente(int idPaciente) {
    Mensaje mensaje = PacientesDAO.eliminarPaciente(idPaciente);
    if (!mensaje.isError()) {
        pacientes.removeIf(paciente -> paciente.getIdPaciente() == idPaciente);
        Utilidades.mostrarAlertaSimple("Éxito", "Paciente eliminado exitosamente.", Alert.AlertType.INFORMATION);
    } else {
        Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar al paciente. Por favor, inténtalo más tarde.", Alert.AlertType.ERROR);
    }
}

   /* @FXML
    private void btnDomicilio(ActionEvent event) {
        Paciente pacienteSeleccionado = tvPaciente.getSelectionModel().getSelectedItem(); 
        if (pacienteSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioDomicilio.fxml"));
                Parent root = loader.load();
                FXMLFormularioDomicilioController domicilioController = loader.getController();
                domicilioController.cargarDatosDomicilio(pacienteSeleccionado.getIdPaciente()); 

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Domicilio de " + pacienteSeleccionado.getNombre()); 
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
             Utilidades.mostrarAlertaSimple("Error", "Selecciona un paciente para ver su domicilio.", Alert.AlertType.ERROR);

        }

    }*/
    @FXML
    private void btnDomicilio(ActionEvent event){
        irFormularioDomicilio(idPaciente);
    }
    
    private void irFormularioDomicilio(int idPaciente){
        Paciente pacienteSeleccionado = tvPaciente.getSelectionModel().getSelectedItem();
        if(pacienteSeleccionado != null){
            try{
                FXMLLoader loadVista = new FXMLLoader(getClass().getResource("FXMLFormularioDomicilio.fxml"));
                Parent vista = loadVista.load();

                FXMLFormularioDomicilioController agregarDomicilioController = loadVista.getController();
                agregarDomicilioController.cargarDatosDomicilio(idPaciente);

                Scene escenaAdmin = new Scene(vista);
                Stage escenarioAdmin = new Stage();
                escenarioAdmin.setScene(escenaAdmin);
                escenarioAdmin.setTitle("Agregar domicilio");
                escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
                escenarioAdmin.showAndWait();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de paciente", "Para poder agregar un domicilio debes "
                    + "seleccionar un paciente de la tabla", Alert.AlertType.WARNING);
        }
    }
    
    
    private void inicializarBusquedaPaciente(){
        if(pacientes!=null){
           FilteredList<Paciente> filtroPaciente= new FilteredList<>(pacientes, p-> true); 
           tfBusquedaPaciente.textProperty().addListener(new ChangeListener<String>(){
               @Override
               public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroPaciente.setPredicate(pacienteBusqueda -> {
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        
                        String lowerFilter = newValue.toLowerCase();
                        //Reglas de filtrado
                        if(pacienteBusqueda.getNombre().toLowerCase().contains(lowerFilter)){//Si lo quiseramos buscar por algo específico pondríamos equal
                            return true;
                        }
                        if(pacienteBusqueda.getApellidoPaterno().toLowerCase().contains(lowerFilter)){//Si lo quiseramos buscar por algo específico pondríamos equal
                            return true;
                        }
                        if(pacienteBusqueda.getApellidoMaterno().toLowerCase().contains(lowerFilter)){//Si lo quiseramos buscar por algo específico pondríamos equal
                            return true;
                        }
                        if(pacienteBusqueda.getTelefono().toLowerCase().contains(lowerFilter)){//Si lo quiseramos buscar por algo específico pondríamos equal
                            return true;
                        }
                        return false;
                        
                        });
                SortedList<Paciente> pacientesOrdenados = new SortedList<>(filtroPaciente);
                pacientesOrdenados.comparatorProperty().bind(tvPaciente.comparatorProperty());
                tvPaciente.setItems(pacientesOrdenados);
               } 
           });
        }
    }    
                   
                   
               
        
        
  }

        
    
    
    
   
    
    
  
    

