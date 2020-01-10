/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import businessLogic.ApunteManager;
import static businessLogic.ApunteManagerFactory.createApunteManager;
import businessLogic.BusinessLogicException;
import businessLogic.MateriaManager;
import static businessLogic.MateriaManagerFactory.createMateriaManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import transferObjects.ApunteBean;
import transferObjects.ClienteBean;
import transferObjects.MateriaBean;
import transferObjects.UserBean;
import static view.ControladorGeneral.showErrorAlert;

/**
 *
 * @author Usuario
 */
public class GestorDeApuntesFXController {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("view.GestorDeApuntesFXController");
    private UserBean user;
    private Stage stage;
    
    private ApunteManager apunteLogic = createApunteManager("real");
    private MateriaManager materiaLogic = createMateriaManager("real");
    private Set<ApunteBean> apuntes=null;
    private ObservableList<ApunteBean> apuntesData=null;
    private ObservableList<MateriaBean> materiasData=null;
    private ApunteBean apunteProvisional;
    @FXML
    private TableView tableApuntes;
    @FXML
    private TableColumn clId;
    @FXML
    private TableColumn clTitulo;
    @FXML
    private TableColumn clDesc;
    @FXML
    private TableColumn clFechaValidacion;
    @FXML
    private TableColumn clLikes;
    @FXML
    private TableColumn clPrecio;
    @FXML
    private TableColumn clCreador;
    @FXML
    private TableColumn clDislike;
    @FXML
    private TableColumn clMateria;
    @FXML
    private Button btnRefrescar;
    @FXML
    private Button btnCargarDatos;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnInforme;
    @FXML
    private TextField textFieldTitulo;
    @FXML
    private TextArea textFieldDesc;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private ComboBox comboBoxMaterias;
    @FXML
    private Label labelTitulo;
    @FXML
    private Label labelDesc;
    @FXML
    private Label labelFecha;
    @FXML
    private Label labelMateria;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuCuenta;
    @FXML
    private MenuItem menuCuentaCerrarSesion;
    @FXML
    private MenuItem menuCuentaSalir;
    @FXML
    private Menu menuVentanas;
    @FXML
    private MenuItem menuVentanasGestorApuntes;
    @FXML
    private MenuItem menuVentanasGestorPacks;
    @FXML
    private MenuItem menuVentanasGestorOfertas;
    @FXML
    private MenuItem menuVentanasGestorMaterias;
    @FXML
    private Menu menuHelp;
    @FXML
    private MenuItem menuHelpAbout;
    @FXML
    public void initStage(Parent root) {
        try{
            LOGGER.info("Iniciando la ventana LogOut");
            Scene scene=new Scene(root);
            stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Tienda de apuntes");
            stage.setResizable(true);
            stage.setMaximized(true);
            //Vamos a rellenar los datos en la ventana.
            stage.setOnShowing(this::handleWindowShowing);
            
            //Mnemonicos
            //Menu->
            menuCuenta.setMnemonicParsing(true);
            menuCuenta.setText("_Cuenta");
            menuVentanas.setMnemonicParsing(true);
            menuVentanas.setText("_Ventanas");
            menuHelp.setMnemonicParsing(true);
            menuHelp.setText("_Help");
            //<-Menu
            
            //CARGAR DATOS
            
            
            clId.setCellValueFactory(new PropertyValueFactory<>("idApunte"));
            clTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            clDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            clFechaValidacion.setCellValueFactory(new PropertyValueFactory<>("fechaValidacion"));
            clLikes.setCellValueFactory(new PropertyValueFactory<>("likeCont"));
            clPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            clCreador.setCellValueFactory(new PropertyValueFactory<ClienteBean,String>("creador"));
            clDislike.setCellValueFactory(new PropertyValueFactory<>("dislikeCont"));
            clMateria.setCellValueFactory(new PropertyValueFactory<MateriaBean,String>("materia"));
            cargarApuntes();
            cargarMaterias();
            tableApuntes.getSelectionModel().selectedItemProperty().addListener(this::handleApuntesTableSelectionChanged);
            this.btnModificar.setDisable(true);
            this.btnBorrar.setDisable(true);
            stage.show();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
        
    }
    private void handleWindowShowing(WindowEvent event){
        try{
            LOGGER.info("handlWindowShowing --> LogOut");
            
            
            
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
    }
    public void setUser(UserBean user){
        this.user=user;
    }
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
    private void onActionBorrar(ActionEvent event){
        if(this.apunteProvisional!=null){
            try{
                boolean sePuedeBorrar=true;
                int cuantasCompras=this.apunteLogic.cuantasCompras(this.apunteProvisional.getIdApunte());
                if(cuantasCompras==0){
                    try{
                        //Creamos la alerta del tipo confirmación.
                        Alert alertCerrarSesion = new Alert(AlertType.CONFIRMATION);
                        //Ponemos titulo de la ventana como titulo para la alerta.
                        alertCerrarSesion.setTitle("Borrar apunte");
                        alertCerrarSesion.setHeaderText("¿Estas seguro de borrar el apunte "+this.apunteProvisional.getTitulo()+"?");
                        //Si acepta se cerrara esta ventana.
                        alertCerrarSesion.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                try {
                                    apunteLogic.remove(apunteProvisional.getIdApunte());Alert alert=new Alert(Alert.AlertType.INFORMATION,
                                            "El apunte "+this.apunteProvisional.getTitulo()+" fue eliminado",
                                            ButtonType.OK);
                                    alert.showAndWait();
                                    this.tableApuntes.getItems().remove(this.tableApuntes.getSelectionModel().getSelectedItem());
                                    this.tableApuntes.refresh();
                                    vaciar();
                                } catch (BusinessLogicException ex) {
                                    Logger.getLogger(GestorDeApuntesFXController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }
                        });
                    }catch(Exception e){
                        LOGGER.severe(e.getMessage());
                    }
                }else{
                    showErrorAlert("Lo sentimos, no se puede borrar el apunte, ya que tiene una o más de una venta.");
                }
            }catch(BusinessLogicException ex){
                Logger.getLogger(GestorDeApuntesFXController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            showErrorAlert("Para borrar un apunte seleccione en la tabla el apunte correspondient porfavor.");
        }
    }
    @FXML
    private void onActionRefrescar(ActionEvent event){
        cargarApuntes();
        cargarMaterias();
        vaciar();
    }
    @FXML
    private void onActionModificar(ActionEvent event){
        if(this.apunteProvisional!=null){
            boolean todoBien = true;
            if(!esValido(this.textFieldTitulo.getText().trim(),3,250)){
                this.labelTitulo.setText("Titulo (Min 3 car. | Max 250 car.)");
                this.labelTitulo.setTextFill(Color.web("red"));
                todoBien=false;
            }else{
                this.labelTitulo.setText("Titulo");
                this.labelTitulo.setTextFill(Color.web("black"));
            }
            if(!esValido(this.textFieldDesc.getText().trim(),3,250)){
                this.labelDesc.setText("Descripción (Min 3 car. | Max 250 car.)");
                this.labelDesc.setTextFill(Color.web("red"));
                todoBien=false;
            }else{
                this.labelDesc.setText("Descripción");
                this.labelDesc.setTextFill(Color.web("black"));
            }
            if(this.datePickerFecha.getValue()==null){
                this.labelFecha.setTextFill(Color.web("red"));
                todoBien=false;
            }else{
                this.labelFecha.setTextFill(Color.web("black"));
            }
            if(this.comboBoxMaterias.getSelectionModel().getSelectedItem()==null){
                this.labelMateria.setTextFill(Color.web("red"));
                todoBien=false;
            }else{
                this.labelMateria.setTextFill(Color.web("black"));
            }
            if(todoBien){
                apunteProvisional.setTitulo(this.textFieldTitulo.getText().trim());
                apunteProvisional.setDescripcion(this.textFieldDesc.getText().trim());//Poner maximo!!!!!! y Minimo!!!!!
                apunteProvisional.setFechaValidacion(localDateToDate(this.datePickerFecha.getValue()));
                apunteProvisional.setMateria((MateriaBean) this.comboBoxMaterias.getSelectionModel().getSelectedItem());
                showErrorAlert("Titulo: "+apunteProvisional.getTitulo()+"\n Descripción"+apunteProvisional.getDescripcion()+"\n Fecha "+apunteProvisional.getFechaValidacion().toString()+"\n Materia: "+apunteProvisional.getMateria().getTitulo());
                
                try {
                    apunteLogic.edit(apunteProvisional);
                    apuntesData.set(this.tableApuntes.getSelectionModel().getFocusedIndex(), apunteProvisional);
                    this.tableApuntes.getSelectionModel().clearSelection();
                    this.tableApuntes.refresh();
                    vaciar();
                    
                } catch (BusinessLogicException ex) {
                    LOGGER.severe("Error enviar la modificación del apunte: "+ex.getMessage());
                }
            }
        }else{
            showErrorAlert("Para modificar primero seleccione un apunte.");
        }
    }
    
    @FXML
    private void onActionAbout(ActionEvent event){
    }
    //Fin de los metodos de navegación de la aplicación
    @FXML
    private void onActionAbrirGestorApuntes(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("gestor_de_apuntes.fxml"));
            Parent root = (Parent)loader.load();
            GestorDeApuntesFXController controller =
                    ((GestorDeApuntesFXController)loader.getController());
            
            controller.setUser(user);
            controller.setStage(stage);
            controller.initStage(root);
            stage.hide();
        }catch(Exception e){
            showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor."+e.getMessage());
        }
    }
    @FXML
    private void onActionAbrirGestorPacks(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("gestor_de_packs.fxml"));
            Parent root = (Parent)loader.load();
            GestorDePacksFXController controller =
                    ((GestorDePacksFXController)loader.getController());
            
            controller.setUser(user);
            controller.setStage(stage);
            controller.initStage(root);
            stage.hide();
        }catch(Exception e){
            showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor."+e.getMessage());
        }
    }
    @FXML
    private void onActionAbrirGestorOfertas(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("gestor_de_ofertas.fxml"));
            Parent root = (Parent)loader.load();
            GestorDeOfertasFXController controller =
                    ((GestorDeOfertasFXController)loader.getController());
            
            controller.setUser(user);
            controller.setStage(stage);
            controller.initStage(root);
            stage.hide();
        }catch(Exception e){
            showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor."+e.getMessage());
        }
    }
    @FXML
    private void onActionAbrirGesstorMaterias(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("gestor_de_materias.fxml"));
            Parent root = (Parent)loader.load();
            GestorDeMateriasFXController controller =
                    ((GestorDeMateriasFXController)loader.getController());
            
            controller.setUser(user);
            controller.setStage(stage);
            controller.initStage(root);
            stage.hide();
        }catch(Exception e){
            showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor.");
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    private void cargarApuntes() {
        try {
            apuntes=apunteLogic.findAll();
            //ApunteBean apunte=apunteLogic.find(4);
            //MateriaRESTClient mrc=new MateriaRESTClient();
            //MateriaBean materia=mrc.find(MateriaBean.class, "2");
            
            apuntesData=FXCollections.observableArrayList(new ArrayList<>(apuntes));
            tableApuntes.setItems(apuntesData);
            ArrayList <ApunteBean> apuntesInfo=new ArrayList<>(apuntes);
            LOGGER.info("INf. "+apuntesInfo.get(0).getCreador().getNombreCompleto());
        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error al intentar cargar los apuntes :"+ex.getMessage());
            showErrorAlert("No se ha podido cargar los apuntes");
        }
        
    }
    private void handleApuntesTableSelectionChanged(ObservableValue obvservable, Object oldValue, Object newValue){
        if(newValue!=null && newValue!=oldValue){
            apunteProvisional =(ApunteBean) newValue;
            this.textFieldTitulo.setText(apunteProvisional.getTitulo());
            this.textFieldDesc.setText(apunteProvisional.getDescripcion());
            this.datePickerFecha.setValue(dateToLocalDate(apunteProvisional.getFechaValidacion()));
            this.comboBoxMaterias.getSelectionModel().select(apunteProvisional.getMateria());
            this.btnModificar.setDisable(false);
            this.btnBorrar.setDisable(false);
        }else{
            vaciar();
            
        }
    }
    
    //Metodos utiles de la clase
    public LocalDate dateToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public Date localDateToDate(LocalDate date) {
        return java.util.Date.from(date.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    public boolean esValido(String frase,int minimo, int maximo){
        boolean resultado=true;
        if(frase.length()>maximo || frase.length()<minimo)
            resultado=false;
        return resultado;
    }
    public void vaciar(){
        this.textFieldTitulo.setText("");
        this.textFieldDesc.setText("");
        this.datePickerFecha.setValue(null);
        this.apunteProvisional=null;
        this.comboBoxMaterias.getSelectionModel().select(null);
        this.btnModificar.setDisable(true);
        this.btnBorrar.setDisable(true);
    }
    
    private void cargarMaterias() {
        try {
            Set<MateriaBean> materias = materiaLogic.findAllMateria();
            materiasData=FXCollections.observableArrayList(new ArrayList<>(materias));
            this.comboBoxMaterias.setItems(materiasData);
        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error al cargar las materias: "+ex.getMessage());
        }
    }
}
