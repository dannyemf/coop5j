/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.periodo;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.core.Semana;
import coop5j.service.PeriodoService;
import coop5j.service.SocioService;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;



/**
 * Controlador de usuarios
 * @author Danny Muñoz
 */

@Named(value = "semController")
@ConversationScoped
public class SemanaController extends AbstractController{
    
    @EJB
    private PeriodoService service;
    
    @EJB
    private SocioService serviceSocio;
    
    @Inject
    private ContextBean context;
    
    @Inject
    private Conversation conversation;       
    
    /**
     * Socio en edición
     */
    private Semana modeloEdicion;     

    public SemanaController() {
    }
    
    @PostConstruct
    public void init(){    
        
    }
                
    
    
    /**
     * Evento invocada al presionar el vínculo editar
     * @param item El usuario a editar
     * @return 
     */
    public String eventoEditar(Semana item){
        System.out.println("eventoEditar(): ");        
          
        modeloEdicion = item;        
        return toRedirect("editarSemana.xhtml");
    }        
                
    
    /**
     * Evento para cancelar la edición
     * @return 
     */
    public String eventoCancelar(){
        System.out.println("eventoCancelar(): ");
            
        return toRedirect("listaSemanas.xhtml");
    }
    
    /**
     * Evento para guardar el usuario en edición
     * @return 
     */
    public String eventoGuardar(){
        System.out.println("eventoGuardar(): ");                                            
        service.save(modeloEdicion);
        
        return toRedirect("listaSemanas.xhtml");        
    }      
    
    

    /**
     * @return the modeloEdicion
     */
    public Semana getModeloEdicion() {
        return modeloEdicion;
    }

    /**
     * @param modeloEdicion the modeloEdicion to set
     */
    public void setModeloEdicion(Semana modeloEdicion) {
        this.modeloEdicion = modeloEdicion;
    }     
    
}
