/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferObjects;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableSet;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@XmlRootElement(name="apunte")
public class ApunteBean implements Serializable {
    private static final long serialVersionUID=1L;
    
    private final SimpleIntegerProperty idApunte;
    private final SimpleStringProperty titulo;
    private final SimpleStringProperty descripcion;
    private final SimpleObjectProperty<byte[]> archivo;
    private final SimpleObjectProperty<Date> fechaValidacion;
    private final SimpleIntegerProperty likeCont;
    private final SimpleIntegerProperty dislikeCont;
    private final SimpleSetProperty<ClienteBean> votantes;
    private final SimpleFloatProperty precio;
    private final SimpleObjectProperty<ClienteBean> creador;
    private final SimpleSetProperty<CompraBean> compras;
    private final SimpleSetProperty<PackBean> packs;
    private final SimpleObjectProperty<MateriaBean> materia;

    /**
     * @return the idApunte
     */
    public int getIdApunte() {
        return idApunte.get();
    }

    /**
     * @param idApunte the idApunte to set
     */
    public void setIdApunte(int idApunte) {
        this.idApunte.set(idApunte);
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo.get();
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion.get();
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    /**
     * @return the archivo
     */
    public byte[] getArchivo() {
        return archivo.get();
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(byte[] archivo) {
        this.archivo.set(archivo);
    }

    /**
     * @return the fechaValidacion
     */
    public Date getFechaValidacion() {
        return fechaValidacion.get();
    }

    /**
     * @param fechaValidacion the fechaValidacion to set
     */
    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion.set(fechaValidacion);
    }

    /**
     * @return the likeCont
     */
    public int getLikeCont() {
        return likeCont.get();
    }

    /**
     * @param likeCont the likeCont to set
     */
    public void setLikeCont(int likeCont) {
        this.likeCont.set(likeCont);
    }

    /**
     * @return the dislikeCont
     */
    public int getDislikeCont() {
        return dislikeCont.get();
    }

    /**
     * @param dislikeCont the dislikeCont to set
     */
    public void setDislikeCont(int dislikeCont) {
        this.dislikeCont.set(dislikeCont);
    }

    /**
     * @return the votantes
     */
    public ObservableSet<ClienteBean> getVotantes() {
        return votantes.get();
    }

    /**
     * @param votantes the votantes to set
     */
    public void setVotantes(ObservableSet<ClienteBean>votantes) {
        this.votantes.set(votantes);
    }

    /**
     * @return the precio
     */
    public Float getPrecio() {
        return precio.get();
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Float precio) {
        this.precio.set(precio);
    }

    /**
     * @return the creador
     */
    public ClienteBean getCreador() {
        return creador.get();
    }

    /**
     * @param creador the creador to set
     */
    public void setCreador(ClienteBean creador) {
        this.creador.set(creador);
    }

    /**
     * @return the compras
     */
    public ObservableSet<CompraBean> getCompras() {
        return compras.get();
    }

    /**
     * @param compras the compras to set
     */
    public void setCompras(ObservableSet<CompraBean> compras) {
        this.compras.set(compras);
    }

    /**
     * @return the packs
     */
    public ObservableSet<PackBean> getPacks() {
        return packs.get();
    }

    /**
     * @param packs the packs to set
     */
    public void setPacks(SetProperty<PackBean> packs) {
        this.packs.set(packs);
    }

    /**
     * @return the materia
     */
    public MateriaBean getMateria() {
        return materia.get();
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(MateriaBean materia) {
        this.materia.set(materia);
    }

    public ApunteBean(int idApunte, String titulo, String descripcion, byte[] archivo, Date fechaValidacion, int likeCont, int dislikeCont, ObservableSet<ClienteBean> votantes, Float precio, ClienteBean creador,ObservableSet<CompraBean> compras, ObservableSet<PackBean> packs, MateriaBean materia) {
        this.idApunte = new SimpleIntegerProperty(idApunte);
        this.titulo = new SimpleStringProperty(titulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.archivo = new SimpleObjectProperty(archivo);
        this.fechaValidacion = new SimpleObjectProperty(fechaValidacion);
        this.likeCont = new SimpleIntegerProperty(likeCont);
        this.dislikeCont = new SimpleIntegerProperty(dislikeCont);
        this.votantes = new SimpleSetProperty(votantes);
        this.precio = new SimpleFloatProperty(precio);
        this.creador = new SimpleObjectProperty(creador);
        this.compras = new SimpleSetProperty(compras);
        this.packs = new SimpleSetProperty(packs);
        this.materia = new SimpleObjectProperty(materia);
    }
    
    
}
