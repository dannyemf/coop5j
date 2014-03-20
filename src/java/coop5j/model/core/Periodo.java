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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author uti
 */
@Entity
@Table(name = "cop_periodo")
public class Periodo implements Serializable, IEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 80, nullable = false, unique = true)
    private String nombre;
    
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    @Column(nullable = false)
    private double valorAccion;
    
    @Column(nullable = false)
    private double valorInteres;

    public Periodo() {
        this.fechaInicio = new Date();
        this.fechaFin = new Date();        
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
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coop5j.model.core.Periodo[ id=" + id + " ]";
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the valorAccion
     */
    public double getValorAccion() {
        return valorAccion;
    }

    /**
     * @param valorAccion the valorAccion to set
     */
    public void setValorAccion(double valorAccion) {
        this.valorAccion = valorAccion;
    }

    /**
     * @return the interesPrestamos
     */
    public double getValorInteres() {
        return valorInteres;
    }

    /**
     * @param interesPrestamos the interesPrestamos to set
     */
    public void setValorInteres(double interesPrestamos) {
        this.valorInteres = interesPrestamos;
    }
    
}
