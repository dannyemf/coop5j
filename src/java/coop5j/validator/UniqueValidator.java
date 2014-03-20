/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.validator;

import coop5j.service.GrupoService;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

/**
 * Validador de entidades con propiedades únicas. Por ejemplo en usuario el
 * login, en un producto el código.
 *
 * La validacion se hace importando el tag:
 * xmlns:s="http://sgps/jsf-custom-components/"
 *
 *
 * @author Danny Muñoz
 */
@Named("uniqueValidator")
@FacesValidator("uniqueValidator")
public class UniqueValidator implements Validator {
    
    @EJB
    private GrupoService service;            

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object entity = component.getNamingContainer().getAttributes().get("uniqueValidatorEntity");
        String property = (String)component.getNamingContainer().getAttributes().get("uniqueValidatorProperty");
        String message =(String)component.getNamingContainer().getAttributes().get("uniqueValidatorMessage");
        //System.out.println("Propiedad a validar: " + entity.getClass() + "." + property);
        if(!service.isUnique(entity, property, value)){            
            FacesMessage fm = new FacesMessage(message != null ? message : "Ya existen un registro con el mismo(a) ");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(fm);
        }
    }
    
    
}
