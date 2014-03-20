/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.seguridad;

import coop5j.model.core.IEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author uti
 */
@Entity
@Table(name = "seg_menu")
public class Menu implements Serializable, IEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 45, nullable = false)
    private String etiqueta;        
    
    @Column(length = 45)
    private String icono;
    
    @Column(nullable = false)
    private double orden;
    
    @Column(length = 80)
    private String url;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Menu padre;
    
    @ManyToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "seg_grupos_menu", joinColumns = { @JoinColumn(name = "id_menu") }, inverseJoinColumns = { @JoinColumn(name = "id_grupo") })
    private Set<Grupo> grupos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coop5j.model.seguridad.Menu[ id=" + id + " ]";
    }

    /**
     * @return the etiqueta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * @return the icono
     */
    public String getIcono() {
        return icono;
    }

    /**
     * @param icono the icono to set
     */
    public void setIcono(String icono) {
        this.icono = icono;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the padre
     */
    public Menu getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Menu padre) {
        this.padre = padre;
    }

    /**
     * @return the grupos
     */
    public Set<Grupo> getGrupos() {
        return grupos;
    }

    /**
     * @param grupos the grupos to set
     */
    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

    /**
     * @return the orden
     */
    public double getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(double orden) {
        this.orden = orden;
    }
    
}
