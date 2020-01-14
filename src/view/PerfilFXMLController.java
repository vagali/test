/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import businessLogic.BusinessLogic;
import businessLogic.ClienteManager;
import static businessLogic.ClienteManagerFactory.createClienteManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import transferObjects.ClienteBean;
import static view.ControladorGeneral.showErrorAlert;

/**
 *
 * @author sergio
 */
public class PerfilFXMLController{
    private static final Logger LOGGER = Logger.getLogger("escritorio.view.Perfil");
    private ClienteManager logicCliente = createClienteManager("real");
    private Stage stage;
    private ClienteBean user;
    private final String CANCELAR_CONTRASENIA = "Cancelar modificacion de la contraseña";
    private final String CAMBIAR_CONTRASENIA = "Cambiar contraseña";
    private byte[] bytes;// = user.getFoto();
    @FXML private Circle imgCircle;
    @FXML private ImageView imagen;
    @FXML private Label lblCambiarContrasenia;
    @FXML private TextField pswContrasenia;
    @FXML private TextField pswConfirmarContrasenia;
    @FXML private Button btnAceptarContrasenia;
    @FXML private Button btnModificarPerfil;
    @FXML private Button btnAceptarPerfil;
    @FXML private Button btnIngresarSaldo;
    @FXML private Label lblCantidadSaldo;
    @FXML private Button btnCancelarPerfil;
    @FXML private Button btnModificarFotoPerfil;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private AnchorPane paneCentro;
    private int toques = 0;
    public void setUser(ClienteBean user){
        this.user = user;
    }
    public void initStage(Parent root){
        //LOGGER.info(user);
        
        //Crear scena y stage
        Scene scene = new Scene(root);
        stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        //Enviar scena al stage.
        stage.setScene(scene);
        if(user.getFoto()!=null)
            imagen.setImage(new Image(new ByteArrayInputStream(user.getFoto())));
        Ellipse ceiling = new Ellipse();
        //ceiling.centerXProperty().bind(imagen.fitHeightProperty());
        //ceiling.centerYProperty().bind(imagen.fitHeightProperty());
        ceiling.radiusYProperty().bind(imagen.fitHeightProperty());
        ceiling.radiusXProperty().bind(imagen.fitHeightProperty());
        ceiling.radiusYProperty().bind(imagen.fitHeightProperty());
        
        //eventos
        
        txtNombre.setText(user.getNombreCompleto());
        txtEmail.setText(user.getEmail());
        lblCantidadSaldo.setText(String.valueOf(user.getSaldo()));
        imagen.setClip(ceiling);
        stage.show();
    }
    
