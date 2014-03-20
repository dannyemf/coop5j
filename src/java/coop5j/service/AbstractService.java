/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.service;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import coop5j.model.core.IEntity;
import java.lang.reflect.Method;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author uti
 */
public abstract class AbstractService {
    
    @PersistenceContext(name = "Coop5jPU")
    protected EntityManager em;    

    public void setEmf(EntityManager emf) {
        this.em = emf;
    }
    
    public JPAQuery from(EntityPath<?>... paths){
        return new JPAQuery(em).from(paths);
    }
    
    public List all(EntityPath<?> path){
        return new JPAQuery(em).from(path).list(path);
    }
    
    public <T extends IEntity> T getById(Class<? extends T> clase, Long id) {      
        Query q = em.createQuery("from " + clase.getSimpleName() + " where id = " + id);        
        return (T)q.getSingleResult();
    }
    
    public String toLike(Object stringOrObject){
        if(stringOrObject == null){
            return "%";
        }else{
            return "%" + stringOrObject + "%";
        }
    }
    
    public void save(IEntity entity){
        if(entity.getId() == null){
            em.persist(entity);
        }else{
            em.merge(entity);
        }
        
    }
    
    
    
    public boolean isUnique(Object entity, String property, Object value){
        Object id = getId(entity);
        
        Query q = em.createQuery("select count(*) from " + entity.getClass().getSimpleName() + " m where m.id != " + id + " and m."+property + "='"+value+"'");
        Long l = (Long)q.getSingleResult();
        
        if(l > 0){  
            return false;
        }
        
        return true;
    }
    
    private Object getId(Object entity){
        try {
            Method m = entity.getClass().getMethod("getId");
            return m.invoke(entity);
        } catch (Exception e) {
            throw new NotImplementedException();
        }
    }
    
    
    
}
