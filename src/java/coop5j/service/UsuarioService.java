/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.service;

import com.mysema.query.jpa.impl.JPAQuery;
import coop5j.model.seguridad.QUsuario;
import coop5j.model.seguridad.Usuario;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author uti
 */
@Stateless 
public class UsuarioService extends AbstractService{

    
            
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public String saludar(){
        
        return "Número de usuarios: " + from(QUsuario.usuario).count();
    }
    
    public Usuario validar(String username, String password){
        QUsuario us = QUsuario.usuario;
        return from(us).where(us.userName.eq(username), us.password.eq(password)).singleResult(us);
    }
    
    public List<Usuario> buscarPor(String textoBusqueda) {
        
        JPAQuery q = new JPAQuery(em);
        QUsuario us = QUsuario.usuario;
        
        return q.from(us).where(
                us.userName.like(toLike(textoBusqueda))
                .or(us.descripcion.like(toLike(textoBusqueda))))
        .orderBy(us.descripcion.asc())
        .list(us);
    }
            
}
