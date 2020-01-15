/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.LoginNotFoundException;
import exceptions.NoEsUserException;
import exceptions.PasswordWrongException;
import transferObjects.UserBean;

/**
 *
 * @author Usuario
 */
public interface UserManager {
    public void createUser(UserBean user) throws BusinessLogicException;
    public void updateUser(UserBean user) throws BusinessLogicException;
    public Object iniciarSesion(String login,String contrasenia)throws BusinessLogicException, PasswordWrongException, LoginNotFoundException, NoEsUserException;
}
