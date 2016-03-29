/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

/**
 *
 * @author SIGSA
 */
@Component
public class ListUsersBlocked extends ArrayList{
    
    private static ListUsersBlocked instance = new ListUsersBlocked();
    
    public ListUsersBlocked(){
        super();
    }
    
    public static ListUsersBlocked getListUsersBlocked(){
        return instance;
    }
}
