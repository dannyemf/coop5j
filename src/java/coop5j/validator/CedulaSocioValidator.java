/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.validator;


import coop5j.model.core.Socio;
import coop5j.service.SocioService;
import coop5j.util.CedulaUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Validador Java Server Faces para el valor ingresado de un parámtro según sea el tipo de dato del mismo.
 * 
 * @author Noralma Vera
 * @author Doris Viñamagua
 * @version 1.0
 */

@FacesValidator(value="cedulaSocioValidator")
public class CedulaSocioValidator implements Validator{
    
    private SocioService servive = lookupServiceBean();
    
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException{                                       
        
       
        UIComponent c = (UIComponent)component.getAttributes().get("attr1");        
        UIComponent h = (UIComponent)c.getAttributes().get("binding");
        
        Long idSocio = (Long)component.getAttributes().get("attr2");                                
        boolean cedulado = false;
        
        if(cedulado){
            String valor = (String)value;
            boolean v = CedulaUtil.validar(valor);
            String mensaje = null;
            
            if(v){                
                if (! servive.isUnique(new Socio(idSocio), "cedula", valor)){
                    mensaje = "Ya se ha registrado un socio con este número de cédula";
                }
            }else{
                mensaje = "Cédula incorrecta";
            }
            
            if(mensaje != null){
                String label = (String)component.getAttributes().get("label");
                if(label == null){
                    label = component.getClientId();
                }

                FacesMessage fm = new FacesMessage(label + ": " + mensaje);
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);            
                throw  new ValidatorException(fm);
            }
        }                                
    }
    
    private SocioService lookupServiceBean() {
        try {
            Context c = new InitialContext();
            return (SocioService) c.lookup("java:global/Coop5j/SocioService!coop5j.service.SocioService");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
