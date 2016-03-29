/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author SIGSA
 */
public class UsuarioConIntentoFallido {
    private final List listaUsuariosIntentoFallido;
    
    public UsuarioConIntentoFallido(){
        listaUsuariosIntentoFallido = new ArrayList();
    }
    
    public void addIntentoUsuario(String nombre){
        if(findUsuario(nombre)){//agrego intento
            addIntentoUsuarioNombre(nombre);
        }
        else{//creo el nuevo usuario y lo agrego
            addUsuario(new IntentoFallido(nombre));
        }
    }
    /**
     * Se agrega un nuevo objeto IntentoFallido a la lista
     * @param nuevoUsuario Intento fallido que identifica al usuario
     */
    private void addUsuario(IntentoFallido nuevoUsuario){
        listaUsuariosIntentoFallido.add(nuevoUsuario);
    }
    
    private void addIntentoUsuarioNombre(String nombre){
        for (Iterator iterator = listaUsuariosIntentoFallido.iterator(); iterator.hasNext();) {
            IntentoFallido next = (IntentoFallido) iterator.next();
            if(next.getUsuario().equalsIgnoreCase(nombre)){
                next.incrementaIntento();
                break;
            }
        }
    }
    /**
     * Se obtienen los intentos hechos por un nombre de usuario
     * @param nombre Nombre del usuario
     * @return Número de intentos hechos por el usuario
     */
    public int getIntentosUsuario(String nombre){
        if(findUsuario(nombre)){
            for (Iterator iterator = listaUsuariosIntentoFallido.iterator(); iterator.hasNext();) {
                IntentoFallido next = (IntentoFallido) iterator.next();
                if(next.getUsuario().equalsIgnoreCase(nombre)){
                    return next.getIntentos();
                }
            }
        }
        return 0;
    }
    
    /**
     * Busca a un usuario en la lista de los intentos fallidos por nombre
     * @param nombre
     * @return 
     */
    private boolean findUsuario(String nombre){
        for (Iterator iterator = listaUsuariosIntentoFallido.iterator(); iterator.hasNext();) {
            IntentoFallido next = (IntentoFallido) iterator.next();
            if(next.getUsuario().equalsIgnoreCase(nombre)){
                return true;
            }
        }
        return false;
    }
    
    public void removeUsuario(String nombre){
        removeUsuarioNombre(nombre);
    }
    
    private void removeUsuarioNombre(String nombre){
        for (Iterator iterator = listaUsuariosIntentoFallido.iterator(); iterator.hasNext();) {
            IntentoFallido next = (IntentoFallido) iterator.next();
            if(next.getUsuario().equalsIgnoreCase(nombre)){
                iterator.remove();
                break;
            }
        }
    }
    
    public List getUsuariosActivos(){
        return listaUsuariosIntentoFallido;
    }
    
}
