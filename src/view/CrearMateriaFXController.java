package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import transferObjects.MateriaBean;

/**
 *
 * @author Luis
 */
public class CrearMateriaFXController {
    private Stage stage;
    private MateriaBean materia;
    private int opcion;
    
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
            stage.showAndWait();
        }catch(Exception e){
            ControladorGeneral.showErrorAlert("Ha ocurrido un error.");
        }
    }
    
    public void setMateria(MateriaBean materia){
        this.materia = materia;
    }
    
    public void setOpcion(int opc){
        this.opcion = opc;
    }
    
    @FXML
    public void onActionBtnCrearCrearMateria(ActionEvent event){
        if(!tfTituloCrearMateria.getText().isEmpty() || !taDescripcionCrearMateria.getText().isEmpty()){
            materia.setTitulo(tfTituloCrearMateria.getText());
            materia.setDescripcion(taDescripcionCrearMateria.getText());
            opcion = 1;
            stage.hide();
        }else{
            ControladorGeneral.showErrorAlert("Debes rellenar todos los campos.");
        }
    }
    
    @FXML
    public void onActionBtnSalirCrearMateria(ActionEvent event){
        opcion = 0;
        stage.hide();
    }
}
