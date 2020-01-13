/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import java.util.Set;
import transferObjects.OfertaBean;
/**
 *
 * @author 2dam
 */
public interface OfertaManager {
    public void createOferta(OfertaBean oferta) throws BusinessLogic;
    public Collection todasOfertas() throws BusinessLogic;
    public void borrarOferta(Integer idOferta) throws BusinessLogic;
    public void actualizarOferta(OfertaBean oferta) throws BusinessLogic;
}
