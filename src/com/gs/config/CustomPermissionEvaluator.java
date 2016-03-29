/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import com.sigsa.sisbc.model.ListTools;
import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

/**
 * Clase para evaluar los permisos de la función hasPermission en la anotación @PreAuthorize
 * @author SIGSA
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {

    /**
     * Se sobreescribe el comportamiento del método hasPermission.
     * @param authentication Objeto de autenticación.
     * @param targetDomainObject Objeto a comparar (en nuestro caso es el objeto que contiene las herramientas de los usuarios logeado).
     * @param permisoHerramienta El nombre de la herramienta a comparar.
     * @return Retorna true o false dependiendo si el usuario tiene permisos para acceder a la herramienta solicitada.
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permisoHerramienta) {
        boolean hasPermission = false;
        if (targetDomainObject instanceof ListTools) {
            ListTools lista = (ListTools)targetDomainObject;
            if(lista.getSize() > 0 && lista.findHerramienta(permisoHerramienta.toString())){
                hasPermission = true;
            }
//            System.out.println("Se imprime la información de las herramientas por módulos");
//            for (Departamento departamento : lista.getDepartamentos()) {
//                System.out.println("Impimiendo el contenido: " + departamento.toString());
//            }
        }
        return hasPermission;
    }
 
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
