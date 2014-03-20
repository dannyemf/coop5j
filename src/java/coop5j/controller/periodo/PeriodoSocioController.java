/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.periodo;


import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.core.Cuenta;
import coop5j.model.core.Periodo;
import coop5j.model.core.QCuenta;
import coop5j.model.core.QSocio;
import coop5j.model.core.Socio;
import coop5j.service.PeriodoService;
import coop5j.service.SocioService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;



/**
 * Controlador de usuarios
 * @author Danny Muñoz
 */

@Named(value = "perSocController")
@ConversationScoped
public class PeriodoSocioController extends AbstractController{
    
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
    private Periodo modeloEdicion;     
    
    
    private Socio selectedSocio = null; // Text the user is typing in
    private int acciones = 1;
    private List<Cuenta> listaCuentas = new ArrayList<Cuenta>();

    public PeriodoSocioController() {
    }
    
    @PostConstruct
    public void init(){        
    }
                
    
    public List<Socio> textChangeEventHandler(String filter) {                
        filter = service.toLike(filter);        
        QSocio s = QSocio.socio;        
        List<Socio> sociosAutocomplete = serviceSocio.from(s).where(s.cedula.like(filter).or(s.nombres.like(filter)).or(s.apellidos.like(filter))).limit(10L).list(s);
        System.out.println("Filter: " + filter + ", Results: " + sociosAutocomplete.size());
        return sociosAutocomplete;
    }
    
    public void rowEditEvent(RowEditEvent e){
        Cuenta c =  (Cuenta)e.getObject();
        System.out.println("rowEditEvent(): " + c);        
        service.save(c);
    }
    
    public void addSocio(ActionEvent e){
        
        FacesMessage fm = null;
                
        if (selectedSocio != null){                
            QCuenta cc = QCuenta.cuenta;
            if(!service.from(cc).where(cc.socio.eq(selectedSocio), cc.periodo.eq(modeloEdicion)).exists()){                    
                Cuenta c = new Cuenta(modeloEdicion, selectedSocio, acciones);                        
                c.setUsuario(context.getUsuario());

                service.save(c);
                listaCuentas.add(c);
            }else{
                fm = new FacesMessage("Ya se ha registrado una cuenta para este socio");                       
            }                            
        }else{
            fm = new FacesMessage("Por favor seleccione un socio");
        }
        
        if(fm != null){
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("frmEdit:acSocio", fm);
        }
        
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
    public String eventoAdmSocios(Periodo item){
        System.out.println("eventoAdmSocios(): ");        
        beginConversation();        
        modeloEdicion = item;
        
        QCuenta c = QCuenta.cuenta;
                
        listaCuentas = service.from(c).where(c.periodo.eq(item)).list(c);
        
        return toRedirect("listaSocios.xhtml");
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

    /**
     * @return the listaCuentas
     */
    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    /**
     * @param listaCuentas the listaCuentas to set
     */
    public void setListaCuentas(List<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    /**
     * @return the acciones
     */
    public int getAcciones() {
        return acciones;
    }

    /**
     * @param acciones the acciones to set
     */
    public void setAcciones(int acciones) {
        this.acciones = acciones;
    }

    /**
     * @return the selectedSocio
     */
    public Socio getSelectedSocio() {
        return selectedSocio;
    }

    /**
     * @param selectedSocio the selectedSocio to set
     */
    public void setSelectedSocio(Socio selectedSocio) {
        this.selectedSocio = selectedSocio;
    }
    
}
