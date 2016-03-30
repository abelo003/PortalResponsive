///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.gs.config;
//
//import java.util.Calendar;
//import javax.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.PropertySources;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author SIGSA
// */
//@Component
//@PropertySources({
//    @PropertySource("/WEB-INF/properties/municipioConfig.properties")
//})
//public class MunicipioInfo {
//    
//    @Autowired
//    Environment env;
//    
//    private String numero;
//    private String nombre;
//    private String entidad;
//    private String noEntidad;
//    
//    @PostConstruct
//    public void inicio() {
//        this.nombre = env.getProperty("municipio.nombre").toLowerCase();
//        this.numero = env.getProperty("municipio.numero").toLowerCase();
//        this.entidad = env.getProperty("municipio.entidad").toLowerCase();
//        this.noEntidad = env.getProperty("municipio.nombre.entidad").toLowerCase();
//    }
//
//    /**
//     * Método que obtiene el número de municipio configurado en el archivo municipioConfig.properties
//     * @return 
//     */
//    public String getNumero() {
//        return numero;
//    }
//
//    public void setNumero(String numero) {
//        this.numero = numero;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    /**
//     * Método que obtiene el nombre del municipio configurado en el archivo municipioConfig.properties
//     * @param nombre Nombre del municipio.
//     */
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//    
//    /**
//     * Crea los primeros 8 dígitos de un folio de multitrámite del municipio configurado.
//     * @return Los primero 8 dígitos del folio multitrámite.
//     */
//    public String getFMNow(){
//        Calendar fecha = Calendar.getInstance();
//        return "FM" + this.numero.substring(1) + fecha.get(Calendar.YEAR);
//    }
//    
//    /**
//     * Crea un folio de multitrámite ingresando la sección numérica del mismo.
//     * @param folio Números de terminación del folio.
//     * @return Folio de multitrámite completo de 15 dígitos.
//     */
//    public String getFMNow(String folio){
//        if(folio.length() <= 8){
//            Calendar fecha = Calendar.getInstance();
//            return "FM" + this.numero.substring(1) + fecha.get(Calendar.YEAR) + acompleteFolio(folio);
//        }
//        return "nothing";
//    }
//    /**
//     * Crea un folio tramite ingresado la seccion numerica del mismo
//     * @param folio numeros de terminacion del folio
//     * @return Folio de tramie completo de 13 digitos
//     */
//    public String GetFTNow(String folio){
//        if(folio.length()<=8){
//            Calendar fecha = Calendar.getInstance();
//            return "FT" + this.numero.substring(1) + fecha.get(Calendar.YEAR) + acompleteFolioT(folio); 
//        }
//        return "nothing";
//    }
//    
//    private String acompleteFolio(String folio){
//        int size = folio.length();
//        for (int i = 0; i < 7 - size; i++) {
//            folio = "0" + folio;
//        }
//        return folio;
//    }
//    
//    private String acompleteFolioT(String folio){
//        int size = folio.length();
//        for (int i = 0; i < 5-size; i++) {
//            folio = "0" + folio;
//        }
//        return folio;
//    }
//
//    public String getEntidad() {
//        return entidad;
//    }
//
//    public void setEntidad(String entidad) {
//        this.entidad = entidad;
//    }
//
//    public String getNoEntidad() {
//        return noEntidad;
//    }
//
//    public void setNoEntidad(String noEntidad) {
//        this.noEntidad = noEntidad;
//    }
//    
//}
