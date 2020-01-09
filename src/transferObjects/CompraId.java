/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferObjects;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class CompraId implements Serializable{
    private Integer clienteId;
    private Integer apunteId;

    /**
     * @return the clienteId
     */
    public Integer getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @return the apunteId
     */
    public Integer getApunteId() {
        return apunteId;
    }

    /**
     * @param apunteId the apunteId to set
     */
    public void setApunteId(Integer apunteId) {
        this.apunteId = apunteId;
    }

    public CompraId() {
    }

    public CompraId(Integer clienteId, Integer apunteId) {
        this.clienteId = clienteId;
        this.apunteId = apunteId;
    }
    
    
}
