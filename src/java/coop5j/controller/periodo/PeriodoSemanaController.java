/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.periodo;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.core.Periodo;
import coop5j.model.core.QSemana;
import coop5j.model.core.Semana;
import coop5j.service.PeriodoService;
import coop5j.service.SocioService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * Controlador de usuarios
 * @author Danny Muñoz
 */

@Named(value = "perSemController")
@ConversationScoped
public class PeriodoSemanaController extends AbstractController{
    
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
    private List<Semana> listaSemanas = new ArrayList<Semana>();
    private String diaSemana = "lun";

    public PeriodoSemanaController() {
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
    public String eventoAdmSemanas(Periodo item){
        System.out.println("eventoAdmSemanas(): ");        
        beginConversation();        
        modeloEdicion = item;
        
        QSemana c = QSemana.semana;
                
        listaSemanas = service.from(c).where(c.periodo.eq(item)).list(c);
        
        return toRedirect("listaSemanas.xhtml");
    }
    
    public void crearSemanas(ActionEvent ev){
        Locale l = new Locale("es", "EC");
        Calendar c = GregorianCalendar.getInstance(l);
        SimpleDateFormat s = new SimpleDateFormat("E", l);
        Date fi = modeloEdicion.getFechaInicio();
        Date ff = modeloEdicion.getFechaFin();        
        c.setTime(fi);
        
        while (!s.format(fi).equals(diaSemana)){
            c.add(Calendar.DATE, 1);
            fi = c.getTime();
        }
        
        while(fi.before(ff) || fi.equals(ff)){
            Semana sm = new Semana(fi, modeloEdicion);
            listaSemanas.add(sm);
            service.save(sm);    
            
            c.add(Calendar.DATE, 7);
            fi = c.getTime();
        }
        
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
    public List<Semana> getListaSemanas() {
        return listaSemanas;
    }

    /**
     * @param listaCuentas the listaCuentas to set
     */
    public void setListaSemanas(List<Semana> listaSemanas) {
        this.listaSemanas = listaSemanas;
    }  

    /**
     * @return the diaSemana
     */
    public String getDiaSemana() {
        return diaSemana;
    }

    /**
     * @param diaSemana the diaSemana to set
     */
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    
}
