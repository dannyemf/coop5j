/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.converter;

import coop5j.model.core.QSocio;
import coop5j.model.core.Socio;
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
@FacesConverter(value = "socioConverter")
public class SocioConverter implements Converter{
    
    @EJB
    SocioService service;   
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Socio m = null;
        
        try {
            Long id = Long.parseLong(value);
            QSocio mn = QSocio.socio;
            m = service.from(mn).where(mn.id.eq(id)).singleResult(mn);
        } catch (Exception e) {}
        
        return m;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            Socio m = (Socio)value;
            return m.getId().toString();
        } catch (Exception e) {
            return "";
        }
    }    
    
}
