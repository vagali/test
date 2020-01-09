/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@XmlRootElement(name="cliente")
public class ClienteBean extends UserBean implements Serializable{
    //private static final long serialVersionUID=1L;
    
    
   // private Set <CompraBean> compras;
   // private Set <ApunteBean> apuntes;
 //   private Set <ApunteBean> misVotaciones;
    private float saldo;
    private byte[] foto;
/*
    public ClienteBean(Set<CompraBean> compras, Set<ApunteBean> apuntes, Set<ApunteBean> misVotaciones, float saldo, byte[] foto, Integer id, String login, String email, String nombreCompleto, UserStatus status, UserPrivilege privilegio, String contrasenia, Date ultimoAcceso, Date ultimoCambioContrasenia) {
        super(id, login, email, nombreCompleto, status, privilegio, contrasenia, ultimoAcceso, ultimoCambioContrasenia);
        this.compras = compras;
        this.apuntes = apuntes;
        this.misVotaciones = misVotaciones;
        this.saldo = saldo;
        this.foto = foto;
    }
*/
    public ClienteBean() {
    }

    public ClienteBean(float saldo, byte[] foto, Integer id, String login, String email, String nombreCompleto, UserStatus status, UserPrivilege privilegio, String contrasenia, Date ultimoAcceso, Date ultimoCambioContrasenia) {
        super(id, login, email, nombreCompleto, status, privilegio, contrasenia, ultimoAcceso, ultimoCambioContrasenia);
        this.saldo = saldo;
        this.foto = foto;
    }

    /**
     * @return the saldo
     */
    public float getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the foto
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
    
    
    
    
}
