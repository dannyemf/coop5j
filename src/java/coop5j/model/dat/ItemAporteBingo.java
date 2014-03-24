/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.dat;

import coop5j.model.core.AporteBingo;
import java.io.Serializable;

/**
 *
 * @author uti
 */
public class ItemAporteBingo implements Serializable {

    private AporteBingo aporte;
    private boolean checked;

    public ItemAporteBingo() {
    }

    public ItemAporteBingo(AporteBingo aporte, boolean checked) {
        this.aporte = aporte;
        this.checked = checked;
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

    /**
     * @return the aporte
     */
    public AporteBingo getAporte() {
        return aporte;
    }

    /**
     * @param aporte the aporte to set
     */
    public void setAporte(AporteBingo aporte) {
        this.aporte = aporte;
    }
}
