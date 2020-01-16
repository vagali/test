/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import businessLogic.ApunteManager;
import static businessLogic.ApunteManagerFactory.createApunteManager;
import businessLogic.BusinessLogicException;
import businessLogic.ClienteManager;
import static businessLogic.ClienteManagerFactory.createClienteManager;
import businessLogic.MateriaManager;
import static businessLogic.MateriaManagerFactory.createMateriaManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import transferObjects.ApunteBean;
import transferObjects.ClienteBean;
import transferObjects.MateriaBean;

/**
 *
 * @author Sergio
 */
public class BibliotecaClienteFXController{
    private static final Logger LOGGER = Logger.getLogger("escritorio.view.MisPuntesClienteFXController");
    public ClienteManager clienteLogic = createClienteManager("real");
    private ClienteBean user;
    private final ApunteManager apuntesLogic = createApunteManager("real");
    private final MateriaManager materiaLogic = createMateriaManager("real");
    private Stage stage;
    @FXML private TableView<ApunteBean> tablaBiblioteca;
    @FXML private TableColumn columnaTitulo;
    @FXML private TableColumn columnaMateria;
    @FXML private TableColumn columnaAutor;
    @FXML private TableColumn columnaDescripcion;
    @FXML private TextField txtBuscar;
    @FXML private Text txtDescripcion;
    @FXML private ComboBox comboFiltroBiblioteca;
    @FXML private ImageView imgLike;
    @FXML private ImageView imgDislike;
    @FXML private ImageView imgDescarga;
    private Boolean heVotado = false;
    private ObservableList<ApunteBean>  apuntes;
    private ObservableList<MateriaBean>  materias;
    private ObservableList<String>  materias_titulo = FXCollections.observableArrayList();
    
    private final ContextMenu popupMenu = new ContextMenu();; //llamado tmb popup-menu, es decir lo declaramos
    private final MenuItem menu1 = new MenuItem("Añadir");
    private final Menu menu2 = new Menu("Borrar Fila");
    private final MenuItem submenu1 = new MenuItem("Fila seleccionada");
    private final MenuItem submenu2 = new MenuItem("todas las filas");
    private final MenuItem menu3 = new MenuItem("Modificar");
    private Parent root;
    public void setStage(Stage stage){
        this.stage= stage;
    }
    public void setUser(ClienteBean user){
        this.user=user;
    }
    
