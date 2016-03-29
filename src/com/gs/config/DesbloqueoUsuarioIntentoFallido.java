/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author SIGSA
 */
public class DesbloqueoUsuarioIntentoFallido extends Thread{

    private String nombreUsuario;
    private ArrayList listUsersBlocked;
    private int tiempoDesloqueo;
    
    public DesbloqueoUsuarioIntentoFallido(){
    }

    public ArrayList getListUsersBlocked() {
        return listUsersBlocked;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getNombreUsuario(String nombreUsuario){
        return this.nombreUsuario;
    }
    
    public void setListUsersBlocked(ArrayList listUsersBlocked) {
        this.listUsersBlocked = listUsersBlocked;
    }

    public int getTiempoDesloqueo() {
        return tiempoDesloqueo;
    }

    public void setTiempoDesloqueo(int tiempoDesloqueo) {
        this.tiempoDesloqueo = tiempoDesloqueo;
    }
    
    public DesbloqueoUsuarioIntentoFallido(String nombreUsuario, ArrayList listUsersBlocked, int tiempoDesloqueo){
        this.nombreUsuario = nombreUsuario;
        this.listUsersBlocked = listUsersBlocked;
        this.tiempoDesloqueo = tiempoDesloqueo;
    }
    
    public String getNombreUsuario(){
        return this.nombreUsuario;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(tiempoDesloqueo);
            synchronized(listUsersBlocked) {
                Iterator<String> iterator = listUsersBlocked.iterator();
                while ( iterator.hasNext()) {
                    if (iterator.next().equalsIgnoreCase(nombreUsuario)) {
//                        System.out.println("SE HA DESBLOQUEADO EL USUARIO " + nombreUsuario);
                        iterator.remove();
                        break;
                    }
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DesbloqueoUsuarioIntentoFallido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
