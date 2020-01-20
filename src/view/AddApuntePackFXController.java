/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Luis
 */
public class AddApuntePackFXController {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("view.AddApuntePackFXController");
    private Stage stage;
    private ModificarPackFXController fxModificarPack = null;
    
    @FXML
    private Button btnBuscarAddApunte;
    @FXML
    private TextField tfFiltarAddApunte;
    @FXML
    private TableView tvApuntesAddApunte;
    @FXML
    private TableColumn cIncluido;
    @FXML
    private TableColumn cId;
    @FXML
    private TableColumn cTitulo;
    @FXML
    private TableColumn cDescripcion;
    @FXML
    private TableColumn cMateria;
    @FXML
    private Button btnModificarAddApunte;
    
    public void setFXModificarPack(ModificarPackFXController fxController){
        this.fxModificarPack = fxController;
    }
    
    @FXML
    public void initStage(Parent root) {
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Modificar Pack");
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.setOnShowing(this::handleWindowShowing);
            //Tabla
            /*cId.setCellValueFactory(new PropertyValueFactory<>("idMateria"));
            cTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            cDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            //cargarDatos();
            tablaMateria.getSelectionModel().selectedItemProperty().addListener(this::MateriaClicked);*/
            stage.showAndWait();
        }catch(Exception e){
            ControladorGeneral.showErrorAlert("Ha ocurrido un error.");
        }
    }
    
    private void handleWindowShowing(WindowEvent event){
        try{
            LOGGER.info("handlWindowShowing --> Gestor de Pack MODIFICAR Añadir apunte");
            tfFiltarAddApunte.requestFocus();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
    }
}
