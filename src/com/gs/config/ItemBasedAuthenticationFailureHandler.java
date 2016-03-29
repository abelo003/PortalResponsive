/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import com.sigsa.sisbc.daoimp.SessionFailDaoImp;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

/**
 *
 * @author SIGSA
 */
public class ItemBasedAuthenticationFailureHandler implements AuthenticationFailureHandler {

    SessionFailDaoImp sessionFailDaoImp;
    ListUsersLockoutIntentFail listUsersLockoutIntentFail;
    private UsuarioConIntentoFallido usuarioConIntentoFallido;
    private int tiempoLockout;

    public UsuarioConIntentoFallido getUsuarioConIntentoFallido() {
        return usuarioConIntentoFallido;
    }

    public SessionFailDaoImp getSessionFailDaoImp() {
        return sessionFailDaoImp;
    }

    public void setSessionFailDaoImp(SessionFailDaoImp sessionFailDaoImp) {
        this.sessionFailDaoImp = sessionFailDaoImp;
    }

    public void setUsuarioConIntentoFallido(UsuarioConIntentoFallido usuarioConIntentoFallido) {
        this.usuarioConIntentoFallido = usuarioConIntentoFallido;
    }

    public ListUsersLockoutIntentFail getListUsersLockoutIntentFail() {
        return listUsersLockoutIntentFail;
    }

    public void setListUsersLockoutIntentFail(ListUsersLockoutIntentFail listUsersLockoutIntentFail) {
        this.listUsersLockoutIntentFail = listUsersLockoutIntentFail;
    }

    public int getTiempoLockout() {
        return tiempoLockout;
    }

    public void setTiempoLockout(int tiempoLockout) {
        this.tiempoLockout = tiempoLockout * 60 * 1000;
    }
        
    @Override
    public void onAuthenticationFailure(HttpServletRequest request , HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken user =(UsernamePasswordAuthenticationToken)exception.getAuthentication();
        //System.out.println("Mensaje del error: "+exception.getMessage());
//        PrincipalsessionInformaction user = request.getUserPrincipal();
        System.out.println("-----------------------------INTENTO FALLIDO-----------------------------");
        
        //Causas de la autenticación fallida
        if(exception.getClass().isAssignableFrom(UsernameNotFoundException.class)){
//            System.out.println("INTENTO FALLIDO: El usuario no está registrado en la base de datos ");
            request.setAttribute("ERRORSESSION", "Usuario no registrado, verifique con el administrador");
            request.getRequestDispatcher("login?err=1").forward(request, response);
            //response.sendRedirect("login?err=1");
        }
        else if(exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            sessionFailDaoImp.insertUserSessionFail(user.getName(), request.getLocalAddr());
            usuarioConIntentoFallido.addIntentoUsuario(user.getName());
//            System.out.println("INTENTO FALLIDO: Creedenciales erroneas");
            request.setAttribute("ERRORSESSION", "Contraseña incorrecta, intente nuevamente");
            request.getRequestDispatcher("login?err=1").forward(request, response);
            //response.sendRedirect("login?err=2");
        } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
//            System.out.println("INTENTO FALLIDO: Usuario desabilitado");
            request.setAttribute("ERRORSESSION", "Usuario deshabilitado, verifique con el administrador");
            request.getRequestDispatcher("login?err=1").forward(request, response);
            //response.sendRedirect("login?err=3");
        }
        else if (exception.getClass().isAssignableFrom(SessionAuthenticationException.class)) {
//            System.out.println("INTENTO FALLIDO: Usuario ya logeado");
            request.setAttribute("ERRORSESSION", "Ya existe una sesión abierta con este usuario");
            request.getRequestDispatcher("login?err=1").forward(request, response);
            //response.sendRedirect("login?err=4");
        }
        else if(exception.getClass().isAssignableFrom(IntentLimitExceeded.class)){
//            System.out.println("INTENTO FALLIDO: NÚMERO DE INTENTOS EXCEDIDOS");
            //Elimino al usuario de la listo de los intentos y se agrega a la lista de usuarios bloqueados
            usuarioConIntentoFallido.removeUsuario(user.getName());
            //Se crea el hilo para desbloquear al usuario
            listUsersLockoutIntentFail.addBlockUserFail(user.getName(), tiempoLockout);
            //request.setAttribute("ERRORSESSION", "Ha excedido el límite de intentos. Por favor espere unos minutos e intente nuevamente");
            request.getRequestDispatcher("intentlimit").forward(request, response);
        }
        else{
//            System.out.println("INTENTO FALLIDO: NO SE QUE PASO");
            request.setAttribute("ERRORSESSION", "No ha sido posible iniciar sesión");
            request.getRequestDispatcher("login?err=1").forward(request, response);
        }
    }
    
}
