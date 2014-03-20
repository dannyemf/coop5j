/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.seguridad;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.dat.ItemGrupo;
import coop5j.model.dat.ItemIcon;
import coop5j.model.seguridad.Menu;
import coop5j.model.seguridad.QMenu;
import coop5j.service.GrupoService;
import coop5j.service.MenuService;
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
public class MenuController extends AbstractController{
    
    @EJB
    private MenuService service;
    
    @EJB
    private GrupoService groupService;
    
    @Inject
    private ContextBean context;
    
    @Inject
    private Conversation conversation;            
    
    private List<Menu> listaPadres = new ArrayList<Menu>();
    
    private ItemIcon selectedIcon;
    
    
    /**
     * Lista de grupos para permitir seleccionar al usuario mediante un check
     */
    private List<ItemGrupo> grupos = new ArrayList<ItemGrupo>();
    private List<ItemIcon> iconos;
    
    /**
     * Usuario en edición
     */
    private Menu modeloEdicion;

    public MenuController() {
    }        
           
    
    @PostConstruct
    public void init(){
        iconos = new ArrayList<ItemIcon>();
        iconos.add(new ItemIcon("ui-icon-carat-1-n"));
        iconos.add(new ItemIcon("ui-icon-carat-1-ne"));
        iconos.add(new ItemIcon("ui-icon-carat-1-e"));
        iconos.add(new ItemIcon("ui-icon-carat-1-se"));
        iconos.add(new ItemIcon("ui-icon-carat-1-s"));
        iconos.add(new ItemIcon("ui-icon-carat-1-sw"));
        iconos.add(new ItemIcon("ui-icon-carat-1-w"));
        iconos.add(new ItemIcon("ui-icon-carat-1-nw"));
        iconos.add(new ItemIcon("ui-icon-carat-2-n-s"));
        iconos.add(new ItemIcon("ui-icon-carat-2-e-w"));
        iconos.add(new ItemIcon("ui-icon-triangle-1-n"));
        iconos.add(new ItemIcon("ui-icon-triangle-1-ne"));
        iconos.add(new ItemIcon("ui-icon-triangle-1-e"));
        iconos.add(new ItemIcon("ui-icon-triangle-1-se"));
        iconos.add(new ItemIcon("ui-icon-triangle-1-s"));
        iconos.add(new ItemIcon("ui-icon-triangle-1-sw"));
        iconos.add(new ItemIcon("ui-icon-triangle-1-w"));
        iconos.add(new ItemIcon("ui-icon-triangle-1-nw"));
        iconos.add(new ItemIcon("ui-icon-triangle-2-n-s"));
        iconos.add(new ItemIcon("ui-icon-triangle-2-e-w"));
        iconos.add(new ItemIcon("ui-icon-arrow-1-n"));
        iconos.add(new ItemIcon("ui-icon-arrow-1-ne"));
        iconos.add(new ItemIcon("ui-icon-arrow-1-e"));
        iconos.add(new ItemIcon("ui-icon-arrow-1-se"));
        iconos.add(new ItemIcon("ui-icon-arrow-1-s"));
        iconos.add(new ItemIcon("ui-icon-arrow-1-sw"));
        iconos.add(new ItemIcon("ui-icon-arrow-1-w"));
        iconos.add(new ItemIcon("ui-icon-arrow-1-nw"));
        iconos.add(new ItemIcon("ui-icon-arrow-2-n-s"));
        iconos.add(new ItemIcon("ui-icon-arrow-2-ne-sw"));
        iconos.add(new ItemIcon("ui-icon-arrow-2-e-w"));
        iconos.add(new ItemIcon("ui-icon-arrow-2-se-nw"));
        iconos.add(new ItemIcon("ui-icon-arrowstop-1-n"));
        iconos.add(new ItemIcon("ui-icon-arrowstop-1-e"));
        iconos.add(new ItemIcon("ui-icon-arrowstop-1-s"));
        iconos.add(new ItemIcon("ui-icon-arrowstop-1-w"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-1-n"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-1-ne"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-1-e"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-1-se"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-1-s"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-1-sw"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-1-w"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-1-nw"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-2-n-s"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-2-ne-sw"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-2-e-w"));
        iconos.add(new ItemIcon("ui-icon-arrowthick-2-se-nw"));
        iconos.add(new ItemIcon("ui-icon-arrowthickstop-1-n"));
        iconos.add(new ItemIcon("ui-icon-arrowthickstop-1-e"));
        iconos.add(new ItemIcon("ui-icon-arrowthickstop-1-s"));
        iconos.add(new ItemIcon("ui-icon-arrowthickstop-1-w"));
        iconos.add(new ItemIcon("ui-icon-arrowreturnthick-1-w"));
        iconos.add(new ItemIcon("ui-icon-arrowreturnthick-1-n"));
        iconos.add(new ItemIcon("ui-icon-arrowreturnthick-1-e"));
        iconos.add(new ItemIcon("ui-icon-arrowreturnthick-1-s"));
        iconos.add(new ItemIcon("ui-icon-arrowreturn-1-w"));
        iconos.add(new ItemIcon("ui-icon-arrowreturn-1-n"));
        iconos.add(new ItemIcon("ui-icon-arrowreturn-1-e"));
        iconos.add(new ItemIcon("ui-icon-arrowreturn-1-s"));
        iconos.add(new ItemIcon("ui-icon-arrowrefresh-1-w"));
        iconos.add(new ItemIcon("ui-icon-arrowrefresh-1-n"));
        iconos.add(new ItemIcon("ui-icon-arrowrefresh-1-e"));
        iconos.add(new ItemIcon("ui-icon-arrowrefresh-1-s"));
        iconos.add(new ItemIcon("ui-icon-arrow-4"));
        iconos.add(new ItemIcon("ui-icon-arrow-4-diag"));
        iconos.add(new ItemIcon("ui-icon-extlink"));
        iconos.add(new ItemIcon("ui-icon-newwin"));
        iconos.add(new ItemIcon("ui-icon-refresh"));
        iconos.add(new ItemIcon("ui-icon-shuffle"));
        iconos.add(new ItemIcon("ui-icon-transfer-e-w"));
        iconos.add(new ItemIcon("ui-icon-transferthick-e-w"));
        iconos.add(new ItemIcon("ui-icon-folder-collapsed"));
        iconos.add(new ItemIcon("ui-icon-folder-open"));
        iconos.add(new ItemIcon("ui-icon-document"));
        iconos.add(new ItemIcon("ui-icon-document-b"));
        iconos.add(new ItemIcon("ui-icon-note"));
        iconos.add(new ItemIcon("ui-icon-mail-closed"));
        iconos.add(new ItemIcon("ui-icon-mail-open"));
        iconos.add(new ItemIcon("ui-icon-suitcase"));
        iconos.add(new ItemIcon("ui-icon-comment"));
        iconos.add(new ItemIcon("ui-icon-person"));
        iconos.add(new ItemIcon("ui-icon-print"));
        iconos.add(new ItemIcon("ui-icon-trash"));
        iconos.add(new ItemIcon("ui-icon-locked"));
        iconos.add(new ItemIcon("ui-icon-unlocked"));
        iconos.add(new ItemIcon("ui-icon-bookmark"));
        iconos.add(new ItemIcon("ui-icon-tag"));
        iconos.add(new ItemIcon("ui-icon-home"));
        iconos.add(new ItemIcon("ui-icon-flag"));
        iconos.add(new ItemIcon("ui-icon-calendar"));
        iconos.add(new ItemIcon("ui-icon-cart"));
        iconos.add(new ItemIcon("ui-icon-pencil"));
        iconos.add(new ItemIcon("ui-icon-clock"));
        iconos.add(new ItemIcon("ui-icon-disk"));
        iconos.add(new ItemIcon("ui-icon-calculator"));
        iconos.add(new ItemIcon("ui-icon-zoomin"));
        iconos.add(new ItemIcon("ui-icon-zoomout"));
        iconos.add(new ItemIcon("ui-icon-search"));
        iconos.add(new ItemIcon("ui-icon-wrench"));
        iconos.add(new ItemIcon("ui-icon-gear"));
        iconos.add(new ItemIcon("ui-icon-heart"));
        iconos.add(new ItemIcon("ui-icon-star"));
        iconos.add(new ItemIcon("ui-icon-link"));
        iconos.add(new ItemIcon("ui-icon-cancel"));
        iconos.add(new ItemIcon("ui-icon-plus"));
        iconos.add(new ItemIcon("ui-icon-plusthick"));
        iconos.add(new ItemIcon("ui-icon-minus"));
        iconos.add(new ItemIcon("ui-icon-minusthick"));
        iconos.add(new ItemIcon("ui-icon-close"));
        iconos.add(new ItemIcon("ui-icon-closethick"));
        iconos.add(new ItemIcon("ui-icon-key"));
        iconos.add(new ItemIcon("ui-icon-lightbulb"));
        iconos.add(new ItemIcon("ui-icon-scissors"));
        iconos.add(new ItemIcon("ui-icon-clipboard"));
        iconos.add(new ItemIcon("ui-icon-copy"));
        iconos.add(new ItemIcon("ui-icon-contact"));
        iconos.add(new ItemIcon("ui-icon-image"));
        iconos.add(new ItemIcon("ui-icon-video"));
        iconos.add(new ItemIcon("ui-icon-script"));
        iconos.add(new ItemIcon("ui-icon-alert"));
        iconos.add(new ItemIcon("ui-icon-info"));
        iconos.add(new ItemIcon("ui-icon-notice"));
        iconos.add(new ItemIcon("ui-icon-help"));
        iconos.add(new ItemIcon("ui-icon-check"));
        iconos.add(new ItemIcon("ui-icon-bullet"));
        iconos.add(new ItemIcon("ui-icon-radio-off"));
        iconos.add(new ItemIcon("ui-icon-radio-on"));
        iconos.add(new ItemIcon("ui-icon-pin-w"));
        iconos.add(new ItemIcon("ui-icon-pin-s"));
        iconos.add(new ItemIcon("ui-icon-play"));
        iconos.add(new ItemIcon("ui-icon-pause"));
        iconos.add(new ItemIcon("ui-icon-seek-next"));
        iconos.add(new ItemIcon("ui-icon-seek-prev"));
        iconos.add(new ItemIcon("ui-icon-seek-end"));
        iconos.add(new ItemIcon("ui-icon-seek-start"));
        iconos.add(new ItemIcon("ui-icon-seek-first"));
        iconos.add(new ItemIcon("ui-icon-stop"));
        iconos.add(new ItemIcon("ui-icon-eject"));
        iconos.add(new ItemIcon("ui-icon-volume-off"));
        iconos.add(new ItemIcon("ui-icon-volume-on"));
        iconos.add(new ItemIcon("ui-icon-power"));
        iconos.add(new ItemIcon("ui-icon-signal-diag"));
        iconos.add(new ItemIcon("ui-icon-signal"));
        iconos.add(new ItemIcon("ui-icon-battery-0"));
        iconos.add(new ItemIcon("ui-icon-battery-1"));
        iconos.add(new ItemIcon("ui-icon-battery-2"));
        iconos.add(new ItemIcon("ui-icon-battery-3"));
        iconos.add(new ItemIcon("ui-icon-circle-plus"));
        iconos.add(new ItemIcon("ui-icon-circle-minus"));
        iconos.add(new ItemIcon("ui-icon-circle-close"));
        iconos.add(new ItemIcon("ui-icon-circle-triangle-e"));
        iconos.add(new ItemIcon("ui-icon-circle-triangle-s"));
        iconos.add(new ItemIcon("ui-icon-circle-triangle-w"));
        iconos.add(new ItemIcon("ui-icon-circle-triangle-n"));
        iconos.add(new ItemIcon("ui-icon-circle-arrow-e"));
        iconos.add(new ItemIcon("ui-icon-circle-arrow-s"));
        iconos.add(new ItemIcon("ui-icon-circle-arrow-w"));
        iconos.add(new ItemIcon("ui-icon-circle-arrow-n"));
        iconos.add(new ItemIcon("ui-icon-circle-zoomin"));
        iconos.add(new ItemIcon("ui-icon-circle-zoomout"));
        iconos.add(new ItemIcon("ui-icon-circle-check"));
        iconos.add(new ItemIcon("ui-icon-circlesmall-plus"));
        iconos.add(new ItemIcon("ui-icon-circlesmall-minus"));
        iconos.add(new ItemIcon("ui-icon-circlesmall-close"));
        iconos.add(new ItemIcon("ui-icon-squaresmall-plus"));
        iconos.add(new ItemIcon("ui-icon-squaresmall-minus"));
        iconos.add(new ItemIcon("ui-icon-squaresmall-close"));
        iconos.add(new ItemIcon("ui-icon-grip-dotted-vertical"));
        iconos.add(new ItemIcon("ui-icon-grip-dotted-horizontal"));
        iconos.add(new ItemIcon("ui-icon-grip-solid-vertical"));
        iconos.add(new ItemIcon("ui-icon-grip-solid-horizontal"));
        iconos.add(new ItemIcon("ui-icon-gripsmall-diagonal-se"));
        iconos.add(new ItemIcon("ui-icon-grip-diagonal-se"));
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
        modeloEdicion = new Menu();
        
        listaPadres = service.all(QMenu.menu);
        
        grupos = groupService.obtenerGrupos(modeloEdicion);
        return "editar.xhtml?faces-redirect=true";
    }
    
    /**
     * Evento invocada al presionar el vínculo editar
     * @param item El usuario a editar
     * @return 
     */
    public String eventoEditar(Menu item){
        System.out.println("eventoEditar(): ");
        beginConversation();        
        modeloEdicion = item;
        
        selectedIcon = new ItemIcon(item.getIcono());
        
        listaPadres = service.all(QMenu.menu);
        listaPadres.remove(item);
        
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
        
        modeloEdicion.setIcono(selectedIcon == null ? "" : selectedIcon.getIcono());
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
    public Menu getModeloEdicion() {
        return modeloEdicion;
    }

    /**
     * @param modeloEdicion the modeloEdicion to set
     */
    public void setModeloEdicion(Menu modeloEdicion) {
        this.modeloEdicion = modeloEdicion;
    }   

    /**
     * @return the listaPadres
     */
    public List<Menu> getListaPadres() {
        return listaPadres;
    }

    /**
     * @param listaPadres the listaPadres to set
     */
    public void setListaPadres(List<Menu> listaPadres) {
        this.listaPadres = listaPadres;
    }

    /**
     * @return the iconos
     */
    public List<ItemIcon> getIconos() {
        return iconos;
    }

    /**
     * @param iconos the iconos to set
     */
    public void setIconos(List<ItemIcon> iconos) {
        this.iconos = iconos;
    }

    /**
     * @return the selectedIcon
     */
    public ItemIcon getSelectedIcon() {
        return selectedIcon;
    }

    /**
     * @param selectedIcon the selectedIcon to set
     */
    public void setSelectedIcon(ItemIcon selectedIcon) {
        this.selectedIcon = selectedIcon;
    }
           
}
