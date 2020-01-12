/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import businessLogic.ApunteManager;
import static businessLogic.ApunteManagerFactory.createApunteManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import transferObjects.ApunteBean;
import transferObjects.ClienteBean;

/**
 *
 * @author Usuario
 */
public class CompraApunteFXController {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("view.CompraApunteFXController");
    private ClienteBean cliente=null;
    private ApunteBean apunte=null;
    private ApunteManager apunteLogic = createApunteManager("real");
    private Stage stage;
    
    @FXML
    private Label labelTituloApunte;
    @FXML
    private Label labelSaldo;
    @FXML
    private Label labelPrecio;
    @FXML
    private Label labelResultado;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnComprarApunte;
    @FXML
    private Text textDescApunte;
    
    @FXML
    public void initStage(Parent root) {
        try{
            LOGGER.info("Iniciando la TiendaApuntesFXController");
            Scene scene=new Scene(root);
            stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Comprar Apunte");
            stage.setResizable(false);
            btnComprarApunte.setMnemonicParsing(true);
            btnComprarApunte.setText("C_omprar");
            btnCancelar.setMnemonicParsing(true);
            btnCancelar.setText("_Cancelar");
            if(cliente.getSaldo()>apunte.getPrecio())
                btnComprarApunte.setDisable(false);
            else
                btnComprarApunte.setDisable(true);
            this.labelPrecio.setText(apunte.getPrecio()+"€");
            this.labelSaldo.setText(cliente.getSaldo()+"€");
            float resultado=cliente.getSaldo()-apunte.getPrecio();
            this.labelResultado.setText(resultado+"€");
            this.labelTituloApunte.setText(apunte.getTitulo());
            this.textDescApunte.setText(apunte.getDescripcion());
            stage.show();
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
    }
    public void setDatos(ClienteBean cliente,ApunteBean apunte){
        this.apunte=apunte;
        this.cliente=cliente;
    }
    @FXML
    private void onActionCancelarCompra(ActionEvent event){
        stage.hide();
    }
    @FXML
    private void onActionComprarApunte(ActionEvent event){
        Alert alertCerrarSesion = new Alert(AlertType.CONFIRMATION);
        alertCerrarSesion.setTitle("Comprar apunte");
        alertCerrarSesion.setHeaderText("Una vez que compres el apunte "+apunte.getTitulo()+" no se podra devolver el producto");
        //Si acepta se cerrara esta ventana.
        alertCerrarSesion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
               // try {
                    //comprar
               // } catch (BusinessLogicException ex) {
               //     Logger.getLogger(GestorDeApuntesFXController.class.getName()).log(Level.SEVERE, null, ex);
               // }
                
            }
        });
    }
}
