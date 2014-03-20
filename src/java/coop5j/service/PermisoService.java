/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.service;

import com.mysema.query.jpa.impl.JPAQuery;
import coop5j.model.seguridad.Permiso;
import coop5j.model.seguridad.QGrupo;
import coop5j.model.seguridad.QPermiso;
import coop5j.model.seguridad.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;



/**
 *
 * @author uti
 */
@Stateless
public class PermisoService extends AbstractService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
        
    public List<Permiso> buscarPor(String textoBusqueda) {
        
        JPAQuery q = new JPAQuery(em);
        QPermiso p = QPermiso.permiso;
        
        return q.from(p).where(p.nombre.like(toLike(textoBusqueda)))
        .orderBy(p.nombre.asc())
        .list(p);
    }
    
    public List<Permiso> obtenerPermisos(Usuario usuario){
        
        
        QPermiso pe = QPermiso.permiso;
        QGrupo   gu = QGrupo.grupo;
        //QUsuario   us = QUsuario.usuario;
        
        JPAQuery q = new JPAQuery(em);
        
        //Grupo.permisos 
        
        List<Permiso> permisos =  new ArrayList<Permiso>();
                
        try {
            permisos = q.from(pe).leftJoin(pe.grupos, gu).where(gu.in(usuario.getGrupos())).list(pe);
        } catch (Exception e) {
            System.out.println(e);
        }                
        
        return permisos;
    }

}
