/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller;

import coop5j.model.seguridad.Menu;
import coop5j.model.seguridad.Usuario;
import coop5j.service.MenuService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;



/**
 *
 * @author Danny Muñoz
 */

@Named
@SessionScoped
public class MenuBean extends AbstractController{
    
    @EJB
    private MenuService service;
    
    @Inject
    private ContextBean context;
    
    private MenuModel menuModel = new DefaultMenuModel();
    
    
    public void init(Usuario us){
        
        menuModel = new DefaultMenuModel();
        
        Usuario usuario = context.getUsuario();
        
        List<Menu> menus =  usuario != null ? service.obtenerMenus(usuario) : new ArrayList<Menu>();
        
        for (Menu m : menus) {            
            Submenu mi = new DefaultSubMenu(m.getEtiqueta(), m.getIcono());            
            menuModel.addElement(mi);            
            this.crearMenu(usuario, mi, m);
        }
                
        
    }
    
    private void crearMenu(Usuario usuario, Submenu mi, Menu menu){
        
        List<Menu> menus =  service.obtenerMenus(usuario, menu);
        
        for (Menu m : menus) {
            
            
            if(m.getUrl()!= null && m.getUrl().trim().equals("") == false){
                DefaultMenuItem mii = new DefaultMenuItem(m.getEtiqueta(), m.getIcono(), null);                
                mii.setOutcome(m.getUrl());
                //ELContext el = FacesContext.getCurrentInstance().getELContext();
                //ExpressionFactory ef = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
                //mii.setActionExpression(ef.createMethodExpression(el, m.getUrl()+"?faces-redirect=true", null, new Class[0]));                
                
                
                mi.getElements().add(mii);
            }else{
                Submenu mii = new DefaultSubMenu(m.getEtiqueta(), m.getIcono());                
                mi.getElements().add(mii);
                crearMenu(usuario, mii, m);
            }
        }
    }

    /**
     * @return the menuModel
     */
    public MenuModel getMenuModel() {
        return menuModel;
    }

    /**
     * @param menuModel the menuModel to set
     */
    public void setMenuModel(DefaultMenuModel menuModel) {
        this.menuModel = menuModel;
    }
    
}
