/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import transferObjects.ApunteBean;

/**
 *
 * @author Usuario
 */
public class CellTiendaApunte extends ListCell<ApunteBean>{
    private VBox vbox = new VBox();
    private HBox hbox = new HBox();
    private Label labelTitulo = new Label("");
    private Text textDesc = new Text("");
    private Label labelPrecio = new Label("");
    private Label labelMateria = new Label("");
    private Label labelCreador = new Label("");
    
    public CellTiendaApunte() {
        super();
        labelTitulo.setPadding(new Insets(0,0, 5, 0));
        labelPrecio.setPadding(new Insets(5,0, 0, 0));
        labelMateria.setPadding(new Insets(5,0, 0, 25));
        labelCreador.setPadding(new Insets(5,0, 0, 25));
        hbox.getChildren().addAll(labelPrecio,labelMateria,labelCreador);
        vbox.getChildren().addAll(labelTitulo,textDesc,hbox);
        
    }
    public void updateItem(ApunteBean apunte,boolean vacio){
        super.updateItem(apunte, vacio);
        setText(null);
        setGraphic(null);
        if(apunte!=null && !vacio){
            labelTitulo.setText(apunte.getTitulo());
            labelPrecio.setText(apunte.getPrecio()+"€");
            textDesc.setText(apunte.getDescripcion());
            labelMateria.setText("Materia:  "+apunte.getMateria().getTitulo());
            labelCreador.setText("Creador: "+apunte.getCreador().getNombreCompleto()+"       Likes: "+apunte.getLikeCont()+"      Dislike: "+apunte.getDislikeCont());
            setGraphic(vbox);
        }
    }
    
}
