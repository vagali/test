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
public class ModificarMateriaFXController {
    private Stage stage;
    private MateriaBean materia;
    private int opcion;
    
    @FXML
    public Button btnModificarModificarMateria;
    @FXML
    private Button btnSalirModificarMateria;
    @FXML
    private TextField tfTituloModificarMateria;
    @FXML
    private TextArea taDescripcionModificarMateria;
    @FXML
    private Button btnEliminarModificarMateria;
    
    @FXML
    public void initStage(Parent root) {
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Modificar Materia");
            stage.setResizable(false);
            stage.setMaximized(false);
            tfTituloModificarMateria.setText(materia.getTitulo());
            taDescripcionModificarMateria.setText(materia.getDescripcion());
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
    public void onActionBtnModificarModificarMateria(ActionEvent event){
        if(!tfTituloModificarMateria.getText().isEmpty() || !taDescripcionModificarMateria.getText().isEmpty()){
            materia.setTitulo(tfTituloModificarMateria.getText());
            materia.setDescripcion(taDescripcionModificarMateria.getText());
            opcion = 2;
            stage.hide();
        }else{
            ControladorGeneral.showErrorAlert("Debes rellenar todos los campos.");
        }
    }
    
    @FXML
    public void onActionBtnEliminarModificarMateria(ActionEvent event){
        opcion = 1;
        stage.hide();
    }
    
    @FXML
    public void onActionBtnSalirModificarMateria(ActionEvent event){
        opcion = 0;
        stage.hide();
    }
}
