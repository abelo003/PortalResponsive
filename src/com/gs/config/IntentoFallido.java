/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

/**
 *
 * @author SIGSA
 */
public class IntentoFallido {
    private String usuario;
    private int intento;
    private boolean bloqueado;
    
    public IntentoFallido(){}
    
    public IntentoFallido(String usuario){
        this.usuario = usuario;
        this.intento = 1;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIntentos() {
        return intento;
    }

    public void setIntentos(int intentos) {
        this.intento = intentos;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    
    public void incrementaIntento(){
        this.intento ++;
    }
    
    @Override
    public String toString(){
        return "Usuario: " + getUsuario() + " " + getIntentos();
    }
}
