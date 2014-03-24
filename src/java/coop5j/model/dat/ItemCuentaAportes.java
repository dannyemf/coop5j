/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.model.dat;

import coop5j.model.core.Cuenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author uti
 */
public class ItemCuentaAportes implements Serializable{

    private Cuenta cuenta;
    private List<ItemAporteSemana> items = new ArrayList<>();
    private List<ItemAporteBingo> itemsBingo = new ArrayList<>();
    
    private long totalSemanasAportadas;

    public ItemCuentaAportes() {
    }         

    public ItemCuentaAportes(Cuenta cuenta) {
        this.cuenta = cuenta;
    } 
    
    public int calcularSemanas(){
        int total = 0;
        for (Iterator<ItemAporteSemana> it = items.iterator(); it.hasNext();) {
            ItemAporteSemana item = it.next();
            if(item.isChecked()){
                total += 1;
            }
        }
        return total;
    }
    
    public int calcularSemanasBingo(){
        int total = 0;
        for (Iterator<ItemAporteBingo> it = itemsBingo.iterator(); it.hasNext();) {
            ItemAporteBingo item = it.next();
            if(item.isChecked()){
                total += 1;
            }
        }
        return total;
    }
    
    public double calcularTotalBingo(){
        double total = 0;
        for (Iterator<ItemAporteBingo> it = itemsBingo.iterator(); it.hasNext();) {
            ItemAporteBingo item = it.next();
            if(item.isChecked()){
                total += cuenta.getPeriodo().getValorBingo();
            }
        }
        return total;
    }
    
    public double calcularTotalSemanas(){
        double total = 0;
        for (Iterator<ItemAporteSemana> it = items.iterator(); it.hasNext();) {
            ItemAporteSemana item = it.next();
            if(item.isChecked()){
                total += cuenta.getTotalAporteSemanal();
            }
        }
        return total;
    }
    
    public double calcularTotal(){
        double total = calcularTotalSemanas() + calcularTotalBingo();
        return total;
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
     * @return the items
     */
    public List<ItemAporteSemana> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<ItemAporteSemana> items) {
        this.items = items;
    }

    /**
     * @return the totalSemanasAportadas
     */
    public long getTotalSemanasAportadas() {
        return totalSemanasAportadas;
    }

    /**
     * @param totalSemanasAportadas the totalSemanasAportadas to set
     */
    public void setTotalSemanasAportadas(long totalSemanasAportadas) {
        this.totalSemanasAportadas = totalSemanasAportadas;
    }

    /**
     * @return the itemsBingo
     */
    public List<ItemAporteBingo> getItemsBingo() {
        return itemsBingo;
    }

    /**
     * @param itemsBingo the itemsBingo to set
     */
    public void setItemsBingo(List<ItemAporteBingo> itemsBingo) {
        this.itemsBingo = itemsBingo;
    }
    
}
