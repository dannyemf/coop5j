/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.dat;

import coop5j.model.core.AporteSemanal;
import java.io.Serializable;

/**
 *
 * @author uti
 */
public class ItemAporteSemana implements Serializable{

    private AporteSemanal aporte;    
    private boolean checked;
    
    public ItemAporteSemana() {
    }

    public ItemAporteSemana(AporteSemanal aporte, boolean checked) {
        this.aporte = aporte;
        this.checked = checked;
    }        

    /**
     * @return the aporte
     */
    public AporteSemanal getAporte() {
        return aporte;
    }

    /**
     * @param aporte the aporte to set
     */
    public void setAporte(AporteSemanal aporte) {
        this.aporte = aporte;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
}
