/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.service;

import com.mysema.query.jpa.impl.JPAQuery;
import coop5j.model.core.Periodo;
import coop5j.model.core.QPeriodo;
import coop5j.model.core.QSocio;
import coop5j.model.core.Socio;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author uti
 */
@Stateless 
public class PeriodoService extends AbstractService{        
    
    public List<Periodo> buscarPor(String textoBusqueda) {
                
        QPeriodo p = QPeriodo.periodo;
        
        return from(p).where(
                p.nombre.like(toLike(textoBusqueda)))
        .orderBy(p.fechaInicio.desc())
        .list(p);
    }
            
}
