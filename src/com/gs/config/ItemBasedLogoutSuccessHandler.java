/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

//import com.sigsa.sisbc.classes.RequestNodeJS;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 *
 * @author SIGSA
 */
public class ItemBasedLogoutSuccessHandler implements LogoutSuccessHandler{
    
//    private RequestNodeJS requestNodeJS;

//    public RequestNodeJS getRequestNodeJS() {
//        return requestNodeJS;
//    }

//    public void setRequestNodeJS(RequestNodeJS requestNodeJS) {
//        this.requestNodeJS = requestNodeJS;
//    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
        //Se envía a refrescar la ventana de todos los clientes para que cierre la sessión
        requestNodeJS.sendRequestWithUsernameAndMethod(a.getName(), "/session-close");
        System.out.println("-----------------------------CERRANDO SESIÓN-----------------------------");
        response.sendRedirect("login?out=1");
        //request.getRequestDispatcher("login?out=1").forward(request, response);
    }
    
}
