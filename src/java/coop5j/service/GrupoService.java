/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.service;

import com.mysema.query.jpa.impl.JPAQuery;
import coop5j.model.dat.ItemGrupo;
import coop5j.model.seguridad.Grupo;
import coop5j.model.seguridad.Menu;
import coop5j.model.seguridad.Permiso;
import coop5j.model.seguridad.QGrupo;
import coop5j.model.seguridad.Usuario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;


/**
 *
 * @author uti
 */
@Stateless
public class GrupoService extends AbstractService  {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
        
    public List<Grupo> buscarPor(String textoBusqueda){
        
        QGrupo g = QGrupo.grupo;
        JPAQuery q = from(g);
        
        return
                
        q.where(
                g.nombre.like(toLike(textoBusqueda))
                .or(g.descripcion.like(toLike(textoBusqueda)))
        )
        .orderBy(g.nombre.asc())
        .list(g);
        
    }
                
    public List<ItemGrupo> obtenerGrupos(Usuario usuario){
        
        
        List<ItemGrupo> lst = new ArrayList<ItemGrupo>();
        
        List<Grupo> grupos = all(QGrupo.grupo);
        
        for (Iterator<Grupo> it = grupos.iterator(); it.hasNext();) {
            Grupo g = it.next();
            if(usuario.getGrupos().contains(g)){
                lst.add(new ItemGrupo(true, g));
            }else{
                lst.add(new ItemGrupo(false, g));
            }
        }
        
        return lst;
    }
        
    public List<ItemGrupo> obtenerGrupos(Permiso permiso){
        
        
        List<ItemGrupo> lst = new ArrayList<ItemGrupo>();
        
        List<Grupo> grupos = all(QGrupo.grupo);
        
        for (Iterator<Grupo> it = grupos.iterator(); it.hasNext();) {
            Grupo g = it.next();
            if(permiso.getGrupos().contains(g)){
                lst.add(new ItemGrupo(true, g));
            }else{
                lst.add(new ItemGrupo(false, g));
            }
        }
        
        return lst;
    }
    
    
    public List<ItemGrupo> obtenerGrupos(Menu menu){
        
        
        List<ItemGrupo> lst = new ArrayList<ItemGrupo>();
        
        List<Grupo> grupos = all(QGrupo.grupo);
        
        for (Iterator<Grupo> it = grupos.iterator(); it.hasNext();) {
            Grupo g = it.next();
            if(menu.getGrupos().contains(g)){
                lst.add(new ItemGrupo(true, g));
            }else{
                lst.add(new ItemGrupo(false, g));
            }
        }
        
        return lst;
    }
}
