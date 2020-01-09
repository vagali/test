package transferObjects;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis
 */
@XmlRootElement(name="materia")
public class MateriaBean2 implements Serializable {
    private static final Long serialVersionUID = 1L;
    
    private Integer idMateria;
    private String titulo;
    private String descripcion;

    public MateriaBean2(Integer idMateria, String titulo, String descripcion) {
        this.idMateria = idMateria;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public MateriaBean2() {
    }

    
    /**
     * @return the idMateria
     */
    public Integer getIdMateria() {
        return idMateria;
    }

    /**
     * @param idMateria the idMateria to set
     */
    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
   
}