    public void initStage(Parent root) throws BusinessLogicException{
        Scene scene = new Scene(root);
        this.root = root;
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        // stage.setResizable(false);
        stage.setTitle("tablaBiblioteca");
        //popup-menu items definiendolas y añadiendolas al popup
        popupMenu.getItems().add(menu1);
        popupMenu.getItems().add(menu2);
        menu2.getItems().add(submenu1);
        menu2.getItems().add(submenu2);
        popupMenu.getItems().add(new SeparatorMenuItem());//añadimos linea separadora entre los items
        popupMenu.getItems().add(menu3);
        //--
        comboFiltroBiblioteca.getSelectionModel().selectedItemProperty().addListener(this::comboControl);
        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnaMateria.setCellValueFactory(new PropertyValueFactory<>("materia"));
        columnaAutor.setCellValueFactory(new PropertyValueFactory<>("nombreCreador"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        LOGGER.info(user.getId().toString());
        apuntes = FXCollections.observableArrayList(apuntesLogic.getApuntesByComprador(user.getId()));
        apuntes.forEach((apunte) -> {
            apunte.setNombreCreador(apunte.getCreador().getNombreCompleto());
        });
        
        materias = FXCollections.observableArrayList(materiaLogic.findAllMateria());
        materias_titulo.add("");
        tablaBiblioteca.setItems(apuntes);
        materias.forEach((materia) -> {
            LOGGER.info(materia.getTitulo());
            materias_titulo.add(materia.getTitulo());
        });
        comboFiltroBiblioteca.setItems(materias_titulo);
        tablaBiblioteca.addEventHandler(MouseEvent.MOUSE_CLICKED, this::puntuacion);
        stage.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clicks);
        imgLike.addEventHandler(MouseEvent.MOUSE_CLICKED, this::puntuacion);
        imgDislike.addEventHandler(MouseEvent.MOUSE_CLICKED, this::puntuacion);
        imgDescarga.addEventHandler(MouseEvent.MOUSE_CLICKED, this::puntuacion);
        stage.show();
    }
    public void clicks(MouseEvent m){
        if(m.getButton()== MouseButton.SECONDARY){
            LOGGER.info("AQUI VA EL MENU!!!!");
            popupMenu.show(root, m.getScreenX(),m.getScreenY());
        }
        else{
            LOGGER.info("clicks");
            
            if(!heVotado){
                tablaBiblioteca.getSelectionModel().select(null);
                txtDescripcion.setText("");
                imgLike.setDisable(true);
                imgLike.setVisible(false);
                imgDislike.setDisable(true);
                imgDislike.setVisible(false);
                imgDescarga.setDisable(true);
                imgDescarga.setVisible(false);
            }
            heVotado=false;
            popupMenu.hide();
            
        }
    }/**
     * Control de las puntuciones de un apunte.
     * @param m
     */
    public void puntuacion(MouseEvent m){
        if(m.getButton()== MouseButton.PRIMARY){
            if(tablaBiblioteca.getSelectionModel().getSelectedIndex()>=0){
                imgLike.setDisable(false);
                imgLike.setVisible(true);
                imgDislike.setDisable(false);
                imgDislike.setVisible(true);
                imgDescarga.setDisable(false);
                imgDescarga.setVisible(true);
                
                txtDescripcion.setText(tablaBiblioteca.getSelectionModel().getSelectedItem().getDescripcion());
                LOGGER.info(tablaBiblioteca.getSelectionModel().getSelectedItem().getDescripcion());
                if(m.getTarget().equals(imgLike)){
                    heVotado=true;
                    if(!coprobarVoto()){
                        LOGGER.info("voy a votar");
                        try {
                            apuntesLogic.votacion(user.getId(), 1, tablaBiblioteca.getSelectionModel().getSelectedItem());
                        } catch (BusinessLogicException ex) {
                            Logger.getLogger(BibliotecaClienteFXController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                else if(m.getTarget().equals(imgDislike)){
                    heVotado=true;
                    if(!coprobarVoto()){
                        LOGGER.info("voy a votar");
                        try {
                            apuntesLogic.votacion(user.getId(), -1, tablaBiblioteca.getSelectionModel().getSelectedItem());
                        } catch (BusinessLogicException ex) {
                            Logger.getLogger(BibliotecaClienteFXController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                else if(m.getTarget().equals(imgDescarga)){
                    heVotado=true;
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter =new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File fileC = fileChooser.showSaveDialog(stage);
                    
                    ApunteBean elApunte =tablaBiblioteca.getSelectionModel().getSelectedItem();
                    writeBytesToFile(elApunte.getArchivo(),fileC);
                }
            }
        }
        
    }
    private Boolean coprobarVoto() {
        Boolean votado = false;
        try {
            LOGGER.info(String.valueOf(tablaBiblioteca.getSelectionModel().getSelectedItem().getIdApunte()));
            Set<ClienteBean> clientes=clienteLogic.getVotantesId(tablaBiblioteca.getSelectionModel().getSelectedItem().getIdApunte());
            if(clientes!=null){
                LOGGER.info("esta lleno");
                for(ClienteBean cliente:clientes){
                    if(cliente.getId().equals(user.getId())){
                        LOGGER.info("ha votado------------------------");
                        votado = true;
                    }
                }
            }
            
        } catch (BusinessLogicException ex) {
            Logger.getLogger(BibliotecaClienteFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return votado;
    }
    
    private void writeBytesToFile(byte[] archivo, File fileC) {
         
        FileOutputStream fileOuputStream = null;
        
        try {
            fileOuputStream = new FileOutputStream(fileC.getPath());
            fileOuputStream.write(archivo);
            
        } catch (IOException e) {
            LOGGER.severe("Error al descargar el apunte en un fichero: "+e.getMessage());
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    LOGGER.severe("Error al intentar cerrar el stream para descargar el fichero: "+e.getMessage());
                }
            }
        }
    }
    private void comboControl(ObservableValue obvservable, Object oldValue, Object newValue){
        ObservableList<ApunteBean> arrayBusquedaPersona = FXCollections.observableArrayList();
        LOGGER.info(String.valueOf(comboFiltroBiblioteca.getSelectionModel().getSelectedIndex()));
        if(newValue !=null){
            apuntes.stream().filter((apunte) -> (apunte.getMateria().toString().toUpperCase().equals(materias_titulo.get(comboFiltroBiblioteca.getSelectionModel().getSelectedIndex()).toUpperCase()))).map((apunte) -> {
                arrayBusquedaPersona.add(apunte);
                return apunte;
            }).forEachOrdered((_item) -> {
                LOGGER.info("he ENCONTRADO");
            });
            if(arrayBusquedaPersona.size()>0){
                tablaBiblioteca.setItems(arrayBusquedaPersona);
                tablaBiblioteca.refresh();
            }
        }
        if(comboFiltroBiblioteca.getSelectionModel().getSelectedIndex()==0){
            LOGGER.info("he entrados");
            tablaBiblioteca.setItems(apuntes);
            tablaBiblioteca.refresh();
        }
    }
    @FXML
    private void buscar(ActionEvent event){
        LOGGER.info("He clicado buscar");
        String palabraBusqueda;
        ObservableList<ApunteBean> arrayBusquedaPersona = FXCollections.observableArrayList();
        if(txtBuscar.getText().isEmpty()){
            tablaBiblioteca.setItems(apuntes);
            tablaBiblioteca.refresh();
        }
        else{
            palabraBusqueda = txtBuscar.getText().toUpperCase();
            LOGGER.info(palabraBusqueda);
            apuntes.stream().filter((apunte) -> (apunte.getTitulo().toUpperCase().contains(palabraBusqueda) ||
                    apunte.getMateria().toString().toUpperCase().contains(palabraBusqueda)||
                    apunte.getCreador().toString().toUpperCase().contains(palabraBusqueda) ||
                    apunte.getDescripcion().toUpperCase().contains(palabraBusqueda))).map((apunte) -> {
                        arrayBusquedaPersona.add(apunte);
                        return apunte;
                    }).forEachOrdered((_item) -> {
                        LOGGER.info("he ENCONTRADO");
                    });
            if(arrayBusquedaPersona.size()>0){
                tablaBiblioteca.setItems(arrayBusquedaPersona);
                tablaBiblioteca.refresh();
            }
            else
                LOGGER.info("No se ha encontrado conicidencias");
            
        }
    }
    @FXML private void descargarApunte(MouseEvent m){
        LOGGER.info("voy a descargr");
    }
    //Inicio de los metodos de navegación de la aplicación
    //Parte comun
    @FXML
    private void onActionCerrarSesion(ActionEvent event){
        try{
            //Creamos la alerta del tipo confirmación.
            Alert alertCerrarSesion = new Alert(Alert.AlertType.CONFIRMATION);
            //Ponemos titulo de la ventana como titulo para la alerta.
            alertCerrarSesion.setTitle("Cerrar sesión");
            alertCerrarSesion.setHeaderText("¿Quieres cerrar sesión?");
            //Si acepta se cerrara esta ventana.
            alertCerrarSesion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    LOGGER.info("Cerrando sesión.");
                    stage.hide();
                }
            });
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
    }
    @FXML
    private void onActionSalir(ActionEvent event){
        try{
            //Creamos la alerta con el tipo confirmación, con su texto y botones de
            //aceptar y cancelar.
            Alert alertCerrarAplicacion = new Alert(Alert.AlertType.CONFIRMATION,"Si sale de la aplicación cerrara\nautomáticamente la sesión.",ButtonType.NO,ButtonType.YES);
            //Añadimos titulo a la ventana como el alert.
            alertCerrarAplicacion.setTitle("Cerrar la aplicación");
            alertCerrarAplicacion.setHeaderText("¿Quieres salir de la aplicación?");
            //Si acepta cerrara la aplicación.
            alertCerrarAplicacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    LOGGER.info("Cerrando la aplicación.");
                    Platform.exit();
                }
            });
        }catch(Exception e){
            LOGGER.severe(e.getMessage());
        }
    }
    
    //Inicio de los metodos de navegación de la aplicación
    @FXML
    private void onActionAbrirMisApuntes(ActionEvent event){
        /*try{
        FXMLLoader loader = new FXMLLoader(getClass()
        .getResource("cliente_misApuntes.fxml"));
        Parent root = (Parent)loader.load();
        BibliotecaClienteFXController controller =
        ((BibliotecaClienteFXController)loader.getController());
        
        //controller.setUser(user);
        controller.setStage(stage);
        controller.initStage(root);
        stage.hide();
        }catch(Exception e){
        showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor."+e.getMessage());
        }*/
    }
    @FXML
    private void onActionAbrirTiendaApuntes(ActionEvent event){
        /*  try{
        FXMLLoader loader = new FXMLLoader(getClass()
        .getResource("tienda_apuntes.fxml"));
        Parent root = (Parent)loader.load();
        TiedaApuntesFXController controller =
        ((TiedaApuntesFXController)loader.getController());
        
        //controller.setUser(user);
        controller.setStage(stage);
        controller.initStage(root);
        stage.hide();
        }catch(Exception e){
        showErrorAlert("A ocurrido un error, reinicie la aplicación porfavor."+e.getMessage());
        }*/
    }
    @FXML
    private void onActionAbrirMiBiblioteca(ActionEvent event){
    }
    @FXML
    private void onActionAbrirTiendaPacks(ActionEvent event){
    }
    @FXML
    private void onActionAbrirMiPerfil(ActionEvent event){
    }
    @FXML
    private void onActionAbout(ActionEvent event){
    }
    //Fin de los metodos de navegación de la aplicación

    
    
}
