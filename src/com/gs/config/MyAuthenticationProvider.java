/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import com.sigsa.sisbc.classes.RequestNodeJS;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author SIGSA
 */
public class MyAuthenticationProvider implements AuthenticationProvider{
    
    private SessionRegistryImpl sessionRegistry;
    private ShaPasswordEncoder shaPasswordEncoder;
    private UsuarioConIntentoFallido usuarioConIntentoFallido;
    private ListUsersLockoutIntentFail listUsersLockoutIntentFail;
    private CustomJDBCDaoImpl customJDBCDaoImpl;
    private int intentosPosibles;
    private RequestNodeJS requestNodeJS;
    
    public void setUserServiceQuery(CustomJDBCDaoImpl userServiceQuery) {
        this.customJDBCDaoImpl = userServiceQuery;
    }

    public SessionRegistryImpl getSessionRegistry() {
        return sessionRegistry;
    }

    public void setSessionRegistry(SessionRegistryImpl sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
    
    public void setRequestNodeJS(RequestNodeJS requestNodeJS) {
        this.requestNodeJS = requestNodeJS;
    }
    
    public UsuarioConIntentoFallido getUsuarioConIntentoFallido() {
        return usuarioConIntentoFallido;
    }

    public void setUsuarioConIntentoFallido(UsuarioConIntentoFallido usuarioConIntentoFallido) {
        this.usuarioConIntentoFallido = usuarioConIntentoFallido;
    }
    
    public int getIntentosPosibles() {
        return intentosPosibles;
    }

    public void setIntentosPosibles(int intentosPosibles) {
        this.intentosPosibles = intentosPosibles;
    }

    public ListUsersLockoutIntentFail getListUsersLockoutIntentFail() {
        return listUsersLockoutIntentFail;
    }

    public void setListUsersLockoutIntentFail(ListUsersLockoutIntentFail listUsersLockoutIntentFail) {
        this.listUsersLockoutIntentFail = listUsersLockoutIntentFail;
    }

    public ShaPasswordEncoder getShaPasswordEncoder() {
        return shaPasswordEncoder;
    }

    public void setShaPasswordEncoder(ShaPasswordEncoder shaPasswordEncoder) {
        this.shaPasswordEncoder = shaPasswordEncoder;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = this.customJDBCDaoImpl.loadUserByUsername(authentication.getName());
        //Obtengo los intentos de inicio de sesión hechos por un usuario
        int intentos = usuarioConIntentoFallido.getIntentosUsuario(authentication.getName());
        if(intentos < intentosPosibles && !listUsersLockoutIntentFail.findUserBlockout(authentication.getName())){
            if (userDetails.isEnabled()) {
                if (userDetails != null && shaPasswordEncoder.isPasswordValid(userDetails.getPassword(), authentication.getCredentials().toString(), null)) {
                    usuarioConIntentoFallido.removeUsuario(userDetails.getUsername());
                    //Verifico si el usuario ya tiene una sesión abierta, si es así la cierro y le creo su nueva instancia
                    verifUserInSession(userDetails.getUsername());
                    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                }
                throw new BadCredentialsException("Bad credentials");
            } else {
                throw new DisabledException("User disabled");
            }
        }
        else{
            throw new IntentLimitExceeded("limite de intentos excedidos");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    /**
     * Verifico si el usuario está en sesión, se ser afirmativo, elimino su sesión actual y libero su usuario
     * para poder iniciar su nueva sesión.
     * @return 
     */
    private void verifUserInSession(String username){
        if(sessionRegistry != null && sessionRegistry.getAllPrincipals().size() > 0){
            for (Iterator iterator = sessionRegistry.getAllPrincipals().iterator(); iterator.hasNext();) {
                List<SessionInformation> sessionInformactionList = sessionRegistry.getAllSessions(iterator.next(), false);
                if(sessionInformactionList.size() > 0){
                    if( ((User)sessionInformactionList.get(0).getPrincipal()).getUsername().equalsIgnoreCase(username)){
                        //Si ya tiene una sesión abierta se elimina su sesión actual y se permite el inicio de sesión en el otro lugar
                        if(!sessionInformactionList.get(0).isExpired()){
                            sessionInformactionList.get(0).expireNow();
                            //Se realiza la petición con nodeJs para notificar al usuario de que su sesión se ha abierto en otro lugar
                            requestNodeJS.sendRequestWithUsernameAndMethod(username.toUpperCase(), "/session-close-new-session");
                        }
                    }
                }
            }
        }
    }
    
}
