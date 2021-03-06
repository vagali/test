package view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import transferObjects.PackBean;

/**
 *
 * @author Luis
 */
public class ModificarPackFXController {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("view.ModificarPackFXController");
    GestorDePacksFXController fxPack = null;
    private Stage stage;
    private PackBean pack;
    private int opcion;
    
    @FXML
    public Button btnModificarModificarPack;
    @FXML
    private Button btnSalirModificarPack;
    @FXML
    private TextField tfTituloModificarPack;
    @FXML
    private TextArea taDescripcionModificarPack;
    @FXML
    private Button btnEliminarModificarPack;
    @FXML
    private Button btnModificarApunteModificarPack;
    @FXML
    private DatePicker dpModificarPack;
    
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
            tfTituloModificarPack.setText(pack.getTitulo());
            taDescripcionModificarPack.setText(pack.getDescripcion());
            dpModificarPack.setValue(dateToLocalDate(pack.getFechaModificacion()));
            stage.showAndWait();
        }catch(Exception e){
            ControladorGeneral.showErrorAlert("Ha ocurrido un error.");
        }
    }
    
    private void handleWindowShowing(WindowEvent event){
        try{
            LOGGER.info("handlWindowShowing --> Gestor de Pack MODIFICAR");
            tfTituloModificarPack.requestFocus();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
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
    public void onActionBtnModificarModificarPack(ActionEvent event){
        if(!tfTituloModificarPack.getText().isEmpty() && !taDescripcionModificarPack.getText().isEmpty()){
            pack.setTitulo(tfTituloModificarPack.getText());
            pack.setDescripcion(taDescripcionModificarPack.getText());
            pack.setFechaModificacion(localDateToDate(dpModificarPack.getValue()));
            fxPack.setOpc(2);
            stage.hide();
        }else{
            ControladorGeneral.showErrorAlert("Debes rellenar todos los campos.");
        }
    }
    
    @FXML
    public void onActionBtnSalirModificarPack(ActionEvent event){
        fxPack.setOpc(0);
        stage.hide();
    }
    @FXML
    public void onActionBtnEliminarModificarPack(ActionEvent event){
        //PEDIR CONFIRMACIÓN
        fxPack.setOpc(1);
        stage.hide();
    }
    @FXML
    public void onActionBtnModificarApuntesModificarPack(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("add_apunte_pack.fxml"));
            Parent root = (Parent)loader.load();
            AddApuntePackFXController controller =
                ((AddApuntePackFXController)loader.getController());
            controller.setFXModificarPack(this);
            controller.initStage(root);
        }catch(Exception e){
            e.printStackTrace();
            ControladorGeneral.showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor. "+e.getMessage());
        }
    }
    
    public Date localDateToDate(LocalDate date) {
        return java.util.Date.from(date.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    
    public LocalDate dateToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
