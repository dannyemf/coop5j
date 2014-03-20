/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.service;

import com.mysema.query.jpa.impl.JPAQuery;
import coop5j.model.core.QSocio;
import coop5j.model.core.Socio;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author uti
 */
@Stateless 
public class SocioService extends AbstractService{        
    
    public List<Socio> buscarPor(String textoBusqueda) {
        
        JPAQuery q = new JPAQuery(em);
        QSocio us = QSocio.socio;
        
        return q.from(us).where(
                us.nombres.like(toLike(textoBusqueda))
                .or(us.apellidos.like(toLike(textoBusqueda))))
        .orderBy(us.apellidos.asc(), us.nombres.asc())
        .list(us);
    }
            
}
