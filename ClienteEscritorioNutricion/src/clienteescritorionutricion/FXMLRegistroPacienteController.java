/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion;

import clienteescritorionutricion.interfaz.IRespuesta;
import clienteescritorionutricion.modelo.DAO.PacientesDAO;
import clienteescritorionutricion.modelo.pojo.Medico;
import clienteescritorionutricion.modelo.pojo.Mensaje;
import clienteescritorionutricion.modelo.pojo.Paciente;
import clienteescritorionutricion.utils.Utilidades;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author grimm
 */
public class FXMLRegistroPacienteController implements Initializable {
    private Paciente paciente;
    private int idMedico;
    private int idPaciente;
    private String sexo;
    private IRespuesta observador;
    private File imagenSeleccionada;
    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private DatePicker tfFechaNacimiento;
    private TextField tfSexo;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfEstatura;
    @FXML
    private TextField tfTallaInicial;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private PasswordField pfContraseña;
    @FXML
    private RadioButton rdMasculino;
    @FXML
    private ToggleGroup tgSexo;
    @FXML
    private RadioButton rdFemenino;
    @FXML
    private ImageView ivFoto;

    /**
     * Initializes the controller class.
     * 
     */
    

    
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        tgSexo.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            
            
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rdFemenino.isSelected()){
                    sexo = "M";
            }else{
                    sexo = "H";
                }
            }
        });
    }
    
    
    
    public void inizializarInfo(Paciente paciente,int idMedico,int idPaciente, IRespuesta observador){
        this.paciente=paciente;
        this.idMedico=idMedico;
        this.observador=observador;
        this.idPaciente=idPaciente;
        
    }
   
    
     public void inicializarPaciente(Paciente paciente, int idMedico,IRespuesta observador) {
             this.paciente=paciente;
             this.idPaciente=paciente.getIdPaciente();
             this.observador=observador;
             this.idMedico=idMedico;
         System.out.println("ID del médico: " + this.idMedico);
    if (paciente != null) {
        tfNombre.setText(paciente.getNombre());
        tfApellidoPaterno.setText(paciente.getApellidoPaterno());
        tfApellidoMaterno.setText(paciente.getApellidoMaterno());
        String patter = "yyyy-MM-dd";
        DateTimeFormatter date = DateTimeFormatter.ofPattern(patter);
    tfFechaNacimiento.setValue(LocalDate.parse(paciente.getFechaNacimiento(),date));
        if(paciente.getSexo().equals("H")){
            rdMasculino.setSelected(true);
        }else{
            rdFemenino.setSelected(true);
        }
        tfPeso.setText(String.valueOf(paciente.getPeso()));
        tfEstatura.setText(String.valueOf(paciente.getEstatura()));
        tfTallaInicial.setText(String.valueOf(paciente.getTallaInicial()));
        tfCorreo.setDisable(true);
        tfTelefono.setText(paciente.getTelefono());
        pfContraseña.setText(paciente.getContraseña());
        obtenerImagenServicio();
        
    }
}
     
    

     
     @FXML
    private void btnGuardar(ActionEvent event) {
        Paciente pacienteEditado = new Paciente();
        pacienteEditado.setIdPaciente(idPaciente);
        pacienteEditado.setNombre(tfNombre.getText());
        pacienteEditado.setApellidoPaterno(tfApellidoPaterno.getText());
        pacienteEditado.setApellidoMaterno(tfApellidoMaterno.getText());
        pacienteEditado.setEmail(tfCorreo.getText());
        pacienteEditado.setTelefono(tfTelefono.getText());
        pacienteEditado.setEstatura(Float.parseFloat(tfEstatura.getText()));
        pacienteEditado.setPeso(Float.parseFloat(tfPeso.getText()));
        pacienteEditado.setTallaInicial(Integer.parseInt(tfTallaInicial.getText()));
        pacienteEditado.setContraseña(pfContraseña.getText());
         pacienteEditado.setFechaNacimiento(tfFechaNacimiento.getValue().toString());
         
       
        pacienteEditado.setSexo(sexo);
        pacienteEditado.setIdMedico(idMedico);
        System.out.println(idMedico);
          System.out.println(idPaciente);

        if (idPaciente == 0) {
            guardarPaciente(pacienteEditado);
        } else {
            editarPaciente(pacienteEditado);
        }
        
    }

    
    
    private void guardarPaciente(Paciente paciente){
        Mensaje msj = PacientesDAO.registrarPaciente(paciente);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Paciente registrado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardadoPaciente(paciente.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
         
            
        }
        
    }
    
  
    
      private void editarPaciente(Paciente paciente){
        Mensaje msj = PacientesDAO.editarPaciente(paciente);
        if(!msj.isError()){
            Utilidades.mostrarAlertaSimple("Paciente Editado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardadoPaciente(paciente.getNombre());
            cerrarVentana();
        }else{
            
            Utilidades.mostrarAlertaSimple("Error al Editar", msj.getMensaje(), Alert.AlertType.ERROR);
               System.out.println(msj.getMensaje());
        }
        
    }
    
    private void cerrarVentana(){
        Stage scenario =(Stage) tfNombre.getScene().getWindow();
        scenario.close();
    }
    
     @FXML
    private void btSeleccionarFoto(ActionEvent event) {
          FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de iamgen (.png, *.jpg)", "*.png");
        dialogoSeleccionImg.getExtensionFilters().add(filtroImg);
        Stage stageActual = (Stage) tfNombre.getScene().getWindow();
        imagenSeleccionada = dialogoSeleccionImg.showOpenDialog(stageActual);
        if(imagenSeleccionada != null){
            mostrarFotografia(imagenSeleccionada);
        }
    }
    
    @FXML
    private void btActualizarFoto(ActionEvent event) {
        if (imagenSeleccionada != null) {
                cargarFotografiaServidor(imagenSeleccionada);
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona la fotografía", "Para subir la fotografía del paciente debes seleccionar una previamente", Alert.AlertType.WARNING);
        }
    }

    private void cargarFotografiaServidor(File imagen) {
    try {
        byte[] imgBytes = Files.readAllBytes(imagen.toPath());
        if (paciente != null) {
            Mensaje msj = PacientesDAO.subirFotografiaPaciente(paciente.getIdPaciente(), imgBytes);
            if (!msj.isError()) {
                Utilidades.mostrarAlertaSimple("Fotografía guardada exitosamente", msj.getMensaje(), Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al guardar la fotografía", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", "El paciente es nulo", Alert.AlertType.ERROR);
        }
    } catch (IOException e) {
        Utilidades.mostrarAlertaSimple("Error", e.getMessage(), Alert.AlertType.ERROR);
    }
}

   
    
    private void mostrarFotografia(File img){
        try{
            BufferedImage buffer = ImageIO.read(img);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivFoto.setImage(image);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void obtenerImagenServicio(){
          Paciente pacienteFoto = PacientesDAO.obtenerFotografiaPaciente(paciente.getIdPaciente());
        if(pacienteFoto != null && pacienteFoto.getFotografiaBase64() != null && !pacienteFoto.getFotografiaBase64().isEmpty()){
            mostrarFotografiaServidor(pacienteFoto.getFotografiaBase64());
        }
    }
    
    private void mostrarFotografiaServidor(String imgBase64){
        byte[] foto =Base64.getDecoder().decode(imgBase64.replaceAll("\\n",""));
        Image image = new Image(new ByteArrayInputStream(foto));
        ivFoto.setImage(image);
        
    }
    
}
