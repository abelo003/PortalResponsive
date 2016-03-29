/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 *
 * @author SIGSA
 */
public class CustomJDBCDaoImpl extends JdbcDaoImpl{
    private String queryByUsername;
    private String queryByAuthoritiesUsername;
    
    public CustomJDBCDaoImpl(){
        super();
    }

    public String getQueryByUsername() {
        return queryByUsername;
    }

    public void setQueryByUsername(String usersByUsernameQueryString) {
//        this.queryByUsername = usersByUsernameQueryString;
        super.setUsersByUsernameQuery(usersByUsernameQueryString);
    }

    public String getQueryByAuthoritiesUsername() {
        return queryByAuthoritiesUsername;
    }

    public void setQueryByAuthoritiesUsername(String queryByAuthoritiesUsername) {
//        this.queryByAuthoritiesUsername = queryByAuthoritiesUsername;
        super.setAuthoritiesByUsernameQuery(queryByAuthoritiesUsername);
    }
    
    
}
