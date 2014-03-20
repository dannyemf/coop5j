/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller.seguridad;

import coop5j.controller.AbstractController;
import coop5j.controller.ContextBean;
import coop5j.model.seguridad.Menu;
import coop5j.service.GrupoService;
import coop5j.service.MenuService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 * Controlador de permisos
 * 
 * @author Danny Muñoz
 */

@ManagedBean
@ViewScoped
public class ListMenuController extends AbstractController{
    
    @EJB
    private MenuService service;
    
    @EJB
    private GrupoService groupService;
    
    @Inject
    private ContextBean context;
       
    
    /**
     * Listado de usuarios a mostrar en la vista
     */
    private List<Menu> listaDatos = new ArrayList<Menu>();        
    
    /**
     * Texto para filtrar los datos al presionar el botón buscar
     */
    private String textoBusqueda;

    public ListMenuController() {
    }
    
    @PostConstruct
    public void init(){        
    }
         
        
    
    /**
     * Evento invocada al presionar el botón buscar
     * @param evento 
     */
    public void eventoBuscar(ActionEvent event){                
        listaDatos = service.buscarPor(textoBusqueda);
    }        

    /**
     * @return the listaDatos
     */
    public List<Menu> getListaDatos() {
        return listaDatos;
    }

    /**
     * @param listaDatos the listaDatos to set
     */
    public void setListaDatos(List<Menu> listaDatos) {
        this.listaDatos = listaDatos;
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
           
}
