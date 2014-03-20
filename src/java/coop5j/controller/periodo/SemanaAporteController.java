/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.periodo;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.core.AporteSemanal;
import coop5j.model.core.Cuenta;
import coop5j.model.core.QAporteSemanal;
import coop5j.model.core.QCuenta;
import coop5j.model.core.Semana;
import coop5j.service.PeriodoService;
import coop5j.service.SocioService;
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
 * Controlador de usuarios
 * @author Danny Muñoz
 */

@Named(value = "semApoController")
@ConversationScoped
public class SemanaAporteController extends AbstractController{
    
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
    private List<AporteSemanal> listaAportes = new ArrayList<AporteSemanal>();    

    public SemanaAporteController() {
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
     * Evento invocada al presionar el vínculo editar
     * @param item El usuario a editar
     * @return 
     */
    public String eventoAdmAportes(Semana item){
        System.out.println("eventoAdmSemanas(): ");        
        beginConversation();        
        modeloEdicion = item;
        
        QCuenta c = QCuenta.cuenta;
                
        List<Cuenta> listaCuentas = service.from(c).where(c.periodo.eq(item.getPeriodo())).list(c);
        for (Iterator<Cuenta> it = listaCuentas.iterator(); it.hasNext();) {
            Cuenta cc = it.next();
            QAporteSemanal a = QAporteSemanal.aporteSemanal;
            
            AporteSemanal as = service.from(a).where(a.semana.eq(item), a.cuenta.eq(cc)).singleResult(a);
            if(as == null){
                as = new AporteSemanal(item, cc);
                service.save(as);
            }
            listaAportes.add(as);
        }
        
        return toRedirect("listaAportes.xhtml");
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
    public Semana getModeloEdicion() {
        return modeloEdicion;
    }

    /**
     * @param modeloEdicion the modeloEdicion to set
     */
    public void setModeloEdicion(Semana modeloEdicion) {
        this.modeloEdicion = modeloEdicion;
    }   

    /**
     * @return the listaAportes
     */
    public List<AporteSemanal> getListaAportes() {
        return listaAportes;
    }

    /**
     * @param listaCuentas the listaAportes to set
     */
    public void setListaAportes(List<AporteSemanal> listaAportes) {
        this.listaAportes = listaAportes;
    }     
    
}
