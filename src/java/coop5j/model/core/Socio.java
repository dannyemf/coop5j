/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.core;

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
@Table(name = "cop_socio")
public class Socio implements Serializable, IEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private boolean cedulado;    
    
    @Column(length = 10, nullable = false)
    private String cedula;        
    
    @Column(length = 80, nullable = false)
    private String nombres;
    
    @Column(length = 80, nullable = false)
    private String apellidos;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Socio representante;

    public Socio() {
        this.fechaRegistro = new Date();
        this.cedulado = true;
    }     
    
    public Socio(Long id) {
        this.id = id;
        this.fechaRegistro = new Date();
        this.cedulado = true;
    }     
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDisplayText(){        
        return "Código: " + id + ", Cédula: "+cedula+", Nombres: " + apellidos + " " + nombres;
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
        if (!(object instanceof Socio)) {
            return false;
        }
        Socio other = (Socio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coop5j.model.core.Socio[ id=" + id + " ]";
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    /**
     * @return the cedulado
     */
    public boolean isCedulado() {
        return cedulado;
    }

    /**
     * @param cedulado the cedulado to set
     */
    public void setCedulado(boolean cedulado) {
        this.cedulado = cedulado;
    }

    /**
     * @return the representante
     */
    public Socio getRepresentante() {
        return representante;
    }

    /**
     * @param representante the representante to set
     */
    public void setRepresentante(Socio representante) {
        this.representante = representante;
    }
    
}
