/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.converter;

import coop5j.model.seguridad.Menu;
import coop5j.model.seguridad.QMenu;
import coop5j.service.MenuService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author uti
 */

@Named
@FacesConverter(value = "menuConverter")
public class MenuConverter implements Converter{
    
    @EJB
    MenuService menuService;

    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Menu m = null;
        
        try {
            Long id = Long.parseLong(value);
            QMenu mn = QMenu.menu;
            m = menuService.from(mn).where(mn.id.eq(id)).singleResult(mn);
        } catch (Exception e) {}
        
        return m;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            Menu m = (Menu)value;
            return m.getId().toString();
        } catch (Exception e) {
            return "";
        }
    }    
    
}
