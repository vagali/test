package view;

import businessLogic.BusinessLogicException;
import businessLogic.MateriaManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import transferObjects.MateriaBean;

/**
 *
 * @author 2dam
 */
public class CrearMateriaFXController {
    
    private Stage stage;
    private MateriaBean materia;
    private MateriaManager manager;
    
    @FXML
    public Button btnCrearCrearMateria;
    @FXML
    private Button btnSalirCrearMateria;
    @FXML
    private TextField tfTituloCrearMateria;
    @FXML
    private TextArea taDescripcionCrearMateria;
    
    @FXML
    public void initStage(Parent root) {
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Crear Materia");
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    public void onActionBtnCrearCrearMateria(ActionEvent event){
        if(!tfTituloCrearMateria.getText().isEmpty() || !taDescripcionCrearMateria.getText().isEmpty()){
            try{
                materia.setTitulo(tfTituloCrearMateria.getText());
                materia.setDescripcion(taDescripcionCrearMateria.getText());
                manager.createMateria(materia);
                stage.hide();
            }catch(BusinessLogicException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            ControladorGeneral.showErrorAlert("Debes rellenar todos los campos.");
        }
    }
    
    @FXML
    public void onActionBtnSalirCrearMateria(ActionEvent event){
        stage.hide();
    }
    
    public void setMateria(MateriaBean materia){
        this.materia = materia;
    }
    
    public void setManager(MateriaManager manager){
        this.manager = manager;
    }
}
