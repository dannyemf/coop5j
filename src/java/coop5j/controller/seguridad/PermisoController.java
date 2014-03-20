/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.seguridad;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.dat.ItemGrupo;
import coop5j.model.seguridad.Permiso;
import coop5j.service.GrupoService;
import coop5j.service.PermisoService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controlador de permisos
 * 
 * @author Danny Muñoz
 */

@Named
@ConversationScoped
public class PermisoController extends AbstractController{
    
    @EJB
    private PermisoService service;
    
    @EJB
    private GrupoService groupService;
    
    @Inject
    private ContextBean context;
    
    @Inject
    private Conversation conversation;          
    
    /**
     * Lista de grupos para permitir seleccionar al usuario mediante un check
     */
    private List<ItemGrupo> grupos = new ArrayList<ItemGrupo>();
    
    /**
     * Usuario en edición
     */
    private Permiso modeloEdicion;       

    public PermisoController() {
    }
    
    @PostConstruct
    public void init(){        
    }
    
    /**
     * Inicia la conversación
     */
    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    /**
     * Termina la conversación
     */
    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }        
    
    /**
     * Evento invocada al presionar el botón nuevo
     * @return 
     */
    public String eventoNuevo(){
        System.out.println("eventoNuevo(): " + conversation.getId());
        beginConversation();
        modeloEdicion = new Permiso();
        
        grupos = groupService.obtenerGrupos(modeloEdicion);
        return "editar.xhtml?faces-redirect=true";
    }
    
    /**
     * Evento invocada al presionar el vínculo editar
     * @param item El usuario a editar
     * @return 
     */
    public String eventoEditar(Permiso item){
        System.out.println("eventoEditar(): ");
        beginConversation();        
        modeloEdicion = item;        
        grupos = groupService.obtenerGrupos(modeloEdicion);
        return "editar.xhtml?faces-redirect=true";
    }
    
    /**
     * Evento para cancelar la edición
     * @return 
     */
    public String eventoCancelar(){
        System.out.println("eventoCancelar(): ");
        //endConversation();        
        return "lista.xhtml?faces-redirect=true";
    }
    
    /**
     * Evento para guardar el usuario en edición
     * @return 
     */
    public String eventoGuardar(){
        System.out.println("eventoGuardar(): ");
        
        for (Iterator<ItemGrupo> it = grupos.iterator(); it.hasNext();) {
            ItemGrupo ig = it.next();
            if(ig.isChecked()){
                modeloEdicion.getGrupos().add(ig.getGrupo());
            }else{
                modeloEdicion.getGrupos().remove(ig.getGrupo());
            }
        }
        
        service.save(modeloEdicion);
        
        
        //endConversation();
        return "lista.xhtml?faces-redirect=true";
    }    

    /**
     * @return the grupos
     */
    public List<ItemGrupo> getGrupos() {
        return grupos;
    }

    /**
     * @param grupos the grupos to set
     */
    public void setGrupos(List<ItemGrupo> grupos) {
        this.grupos = grupos;
    }

    /**
     * @return the modeloEdicion
     */
    public Permiso getModeloEdicion() {
        return modeloEdicion;
    }

    /**
     * @param modeloEdicion the modeloEdicion to set
     */
    public void setModeloEdicion(Permiso modeloEdicion) {
        this.modeloEdicion = modeloEdicion;
    }    
           
}
