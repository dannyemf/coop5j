/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author uti
 */
@Entity
@Table(name = "cop_aporte_semana")
public class AporteSemanal implements Serializable, IEntity{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @ManyToOne(optional = false)
    private Semana semana;
    
    @ManyToOne(optional = false)
    private Semana semanaRegistro;
    
    @ManyToOne(optional = false)
    private Cuenta cuenta;
    
    @Column(nullable = false)
    private boolean estado;

    public AporteSemanal() {
    }

    public AporteSemanal(Semana semana, Semana registro, Cuenta cuenta) {        
        this.semana = semana;
        this.semanaRegistro = registro;
        this.cuenta = cuenta;
    }
    
    

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
        if (!(object instanceof AporteSemanal)) {
            return false;
        }
        AporteSemanal other = (AporteSemanal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coop5j.model.core.AporteSemanal[ id=" + id + " ]";
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the semana
     */
    public Semana getSemana() {
        return semana;
    }

    /**
     * @param semana the semana to set
     */
    public void setSemana(Semana semana) {
        this.semana = semana;
    }

    /**
     * @return the cuenta
     */
    public Cuenta getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the semanaRegistro
     */
    public Semana getSemanaRegistro() {
        return semanaRegistro;
    }

    /**
     * @param semanaRegistro the semanaRegistro to set
     */
    public void setSemanaRegistro(Semana semanaRegistro) {
        this.semanaRegistro = semanaRegistro;
    }
    
}
