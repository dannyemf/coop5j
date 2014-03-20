/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.converter;

import coop5j.model.core.QSocio;
import coop5j.model.core.Socio;
import coop5j.model.dat.ItemIcon;
import coop5j.service.SocioService;
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
@FacesConverter(value = "itemIconoConverter")
public class ItemIconoConverter implements Converter{         
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Socio m = null;        
        try {
            return new ItemIcon(value);
        } catch (Exception e) {}
        
        return m;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            ItemIcon m = (ItemIcon)value;
            return m.getIcono();
        } catch (Exception e) {
            return "";
        }
    }    
    
}
