/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author SIGSA
 */
public class ListUsersLockoutIntentFail {
    
    @Autowired
    private ArrayList listUsersBlocked;

    public ListUsersLockoutIntentFail(){}

    public ArrayList getListUsersBlocked() {
        return listUsersBlocked;
    }

    public void setListUsersBlocked(ArrayList listUsersBlocked) {
        this.listUsersBlocked = listUsersBlocked;
    }

    public List getListaHilosDesbloqueoUsuarioIntentoFallido() {
        return this.listUsersBlocked;
    }
    /**
     * Se agrega un usuario para que ser bloqueado
     * @param nombreUsuario Nombre del usuario
     * @param tiempoLockout Tiempo de bloqueo
     */
    public void addBlockUserFail(String nombreUsuario, int tiempoLockout){
        if(! findUserBlockout(nombreUsuario)){
            //Agrego al usuario a la lista de usuarios bloqueados y se aumenta el número de intentos
            addUsuarioBlock(nombreUsuario, tiempoLockout);
        }
        else{
//            System.out.println("YA HABÍA UN HILO ESPERANDO DESBLOQUEAR A "+nombreUsuario);
        }
    }
    
    private void addUsuarioBlock(String nombreUsuario, int tiempoLockout){
        //Se bloquea al usuario
        this.listUsersBlocked.add(nombreUsuario);
        //Creo e inicio el hilo para desbloquear al usuario
        new DesbloqueoUsuarioIntentoFallido(nombreUsuario, listUsersBlocked, tiempoLockout).start();
    }
    
    public boolean findUserBlockout(String nombre){
        for (Iterator iterator = listUsersBlocked.iterator(); iterator.hasNext();) {
            if(iterator.next().equals(nombre)){
                return true;
            }
        }
        return false;
    }
    
    public void removeUserFromListLockout(String username){
        for (Iterator iterator = listUsersBlocked.iterator(); iterator.hasNext();) {
            if(iterator.next().equals(username)){
                iterator.remove();
                break;
            }
        }
    }
}