    @FXML public void cambiarContrasenia(MouseEvent m){
        LOGGER.info("Voy a cmabiar contraseia");
        pswContrasenia.setVisible(true);
        pswConfirmarContrasenia.setVisible(true);
        btnAceptarContrasenia.setVisible(true);
        lblCambiarContrasenia.setText(CANCELAR_CONTRASENIA);
        toques++;
        if(toques >1){
            pswContrasenia.setVisible(false);
            pswConfirmarContrasenia.setVisible(false);
            btnAceptarContrasenia.setVisible(false);
            toques=0;
            lblCambiarContrasenia.setText(CAMBIAR_CONTRASENIA);
        }
        
    }
    @FXML public void modificarFotoPerfil(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files(*.jpg;*.jpeg;*.png)","*.jpg","*.jpeg","*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File fileC = fileChooser.showOpenDialog(stage);
        try {
            bytes=Files.readAllBytes(fileC.toPath());
        } catch (IOException ex) {
            Logger.getLogger(PerfilFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(fileC==null)
            LOGGER.info("vacio");
        else{
            user.setFoto(bytes);
            LOGGER.log(Level.INFO, "------------------------------------------------file:///{0}", fileC.getPath());
            try {
                logicCliente.edit(user);
            } catch (BusinessLogic ex) {
                Logger.getLogger(PerfilFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imagen.setImage(new Image(new ByteArrayInputStream(user.getFoto())));
        }
    }
    @FXML public void modificarPerfil(ActionEvent e){
        LOGGER.info("cambiar perfil");
        txtNombre.setDisable(false);
        txtNombre.setText("");
        txtNombre.setPromptText(user.getNombreCompleto());
        txtApellido.setDisable(false);
        txtApellido.setPromptText(user.getLogin());
        txtApellido.setText("");
        txtEmail.setDisable(false);
        txtEmail.setText("");
        txtEmail.setPromptText(user.getEmail());
        btnAceptarPerfil.setVisible(true);
        btnCancelarPerfil.setVisible(true);
    }
    public void cancelarPerfil(){
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtEmail.setDisable(true);
        txtNombre.setText(user.getNombreCompleto());
        txtEmail.setText(user.getEmail());
        btnAceptarPerfil.setVisible(false);
        btnCancelarPerfil.setVisible(false);
    }
    public void aceptarPerfil(){
        try {
            LOGGER.info("He aceptado perfil");
             if(!txtNombre.getText().isEmpty() && !txtEmail.getText().isEmpty()){
                user.setEmail(txtEmail.getText());
                user.setNombreCompleto(txtNombre.getText());
                logicCliente.edit(user);
            }
            else if(!txtEmail.getText().isEmpty()){
                user.setEmail(txtEmail.getText());
                logicCliente.edit(user);
            }
            else if(!txtNombre.getText().isEmpty()){
                user.setNombreCompleto(txtNombre.getText());
                logicCliente.edit(user);
            }
             txtNombre.setDisable(true);
             txtApellido.setDisable(true);
             txtEmail.setDisable(true);
             txtNombre.setText(user.getNombreCompleto());
             txtEmail.setText(user.getEmail());
             btnAceptarPerfil.setVisible(false);
             btnCancelarPerfil.setVisible(false);
        } catch (BusinessLogic ex) {
            showErrorAlert("Ha ocurrido un error en el servidor, intentelo otra vez o vuelva mas tarde.");
        }
        
    }
    public void aceptarContrasenia(){
        if(!pswContrasenia.getText().isEmpty() && !pswConfirmarContrasenia.getText().isEmpty() && pswContrasenia.getText().equals(pswConfirmarContrasenia.getText())){
            /*user.setContrasenia(pswContrasenia.getText());
            try {
                logicCliente.actualizarContrasenia(user);
            } catch (BusinessLogic ex) {
                Logger.getLogger(PerfilFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            pswContrasenia.setVisible(false);
            pswConfirmarContrasenia.setVisible(false);
            btnAceptarContrasenia.setVisible(false);
            toques=0;
            lblCambiarContrasenia.setText(CAMBIAR_CONTRASENIA);
        }
    }
    @FXML private void ingresarSaldo(ActionEvent e) throws IOException, InterruptedException{
        LOGGER.info("Voy a ingresar saldo");
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("perfil_saldo.fxml"));
        
        Parent root_saldo = (Parent)loader.load();
        perfil_saldoFXMLController controller =
                ((perfil_saldoFXMLController)loader.getController());
        
        controller.initStage(root_saldo);
        controller.setUser(user);
        Stage stage_saldo=new Stage();
        stage_saldo.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root_saldo);
        stage_saldo.setTitle("Ventana de ayuda");
        stage_saldo.setResizable(false);
        stage_saldo.setScene(scene);
        controller.setStage(stage_saldo);
        stage_saldo.showAndWait();
        this.user=controller.getUser();
        lblCantidadSaldo.setText(String.valueOf(user.getSaldo()));
    }
    //----------------------------
    
    //Inicio de los metodos de navegación de la aplicación
    //Parte comun
    @FXML
    private void onActionCerrarSesion(ActionEvent event){
        try{
            //Creamos la alerta del tipo confirmación.
            Alert alertCerrarSesion = new Alert(Alert.AlertType.CONFIRMATION);
            //Ponemos titulo de la ventana como titulo para la alerta.
            alertCerrarSesion.setTitle("Cerrar sesión");
            alertCerrarSesion.setHeaderText("¿Quieres cerrar sesión?");
            //Si acepta se cerrara esta ventana.
            alertCerrarSesion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    LOGGER.info("Cerrando sesión.");
                    stage.hide();
                }
            });
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
    }
    @FXML
    private void onActionSalir(ActionEvent event){
        try{
            //Creamos la alerta con el tipo confirmación, con su texto y botones de
            //aceptar y cancelar.
            Alert alertCerrarAplicacion = new Alert(Alert.AlertType.CONFIRMATION,"Si sale de la aplicación cerrara\nautomáticamente la sesión.",ButtonType.NO,ButtonType.YES);
            //Añadimos titulo a la ventana como el alert.
            alertCerrarAplicacion.setTitle("Cerrar la aplicación");
            alertCerrarAplicacion.setHeaderText("¿Quieres salir de la aplicación?");
            //Si acepta cerrara la aplicación.
            alertCerrarAplicacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    LOGGER.info("Cerrando la aplicación.");
                    Platform.exit();
                }
            });
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
    }
    
    //Inicio de los metodos de navegación de la aplicación
    @FXML
    private void onActionAbrirMisApuntes(ActionEvent event){
        /* try{
        FXMLLoader loader = new FXMLLoader(getClass()
        .getResource("cliente_misApuntes.fxml"));
        Parent root = (Parent)loader.load();
        MisApuntesClienteFXController controller =
        ((MisApuntesClienteFXController)loader.getController());
        
        //controller.setUser(user);
        controller.setStage(stage);
        controller.initStage(root);
        stage.hide();
        }catch(Exception e){
        showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor."+e.getMessage());
        }*/
    }
    @FXML
    private void onActionAbrirTiendaApuntes(ActionEvent event){
        /*try{
        FXMLLoader loader = new FXMLLoader(getClass()
        .getResource("tienda_apuntes.fxml"));
        Parent root = (Parent)loader.load();
        TiedaApuntesFXController controller =
        ((TiedaApuntesFXController)loader.getController());
        
        //controller.setUser(user);
        controller.setStage(stage);
        controller.initStage(root);
        stage.hide();
        }catch(Exception e){
        showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor."+e.getMessage());
        }*/
    }
    @FXML
    private void onActionAbrirMiBiblioteca(ActionEvent event) throws IOException, BusinessLogic{
        /*    URL url = getClass()
        .getResource("biblioteca.fxml");
        LOGGER.info(url.toString());
        FXMLLoader loader = new FXMLLoader(getClass()
        .getResource("biblioteca.fxml"));
        
        Parent root = (Parent)loader.load();
        BibliotecaClienteFXMLController controller =
        ((BibliotecaClienteFXMLController)loader.getController());
        
        controller.setStage(stage);
        controller.initStage(root);*/
        //stage.hide();
    }
    @FXML
    private void onActionAbrirTiendaPacks(ActionEvent event){
    }
    @FXML
    private void onActionAbrirMiPerfil(ActionEvent event){
    }
    @FXML
    private void onActionAbout(ActionEvent event){
    }
    //Fin de los metodos de navegación de la aplicación
}

