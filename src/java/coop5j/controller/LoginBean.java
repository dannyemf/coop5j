/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller;

import coop5j.model.seguridad.Usuario;
import coop5j.service.UsuarioService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author uti
 */

@Named
@RequestScoped
public class LoginBean {
    
    private String usename;
    private String password;
    
    @EJB
    private UsuarioService srv;              
    
    @Inject
    private ContextBean userBean;
    
    @Inject
    private MenuBean menuBean;
    
    public String login(){
        Usuario us = srv.validar(usename, password);
        
        if (us != null){
            userBean.init(us); 
            menuBean.init(us);
            return "app/index.xhtml?faces-redirect=true";
        }
        
        return null;
        
    }
    
    public String logoff(){                
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        
        return "/login.xhtml?faces-redirect=true";
    }

    /**
     * @return the usename
     */
    public String getUsename() {
        return usename;
    }

    /**
     * @param usename the usename to set
     */
    public void setUsename(String usename) {
        this.usename = usename;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param srv the srv to set
     */
    public void setSrv(UsuarioService srv) {
        this.srv = srv;
    }

    /**
     * @param userBean the userBean to set
     */
    public void setUserBean(ContextBean userBean) {
        this.userBean = userBean;
    }
    
            
}
