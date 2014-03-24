/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author uti
 */
@Entity
@Table(name = "cop_ingreso_salida")
public class IngresoSalida implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(optional = false)
    private Semana semana;
    
    @Enumerated(EnumType.STRING)
    private TipoIngresoSalida tipo;
    
    @Column(nullable = false)
    private double monto;

    public IngresoSalida() {
    }

    public IngresoSalida(Semana semana, TipoIngresoSalida tipo, double monto) {        
        this.semana = semana;
        this.tipo = tipo;
        this.monto = monto;
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
        if (!(object instanceof IngresoSalida)) {
            return false;
        }
        IngresoSalida other = (IngresoSalida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coop5j.model.core.IngresoSalida[ id=" + id + " ]";
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
     * @return the tipo
     */
    public TipoIngresoSalida getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoIngresoSalida tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
}
