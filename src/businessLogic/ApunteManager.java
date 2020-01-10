/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Set;
import transferObjects.ApunteBean;

/**
 *
 * @author 2dam
 */
public interface ApunteManager {
    public void create(ApunteBean apunte) throws BusinessLogicException;
    public void edit( ApunteBean apunte) throws BusinessLogicException;
    public void remove(Integer id) throws BusinessLogicException;
    public ApunteBean find(Integer id) throws BusinessLogicException;
    public Set<ApunteBean> findAll()throws BusinessLogicException;
    public Set<ApunteBean> getApuntesByCreador(Integer id) throws BusinessLogicException;
    public Set<ApunteBean> getApuntesByComprador(Integer id) throws BusinessLogicException;
    public void votacion(Integer idCliente,Integer like, ApunteBean apunte) throws BusinessLogicException;
    public int cuantasCompras(Integer id) throws BusinessLogicException;
}
