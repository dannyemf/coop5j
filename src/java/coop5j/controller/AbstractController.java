/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.controller;

import java.io.Serializable;

/**
 *
 * @author uti
 */
public abstract class AbstractController implements Serializable{
    
    public String toRedirect(String url){
        return url + "?faces-redirect=true";
    }
    
}
