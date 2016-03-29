/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author SIGSA
 */
public class IntentLimitExceeded extends AuthenticationException {
    
    public IntentLimitExceeded(String msj){
        super(msj);
    }
}
