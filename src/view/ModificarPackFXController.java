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
import transferObjects.PackBean;

/**
 *
 * @author Luis
 */
public class ModificarPackFXController {
    GestorDePacksFXController fxPack = null;
    private Stage stage;
    private PackBean pack;
    private int opcion;
    
    @FXML
    public Button btnCrearCrearPack;
    @FXML
    private Button btnSalirCrearPack;
    @FXML
    private TextField tfTituloCrearPack;
    @FXML
    private TextArea taDescripcionCrearPack;
    
    @FXML
    public void initStage(Parent root) {
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Crear Pack");
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.showAndWait();
        }catch(Exception e){
            ControladorGeneral.showErrorAlert("Ha ocurrido un error.");
        }
    }
    
    public void setPack(PackBean pack){
        this.pack = pack;
    }
    
    public void setOpcion(int opc){
        this.opcion = opc;
    }
    
    public void setFXPack(GestorDePacksFXController fxController){
        this.fxPack = fxController;
    }
    
    @FXML
    public void onActionBtnCrearCrearPack(ActionEvent event){
        if(!tfTituloCrearPack.getText().isEmpty() || !taDescripcionCrearPack.getText().isEmpty()){
            pack.setTitulo(tfTituloCrearPack.getText());
            pack.setDescripcion(taDescripcionCrearPack.getText());
            fxPack.setOpc(1);
            stage.hide();
        }else{
            ControladorGeneral.showErrorAlert("Debes rellenar todos los campos.");
        }
    }
    
    @FXML
    public void onActionBtnSalirCrearPack(ActionEvent event){
        fxPack.setOpc(0);
        stage.hide();
    }
}
