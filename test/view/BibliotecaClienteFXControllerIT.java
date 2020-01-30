/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import clientea4.ClienteA4;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import transferObjects.ApunteBean;

/**
 *
 * @author Sergio
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BibliotecaClienteFXControllerIT extends ApplicationTest{
   private TableView<ApunteBean> table; 
   private Text buscar;
   private Button btnBuscar;
   public BibliotecaClienteFXControllerIT() {
    }
    @Override
    public void start(Stage stage) throws Exception{
        new ClienteA4().start(stage);
        table = lookup("#tablaBiblioteca").queryTableView();
        buscar = lookup("#txtDescripcion").query();
    }
    @Test
    public void testA_Login() {
        clickOn("#tfNombreUsuario");
        write("pildora");
        clickOn("#tfContra");
        write("abcd*1234");
        clickOn("#btnAcceder");
        
    }
    @Test
    public void testA_inicio() {
        verifyThat("#imgLike", isVisible());
        verifyThat("#imgDislike", isVisible());
        verifyThat("#imgDescarga",  isVisible());
        verifyThat("#txtDescripcion", hasText(""));
        
    }
    @Test
    public void testB_seleccionarFilaDeTablaSabiendoQuehayEnLaBBDDErgoSiempreTendraDatos()  throws Exception{
       Node row=lookup(".table-row-cell").nth(0).query();
       clickOn(row);
       assertNotNull("Row is null: table has not that row. ",row); 
       ApunteBean apunte = (ApunteBean) table.getSelectionModel()
                                     .getSelectedItem();
        if(!buscar.getText().trim().equals(apunte.getDescripcion().trim())){
            throw new Exception(buscar.getText().trim());
        }
        
    }
    @Test
    public void testC_votarLikeUnApunteQueNoSeHayaVotadoYSeleccionadoPreviamente(){
        clickOn("#imgLike");
        verifyThat("Se ha votado correctamente", isVisible());
        press(KeyCode.ENTER);
    }
    @Test
    public void testD_votarDislikeUnApunteQueNoSeHayaVotadoYSeleccionadoPreviamente(){
        clickOn("#imgDislike");
        verifyThat("Se ha votado correctamente", isVisible());
        press(KeyCode.ENTER);
    }
    @Test
    public void testE_votarLikeUnApunteQueYaSeHayaVotadoYSeleccionadoPreviamente(){
        clickOn("#imgLike");
        verifyThat("No puedes volver a votar ya que ya has votado este apunte.", isVisible());
        press(KeyCode.ENTER);
    }
    @Test
    public void testF_votarDislikeUnApunteQueYaSeHayaVotadoYSeleccionadoPreviamente(){
        clickOn("#imgDislike");
        verifyThat("No puedes volver a votar ya que ya has votado este apunte.", isVisible());
        press(KeyCode.ENTER);   
    }
    
}
