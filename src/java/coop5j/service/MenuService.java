/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.service;

import com.mysema.query.jpa.impl.JPAQuery;
import coop5j.model.seguridad.Menu;
import coop5j.model.seguridad.QGrupo;
import coop5j.model.seguridad.QMenu;
import coop5j.model.seguridad.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;


/**
 *
 * @author uti
 */
@Stateless
public class MenuService extends AbstractService{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
        
    public List<Menu> obtenerMenus(Usuario usuario){
        
        
        QMenu me = QMenu.menu;
        QGrupo   gr = QGrupo.grupo;        
        
        JPAQuery q = new JPAQuery(em);        
        
        List<Menu> menus =  new ArrayList<Menu>();
                
        try {
            menus = q.from(me).leftJoin(me.grupos, gr)
                    .where(gr.in(usuario.getGrupos()).and(me.padre.isNull()))
                    .orderBy(me.orden.asc()).list(me);
        } catch (Exception e) {
            System.out.println(e);
        }                
        
        return menus;
    }
        
    public List<Menu> obtenerMenus(Usuario usuario, Menu padre){
        
        
        QMenu me = QMenu.menu;
        QGrupo   gr = QGrupo.grupo;        
        
        JPAQuery q = new JPAQuery(em);        
        
        List<Menu> menus =  new ArrayList<Menu>();
                
        try {
            menus = q.from(me).leftJoin(me.grupos, gr)
                    .where(gr.in(usuario.getGrupos()).and(me.padre.eq(padre)))
                    .orderBy(me.orden.asc()).list(me);
        } catch (Exception e) {
            System.out.println(e);
        }                
        
        return menus;
    }
        
    public List<Menu> buscarPor(String textoBusqueda) {
        
        JPAQuery q = new JPAQuery(em);
        QMenu m = QMenu.menu;        
        
        return q.from(m)
                .where(m.etiqueta.like(toLike(textoBusqueda)).or(m.url.like(toLike(textoBusqueda))))
                .orderBy(m.orden.asc())
        .list(m);
    }

}
