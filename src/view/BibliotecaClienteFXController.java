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
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import transferObjects.ApunteBean;
import transferObjects.ClienteBean;
import transferObjects.MateriaBean;

/**
 *
 * @author Sergio
 */
public class BibliotecaClienteFXController{
    private static final Logger LOGGER = Logger.getLogger("escritorio.view.MisPuntesClienteFXController");
    private int click=0;
    private ClienteBean user;
    private final ApunteManager apuntesLogic = createApunteManager("real");
    private final MateriaManager materiaLogic = createMateriaManager("real");
    private Stage stage;
    @FXML private TableView<ApunteBean> tablaBiblioteca;
    @FXML private TableColumn columnaTitulo;
    @FXML private TableColumn columnaMateria;
    @FXML private TableColumn columnaAutor;
    @FXML private TableColumn columnaDescripcion;
    @FXML private TextField txtBuscar;
    @FXML private ComboBox comboFiltroBiblioteca;
    private ObservableList<ApunteBean>  apuntes;
    private ObservableList<MateriaBean>  materias;
    private ObservableList<String>  materias_titulo = FXCollections.observableArrayList();
    public void setStage(Stage stage){
        this.stage= stage;
    }
    public void setUser(ClienteBean user){
        this.user=user;
    }
    public void initStage(Parent root) throws BusinessLogicException{
        Scene scene = new Scene(root);
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("tablaBiblioteca");
        
        //--
        comboFiltroBiblioteca.getSelectionModel().selectedItemProperty().addListener(this::clicks);
        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnaMateria.setCellValueFactory(new PropertyValueFactory<>("materia"));
        columnaAutor.setCellValueFactory(new PropertyValueFactory<>("creador"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        LOGGER.info(user.getId().toString());
        apuntes = FXCollections.observableArrayList(apuntesLogic.getApuntesByComprador(user.getId()));
        materias = FXCollections.observableArrayList(materiaLogic.findAllMateria());
        materias_titulo.add("");
        tablaBiblioteca.setItems(apuntes);
        materias.forEach((materia) -> {
            LOGGER.info(materia.getTitulo());
            materias_titulo.add(materia.getTitulo());
        });
        comboFiltroBiblioteca.setItems(materias_titulo);
        
        stage.show();
    }
    private void clicks(ObservableValue obvservable, Object oldValue, Object newValue){
        ObservableList<ApunteBean> arrayBusquedaPersona = FXCollections.observableArrayList();
        LOGGER.info(String.valueOf(comboFiltroBiblioteca.getSelectionModel().getSelectedIndex()));
        if(newValue !=null){
            apuntes.stream().filter((apunte) -> (apunte.getMateria().toString().toUpperCase().equals(materias_titulo.get(comboFiltroBiblioteca.getSelectionModel().getSelectedIndex()).toUpperCase()))).map((apunte) -> {
                arrayBusquedaPersona.add(apunte);
                return apunte;
            }).forEachOrdered((_item) -> {
                LOGGER.info("he ENCONTRADO");
            });
            if(arrayBusquedaPersona.size()>0){
                tablaBiblioteca.setItems(arrayBusquedaPersona);
                tablaBiblioteca.refresh();
            }
        }
        if(comboFiltroBiblioteca.getSelectionModel().getSelectedIndex()==0){
            LOGGER.info("he entrados");
            tablaBiblioteca.setItems(apuntes);
            tablaBiblioteca.refresh();
        }
        
        
        
    }
    @FXML
    private void buscar(ActionEvent event){
        LOGGER.info("He clicado buscar");
        String palabraBusqueda;
        ObservableList<ApunteBean> arrayBusquedaPersona = FXCollections.observableArrayList();
        if(txtBuscar.getText().isEmpty()){
            tablaBiblioteca.setItems(apuntes);
            tablaBiblioteca.refresh();
        }
        else{
            palabraBusqueda = txtBuscar.getText().toUpperCase();
            LOGGER.info(palabraBusqueda);
            apuntes.stream().filter((apunte) -> (apunte.getTitulo().toUpperCase().contains(palabraBusqueda) ||
                    apunte.getMateria().toString().toUpperCase().contains(palabraBusqueda)||
                    apunte.getCreador().toString().toUpperCase().contains(palabraBusqueda) ||
                    apunte.getDescripcion().toUpperCase().contains(palabraBusqueda))).map((apunte) -> {
                        arrayBusquedaPersona.add(apunte);
                        return apunte;
                    }).forEachOrdered((_item) -> {
                        LOGGER.info("he ENCONTRADO");
                    });
            if(arrayBusquedaPersona.size()>0){
                tablaBiblioteca.setItems(arrayBusquedaPersona);
                tablaBiblioteca.refresh();
            }
            else
                LOGGER.info("No se ha encontrado conicidencias");
            
        }
    }
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
        /*try{
        FXMLLoader loader = new FXMLLoader(getClass()
        .getResource("cliente_misApuntes.fxml"));
        Parent root = (Parent)loader.load();
        BibliotecaClienteFXController controller =
        ((BibliotecaClienteFXController)loader.getController());
        
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
        /*  try{
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
    private void onActionAbrirMiBiblioteca(ActionEvent event){
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
