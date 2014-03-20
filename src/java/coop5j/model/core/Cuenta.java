/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.core;

import coop5j.model.seguridad.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "cop_cuenta")
public class Cuenta implements Serializable, IEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Periodo periodo;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Socio socio;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    
    @Column(nullable = false)
    private int acciones;    

    public Cuenta() {
        this.fechaRegistro = new Date();
    }
    
    public Cuenta(Periodo periodo, Socio socio, int acciones) {        
        this.periodo = periodo;
        this.socio = socio;
        this.acciones = acciones;
        this.fechaRegistro = new Date();
    }
    
    public double getTotalAporteSemanal(){
        return this.acciones * this.periodo.getValorAccion();
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
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coop5j.model.core.Cuenta[ id=" + id + " ]";
    }

    /**
     * @return the acciones
     */
    public int getAcciones() {
        return acciones;
    }

    /**
     * @param acciones the acciones to set
     */
    public void setAcciones(int acciones) {
        this.acciones = acciones;
    }

    /**
     * @return the periodo
     */
    public Periodo getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the socio
     */
    public Socio getSocio() {
        return socio;
    }

    /**
     * @param socio the socio to set
     */
    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
}
