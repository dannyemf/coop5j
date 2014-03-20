/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.dat;

import java.io.Serializable;

/**
 *
 * @author uti
 */
public class ItemIcon implements Serializable{
    
    private String icono;

    public ItemIcon() {
    }        

    public ItemIcon(String icono) {
        this.icono = icono;
    }

    public String getIcono() {
        if (icono == null){
            icono = "";
        }
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        
        if (! (obj instanceof ItemIcon)){
            return false;
        }
        
        ItemIcon ot = (ItemIcon)obj;
        return ot.getIcono().equals(this.getIcono());
        
    }
    
    
    
    
    
}
