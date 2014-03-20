/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller;

import coop5j.model.seguridad.Permiso;
import coop5j.model.seguridad.Usuario;
import coop5j.service.PermisoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author uti
 */

@Named
@SessionScoped
public class ContextBean implements Serializable{
    
    private Usuario usuario;    
    private List<Permiso> permisos = new ArrayList<Permiso>();        
    
    @EJB
    PermisoService servicePermiso;            
    
    public void init(Usuario usuario){
        this.usuario = usuario;        
        this.permisos = servicePermiso.obtenerPermisos(usuario);        
    }
    
    public boolean checkPermiso(String permiso){
        if(hasPermiso(permiso)){
            return true;
        }        
        return false;
    }
    
    public boolean checkDisabled(String permiso){
        if(hasPermiso(permiso)){
            return false;
        }        
        return true;
    }
    
    public boolean hasPermiso(String permiso){
        
        for (Iterator<Permiso> it = permisos.iterator(); it.hasNext();) {
            Permiso p = it.next();
            if(p.getNombre().equals(permiso)){
                return true;
            }
        }
        
        return false;
    }
    
    public void checkPagePermiso(String permiso){                                    
                  
        if(!hasPermiso(permiso)){
            System.out.println("No contiene el permiso: " + permiso);
            FacesContext fc = FacesContext.getCurrentInstance();
            try {            
                fc.getExternalContext().redirect(fc.getExternalContext().getRequestContextPath() + "/nopermiso.jsf");
            } catch (Exception e) {
            }
        }
        
    }        
    
    public boolean isLogedIn(){
        return usuario != null;
    }
    
    public boolean isNotLogedIn(){
        return usuario == null;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
