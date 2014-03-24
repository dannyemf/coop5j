/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.periodo;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.core.AporteBingo;
import coop5j.model.core.AporteSemanal;
import coop5j.model.core.Cuenta;
import coop5j.model.core.IngresoSalida;
import coop5j.model.core.QAporteBingo;
import coop5j.model.core.QAporteSemanal;
import coop5j.model.core.QCuenta;
import coop5j.model.core.QIngresoSalida;
import coop5j.model.core.QSemana;
import coop5j.model.core.Semana;
import coop5j.model.core.TipoIngresoSalida;
import coop5j.model.dat.ItemAporteBingo;
import coop5j.model.dat.ItemAporteSemana;
import coop5j.model.dat.ItemCuentaAportes;
import coop5j.service.PeriodoService;
import coop5j.service.SocioService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
    
    private long totalSemanasPeriodo;
    private long totalSemanasAnteriores;
    private int diasAgregar;
    
    /**
     * Socio en edición
     */
    private Semana modeloEdicion;             
    private ItemCuentaAportes itemAporte;
    private IngresoSalida rubro;
    
    private List<ItemCuentaAportes> listaAportes = new ArrayList<ItemCuentaAportes>();   
    private List<IngresoSalida> listaRubros = new ArrayList<>();
    
    

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
    
    public double calcularTotalSemanas(){
        double t = 0;
        for (Iterator<ItemCuentaAportes> it = listaAportes.iterator(); it.hasNext();) {
            ItemCuentaAportes itc = it.next();
            t += itc.calcularTotalSemanas();
        }
        return t;
    }
    
    public double calcularTotalBingo(){
        double t = 0;
        for (Iterator<ItemCuentaAportes> it = listaAportes.iterator(); it.hasNext();) {
            ItemCuentaAportes itc = it.next();
            t += itc.calcularTotalBingo();
        }
        return t;
    }
    
    public double calcularTotal(){
        double t = 0;
        for (Iterator<ItemCuentaAportes> it = listaAportes.iterator(); it.hasNext();) {
            ItemCuentaAportes itc = it.next();
            t += itc.calcularTotal();
        }
        return t;
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
        listaAportes.clear();
        listaRubros.clear();
        
        QCuenta c = QCuenta.cuenta;
        List<Cuenta> listaCuentas = service.from(c).where(c.periodo.eq(item.getPeriodo())).list(c);
               
        QSemana qs = QSemana.semana;
        
        totalSemanasPeriodo = (service.from(qs).where(qs.periodo.eq(modeloEdicion.getPeriodo())).count());                                        
        totalSemanasAnteriores = (service.from(qs).where(qs.fecha.before(modeloEdicion.getFecha()).or(qs.fecha.eq(modeloEdicion.getFecha()))).count());
        
        List<Semana> semanasAportes = service.from(qs).where(qs.periodo.eq(modeloEdicion.getPeriodo()).and(qs.fecha.before(modeloEdicion.getFecha()).or(qs.fecha.eq(modeloEdicion.getFecha())))).list(qs);
        
        QIngresoSalida qi = QIngresoSalida.ingresoSalida;
        listaRubros = service.from(qi).where(qi.semana.eq(modeloEdicion)).list(qi);
        
        for (Iterator<Cuenta> it = listaCuentas.iterator(); it.hasNext();) {
            QAporteSemanal qa = QAporteSemanal.aporteSemanal;
            QAporteBingo qb = QAporteBingo.aporteBingo;
            
            Cuenta cc = it.next();            
            ItemCuentaAportes itAp = new ItemCuentaAportes(cc);                                    
            
            for (Iterator<Semana> it1 = semanasAportes.iterator(); it1.hasNext();) {
                Semana s = it1.next();                
                
                //Aporte semanal
                AporteSemanal as = service.from(qa).where( qa.semana.eq(s), qa.cuenta.eq(cc)).singleResult(qa);
                if(as == null){
                    as = new AporteSemanal(s, modeloEdicion, cc);
                    itAp.getItems().add(new ItemAporteSemana(as, false));
                }else{
                    if(as.getSemanaRegistro().equals(modeloEdicion)){
                        itAp.getItems().add(new ItemAporteSemana(as, true));
                    }
                }
                
                //Aporte bing
                if(s.isBingo()){
                    AporteBingo ab = service.from(qb).where( qb.semana.eq(s), qb.cuenta.eq(cc)).singleResult(qb);
                    if(ab == null){
                        ab = new AporteBingo(s, modeloEdicion, cc);
                        itAp.getItemsBingo().add(new ItemAporteBingo(ab, false));
                    }else{
                        if(ab.getSemanaRegistro().equals(modeloEdicion)){
                            itAp.getItemsBingo().add(new ItemAporteBingo(ab, true));
                        }
                    }
                }
            }
                                                
            listaAportes.add(itAp);
        }
        
        return toRedirect("listaAportes.xhtml");
    }    
    
    public void eventoAddSemanas(ActionEvent e){
        if(!itemAporte.getItems().isEmpty()){
            ItemAporteSemana item = itemAporte.getItems().get(itemAporte.getItems().size() - 1);
            QSemana qs = QSemana.semana;
            List<Semana> lista = service.from(qs).where(qs.periodo.eq(modeloEdicion.getPeriodo()), qs.fecha.after(item.getAporte().getSemana().getFecha())).limit(diasAgregar).list(qs);
            for (Iterator<Semana> it = lista.iterator(); it.hasNext();) {
                Semana s = it.next();
                AporteSemanal as = new AporteSemanal(s, modeloEdicion, itemAporte.getCuenta());
                itemAporte.getItems().add(new ItemAporteSemana(as, false));
            }
        }
    }
    
    public void eventoAddRubro(ActionEvent e){
        rubro = new IngresoSalida(modeloEdicion, TipoIngresoSalida.Ingreso, 0);
        listaRubros.add(rubro);
    }
    
    public void eventoAddSemanasBingo(ActionEvent e){
        if(!itemAporte.getItemsBingo().isEmpty()){
            ItemAporteSemana item = itemAporte.getItems().get(itemAporte.getItems().size() - 1);
            QSemana qs = QSemana.semana;
            List<Semana> lista = service.from(qs).where(qs.periodo.eq(modeloEdicion.getPeriodo()), qs.fecha.after(item.getAporte().getSemana().getFecha()), qs.bingo.eq(true)).limit(diasAgregar).list(qs);
            for (Iterator<Semana> it = lista.iterator(); it.hasNext();) {
                Semana s = it.next();
                AporteBingo ab = new AporteBingo(s, modeloEdicion, itemAporte.getCuenta());
                itemAporte.getItemsBingo().add(new ItemAporteBingo(ab, false));
            }
        }
    }
    
    public void detalleAportes(ItemCuentaAportes item){
        itemAporte = item;
    }
    
    public void guardarAportes(){
        for (ItemAporteSemana item: itemAporte.getItems()){
            AporteSemanal as = item.getAporte();
            if(item.isChecked()){
                if(as.getFecha() == null){
                    as.setFecha(new Date());
                }                
                service.save(as);
            }else{  
                //Creo un nuevo registro
                item.setAporte(new AporteSemanal(as.getSemana(), modeloEdicion, as.getCuenta()));
                //Borro el registro anterior
                service.delete(as);                                
            }
        }
        
        for (ItemAporteBingo item: itemAporte.getItemsBingo()){
            AporteBingo as = item.getAporte();
            if(item.isChecked()){
                if(as.getFecha() == null){
                    as.setFecha(new Date());
                }                
                service.save(as);
            }else{  
                //Creo un nuevo registro
                item.setAporte(new AporteBingo(as.getSemana(), modeloEdicion, as.getCuenta()));
                //Borro el registro anterior
                service.delete(as);                                
            }
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
    public List<ItemCuentaAportes> getListaAportes() {
        return listaAportes;
    }

    /**
     * @param listaCuentas the listaAportes to set
     */
    public void setListaAportes(List<ItemCuentaAportes> listaAportes) {
        this.listaAportes = listaAportes;
    }     

    /**
     * @return the totalSemanasPeriodo
     */
    public long getTotalSemanasPeriodo() {
        return totalSemanasPeriodo;
    }

    /**
     * @param totalSemanasPeriodo the totalSemanasPeriodo to set
     */
    public void setTotalSemanasPeriodo(long totalSemanasPeriodo) {
        this.totalSemanasPeriodo = totalSemanasPeriodo;
    }

    /**
     * @return the totalSemanasAnteriores
     */
    public long getTotalSemanasAnteriores() {
        return totalSemanasAnteriores;
    }

    /**
     * @param totalSemanasAnteriores the totalSemanasAnteriores to set
     */
    public void setTotalSemanasAnteriores(long totalSemanasAnteriores) {
        this.totalSemanasAnteriores = totalSemanasAnteriores;
    }

    /**
     * @return the itemAporte
     */
    public ItemCuentaAportes getItemAporte() {
        return itemAporte;
    }

    /**
     * @param itemAporte the itemAporte to set
     */
    public void setItemAporte(ItemCuentaAportes itemAporte) {
        this.itemAporte = itemAporte;
    }

    /**
     * @return the diasAgregar
     */
    public int getDiasAgregar() {
        return diasAgregar;
    }

    /**
     * @param diasAgregar the diasAgregar to set
     */
    public void setDiasAgregar(int diasAgregar) {
        this.diasAgregar = diasAgregar;
    }

    /**
     * @return the listaRubros
     */
    public List<IngresoSalida> getListaRubros() {
        return listaRubros;
    }

    /**
     * @param listaRubros the listaRubros to set
     */
    public void setListaRubros(List<IngresoSalida> listaRubros) {
        this.listaRubros = listaRubros;
    }

    /**
     * @return the rubro
     */
    public IngresoSalida getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(IngresoSalida rubro) {
        this.rubro = rubro;
    }
    
}
