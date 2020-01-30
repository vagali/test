/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import clientea4.ClienteA4;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

/**
 *
 * @author Sergio
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerfilFXMLControllerIT extends ApplicationTest{
    private Menu menuCuenta;
    
    @Override
    public void start(Stage stage) throws Exception{
        new ClienteA4().start(stage);
        
    }
    @Test
    public void testBien() {
        
        clickOn("#tfNombreUsuario");
        write("pildora");
        clickOn("#tfContra");
        write("abcd*1234");
        clickOn("#btnAcceder");
        
    }
    
}
