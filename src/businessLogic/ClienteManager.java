/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.LoginNotFoundException;
import exceptions.PasswordWrongException;
import java.util.List;
import java.util.Set;
import transferObjects.ClienteBean;

/**
 *
 * @author Usuario
 */
public interface ClienteManager {
    public void create(ClienteBean cliente) throws BusinessLogic;
    public void edit( ClienteBean cliente) throws BusinessLogic;
    public void remove(Integer id) throws BusinessLogic;
    public ClienteBean find( Integer id) throws BusinessLogic;
    public Set<ClienteBean> findAll() throws BusinessLogic;
    public List <ClienteBean> getVotantesId( Integer id) throws BusinessLogic;
    public void actualizarContrasenia(ClienteBean cliente) throws BusinessLogic;
    public void comprarApunte(ClienteBean cliente, Integer idApunte) throws BusinessLogic;
    public boolean passwordForgot( String login) throws BusinessLogic;
    public ClienteBean iniciarSesion(String login,String contrasenia)throws BusinessLogic, PasswordWrongException, LoginNotFoundException;
    
}
