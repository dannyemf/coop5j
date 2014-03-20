/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.dat;

import coop5j.model.seguridad.Grupo;
import java.io.Serializable;

/**
 *
 * @author uti
 */
public class ItemGrupo implements Serializable{
    
    private boolean checked;
    private Grupo grupo;

    public ItemGrupo() {
    }

    public ItemGrupo(boolean checked, Grupo grupo) {
        this.checked = checked;
        this.grupo = grupo;
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
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    
}
