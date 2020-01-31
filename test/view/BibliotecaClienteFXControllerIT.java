/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import clientea4.ClienteA4;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
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
   private ImageView imgLike;
   private ImageView imgDislike;
   private ImageView imgDescarga;
   private ComboBox combo;
   public BibliotecaClienteFXControllerIT() {
    }
    @Override
    public void start(Stage stage) throws Exception{
        new ClienteA4().start(stage);
        table = lookup("#tablaBiblioteca").queryTableView();
        buscar = lookup("#txtDescripcion").query();
        imgLike = (ImageView) lookup("#imgLike").query();
        imgDislike = (ImageView) lookup("#imgDislike").query();
        imgDescarga = (ImageView) lookup("#imgDescarga").query();
        combo = lookup("#comboFiltroBiblioteca").queryComboBox();
    }
    @Test
    public void testA_Login() {
        clickOn("#tfNombreUsuario");
        write("test19993");
        clickOn("#tfContra");
        write("123");
        clickOn("#btnAcceder");
        
    }
    @Test
    public void testA_inicio() throws Exception {
        Assert.assertFalse(imgLike.isVisible());
        Assert.assertFalse(imgLike.isVisible());
        Assert.assertFalse(imgLike.isVisible());
        if(!buscar.getText().trim().equals("")){
            throw new Exception("No esta vacio");
        }
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
    @Test
    public void testG_descargarUnApuntePdfSuponiendoQueSiempreHabraUnoParaEsteTest() throws Exception{
        clickOn("#txtBuscar");
        write("apunte");
        doubleClickOn("#txtBuscar");
        push(KeyCode.CONTROL, KeyCode.X);
        clickOn(imgDescarga);
        push(KeyCode.CONTROL, KeyCode.V);
        press(KeyCode.ENTER); 
        Assert.assertFalse(imgLike.isVisible());
        Assert.assertFalse(imgLike.isVisible());
        Assert.assertFalse(imgLike.isVisible());
        if(!buscar.getText().trim().equals("")){
            throw new Exception("No esta vacio");
        }
    }
    @Test
    public void testH_busquedaPorCoincidencia() throws Exception{
        int rowCountSearch=0;
        int rowCount=table.getItems().size();
        clickOn("#txtBuscar");
        write("yyy");
        clickOn("#btnBuscar");
        rowCountSearch =table.getItems().size();
        if(rowCount == rowCountSearch)
             throw new Exception("No ha buscado.");
    }
    @Test
    public void testI_filtroPorMaterias()throws Exception{
        String materia;
        int rowCountSearch;
        int rowCount=table.getItems().size();
        doubleClickOn("#txtBuscar");
        press(KeyCode.DELETE);
        clickOn(combo);
        press(KeyCode.DOWN);
        press(KeyCode.ENTER);
        materia = (String) combo.getItems().get(1);
        rowCountSearch =table.getItems().size();
        if(rowCount == rowCountSearch)
             throw new Exception("No ha Filtrado.");
    }
}
