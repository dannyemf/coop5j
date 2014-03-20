/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.seguridad;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.seguridad.Grupo;
import coop5j.service.GrupoService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controlador de grupos de usuarios
 * 
 * @author Danny Muñoz
 */

@Named
@ConversationScoped
public class GrupoController extends AbstractController{   
    
    @EJB
    private GrupoService service;
    
    @Inject
    private ContextBean context;
    
    @Inject
    private Conversation conversation;        
    
    /**
     * Grupo en edición
     */
    private Grupo modeloEdicion;   
    
    private String textoBusqueda;
    private List<Grupo> listaDatos = new ArrayList<Grupo>();

    public GrupoController() {
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
     * Evento buscar invocada al presionar el botón buscar
     * @param evento 
     */
    public void eventoBuscar(ActionEvent evento){
        listaDatos = service.buscarPor(textoBusqueda);
    }
    
    /**
     * Evento nuevo invocada al presionar el botón nuevo
     * @return 
     */
    public String eventoNuevo(){       
        beginConversation();        
        modeloEdicion = new Grupo();
        return "editar.xhtml?faces-redirect=true";
    }
    
    /**
     * Evento editar invocada al presionar el vínculo editar
     * @param item El grupo a editar
     * @return 
     */
    public String eventoEditar(Grupo item){
        System.out.println("eventoEditar(): ");
        modeloEdicion = item;
        beginConversation();                
        return "editar.xhtml?faces-redirect=true";
    }
    
    /**
     * Evento para cancelar la edición del grupo
     * @return 
     */
    public String eventoCancelar(){
        System.out.println("eventoCancelar(): ");
        endConversation();
        
        return "lista.xhtml?faces-redirect=true";
    }
    
    /**
     * Evento para guardar el grupo en edición
     * @return 
     */
    public String eventoGuardar(){
        System.out.println("eventoGuardar(): ");               
        service.save(modeloEdicion);
        endConversation();
        
        return "lista.xhtml?faces-redirect=true";
    }   

    /**
     * @return the modeloEdicion
     */
    public Grupo getModeloEdicion() {
        return modeloEdicion;
    }

    /**
     * @param modeloEdicion the modeloEdicion to set
     */
    public void setModeloEdicion(Grupo modeloEdicion) {
        this.modeloEdicion = modeloEdicion;
    }    

    /**
     * @return the textoBusqueda
     */
    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    /**
     * @param textoBusqueda the textoBusqueda to set
     */
    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    /**
     * @return the listaDatos
     */
    public List<Grupo> getListaDatos() {
        return listaDatos;
    }

    /**
     * @param listaDatos the listaDatos to set
     */
    public void setListaDatos(List<Grupo> listaDatos) {
        this.listaDatos = listaDatos;
    }
            
}
