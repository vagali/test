/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Set;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import service.ApunteRESTClient;
import transferObjects.ApunteBean;

/**
 *
 * @author 2dam
 */
public class ApunteManagerImplementation implements ApunteManager{
    private ApunteRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("BusinessLogic.ClienteManagerImplementation");
    
    public ApunteManagerImplementation() {
        webClient= new ApunteRESTClient();
    }
    @Override
    public void create(ApunteBean apunte) throws BusinessLogic {
        try{
            webClient.create(apunte);
        }catch(Exception e){
            LOGGER.severe("ERROR! ApunteManagerImplementation -> create: "+e.getMessage());
            throw new BusinessLogic(e.getMessage());
        }
    }

    @Override
    public void edit(ApunteBean apunte) throws BusinessLogic {
        try{
            webClient.edit(apunte);
        }catch(Exception e){
            LOGGER.severe("ERROR! ApunteManagerImplementation -> create: "+e.getMessage());
            throw new BusinessLogic(e.getMessage());
        }
    }

    @Override
    public void remove(Integer id) throws BusinessLogic {
        try{
            webClient.remove(id.toString());
        }catch(Exception e){
            LOGGER.severe("ERROR! ApunteManagerImplementation -> create: "+e.getMessage());
            throw new BusinessLogic(e.getMessage());
        }
    }

    @Override
    public ApunteBean find(Integer id) throws BusinessLogic {
        ApunteBean resultado=null;
        try{
           resultado=webClient.find(ApunteBean.class, id.toString());
        }catch(Exception e){
            LOGGER.severe("ERROR! ApunteManagerImplementation -> iniciarSesion: "+e.getMessage());
            throw new BusinessLogic(e.getMessage());
        }
        return resultado;
    }

    @Override
    public Set<ApunteBean> findAll() throws BusinessLogic {
        Set<ApunteBean> resultado=null;
        try{
           resultado=webClient.findAll(new GenericType<Set<ApunteBean>>() {});
        }catch(Exception e){
            LOGGER.severe("ERROR! ApunteManagerImplementation -> findAll: "+e.getMessage());
            throw new BusinessLogic(e.getMessage());
        }
        return resultado;
    }

    @Override
    public Set<ApunteBean> getApuntesByCreador(Integer id) throws BusinessLogic {
        Set<ApunteBean> resultado=null;
        try{
           resultado=webClient.getApuntesByCreador(new GenericType<Set<ApunteBean>>() {}, id.toString());
        }catch(Exception e){
            LOGGER.severe("ERROR! ApunteManagerImplementation -> iniciarSesion: "+e.getMessage());
            throw new BusinessLogic(e.getMessage());
        }
        return resultado;
    }

    @Override
    public Set<ApunteBean> getApuntesByComprador(Integer id) throws BusinessLogic {
        Set<ApunteBean> resultado=null;
        try{
           resultado=webClient.getApuntesByComprador(new GenericType<Set<ApunteBean>>() {}, id.toString());
        }catch(Exception e){
            LOGGER.severe("ERROR! ApunteManagerImplementation -> iniciarSesion: "+e.getMessage());
            throw new BusinessLogic(e.getMessage());
        }
        return resultado;
    }

    @Override
    public void votacion(Integer idCliente, Integer like, ApunteBean apunte) throws BusinessLogic {
         try{
            webClient.votacion(apunte,idCliente.toString(),like.toString());
        }catch(Exception e){
            LOGGER.severe("ERROR! ApunteManagerImplementation -> create: "+e.getMessage());
            throw new BusinessLogic(e.getMessage());
        }
    }
    
}
