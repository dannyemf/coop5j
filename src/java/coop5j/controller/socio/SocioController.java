/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.socio;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.core.QSocio;
import coop5j.model.core.Socio;
import coop5j.service.SocioService;
import coop5j.util.CedulaUtil;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;


/**
 * Controlador de usuarios
 * @author Danny Muñoz
 */

@Named
@ConversationScoped
public class SocioController extends AbstractController{
    
    @EJB
    private SocioService service;        
    
    @Inject
    private ContextBean context;
    
    @Inject
    private Conversation conversation;    
    
    //@Inject
    //private transient Logger logger;
    
    /**
     * Socio en edición
     */
    private Socio modeloEdicion;        

    public SocioController() {
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
        modeloEdicion = new Socio();
        
        return toRedirect("editar.xhtml");
    }
    
    /**
     * Evento invocada al presionar el vínculo editar
     * @param item El usuario a editar
     * @return 
     */
    public String eventoEditar(Socio item){
        System.out.println("eventoEditar(): ");
        beginConversation();        
        modeloEdicion = item;
        
        if(!item.isCedulado() && item.getRepresentante() != null){
            item.setCedula(item.getRepresentante().getCedula());
        }
        
        return toRedirect("editar.xhtml");
    }
    
    /**
     * Evento para cancelar la edición
     * @return 
     */
    public String eventoCancelar(){
        System.out.println("eventoCancelar(): ");
        //endConversation();        
        return toRedirect("lista.xhtml");
    }
    
    /**
     * Evento para guardar el usuario en edición
     * @return 
     */
    public String eventoGuardar(){
        System.out.println("eventoGuardar(): ");                
        
        if(doValidCedula()){
            
            if(!modeloEdicion.isCedulado()){
                QSocio s = QSocio.socio;
                Socio r = service.from(s).where(s.cedula.eq(modeloEdicion.getCedula()), s.cedulado.eq(true)).singleResult(s);
                modeloEdicion.setRepresentante(r);
                modeloEdicion.setCedula("");
            }
            
            service.save(modeloEdicion);
            return toRedirect("lista.xhtml");
        }
        
        return null;
                
        //endConversation();
        
    }      
    
    public boolean doValidCedula() {
        boolean valid = true;
        boolean v = CedulaUtil.validar(modeloEdicion.getCedula());
        String mensaje = null;
        
        if(!v){
            mensaje = "Número de cédula incorrecto";
            valid = false;
        }else{
            if(modeloEdicion.isCedulado()){
                if (!service.isUnique(modeloEdicion, "cedula", modeloEdicion.getCedula())){
                    mensaje = "Ya se ha registrado un socio con este número de cédula";
                    valid = false;
                }
            }else{
                QSocio s = QSocio.socio;
                long socios = service.from(s).where(s.cedula.eq(modeloEdicion.getCedula()), s.cedulado.eq(true)).count();
                if (socios == 0){
                    mensaje = "No existe un socio registrado con este número de cédula que lo pueda representar";
                    valid = false;
                }
            }
        }
                       
        if(mensaje != null){                
            FacesMessage fm = new FacesMessage(mensaje);
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("hform:frmEdit:txtCedula:control", fm);
        }
        
        return valid;
    }

    /**
     * @return the modeloEdicion
     */
    public Socio getModeloEdicion() {
        return modeloEdicion;
    }

    /**
     * @param modeloEdicion the modeloEdicion to set
     */
    public void setModeloEdicion(Socio modeloEdicion) {
        this.modeloEdicion = modeloEdicion;
    }   
    
}
