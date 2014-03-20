/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.periodo;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.core.Periodo;
import coop5j.service.PeriodoService;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * Controlador de usuarios
 * @author Danny Muñoz
 */

@Named
@ConversationScoped
public class PeriodoController extends AbstractController{
    
    @EJB
    private PeriodoService service;        
    
    @Inject
    private ContextBean context;
    
    @Inject
    private Conversation conversation;                    
    
    /**
     * Socio en edición
     */
    private Periodo modeloEdicion;        

    public PeriodoController() {
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
        modeloEdicion = new Periodo();        
        return toRedirect("editar.xhtml");
    }
    
    /**
     * Evento invocada al presionar el vínculo editar
     * @param item El usuario a editar
     * @return 
     */
    public String eventoEditar(Periodo item){
        System.out.println("eventoEditar(): ");        
        beginConversation();        
        modeloEdicion = item;
        return toRedirect("editar.xhtml");
    }
    
    /**
     * Evento para cancelar la edición
     * @return 
     */
    public String eventoCancelar(){
        System.out.println("eventoCancelar(): ");
        endConversation();        
        return toRedirect("lista.xhtml");
    }
    
    /**
     * Evento para guardar el usuario en edición
     * @return 
     */
    public String eventoGuardar(){
        System.out.println("eventoGuardar(): ");                                            
        service.save(modeloEdicion);
        endConversation();
        return toRedirect("lista.xhtml");        
    }      
    
    

    /**
     * @return the modeloEdicion
     */
    public Periodo getModeloEdicion() {
        return modeloEdicion;
    }

    /**
     * @param modeloEdicion the modeloEdicion to set
     */
    public void setModeloEdicion(Periodo modeloEdicion) {
        this.modeloEdicion = modeloEdicion;
    }   
    
}
