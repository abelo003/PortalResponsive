/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gs.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 *
 * @author SIGSA
 */
public class CustomFilterLogin implements Filter, InitializingBean {

    private String expiredUrl;

//~ Methods ================================================== ================================================== ====
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(expiredUrl, "ExpiredUrl required");
    }

    /**
     * Does nothing. We use IoC container lifecycle services instead.
     */
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Assert.isInstanceOf(HttpServletRequest.class, request, "Can only process HttpServletRequest");
        Assert.isInstanceOf(HttpServletResponse.class, response, "Can only process HttpServletResponse");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        System.out.println("ESTOY EN CUSTOMFILTERLOGIN: "+httpRequest.getServletPath());
        String path = httpRequest.getServletPath();
        HttpSession session = httpRequest.getSession(false);
        if (session == null && !httpRequest.isRequestedSessionIdValid()) {
            String targetUrl = httpRequest.getContextPath() + "/login";
            httpResponse.sendRedirect(httpResponse.encodeRedirectURL(targetUrl));
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * Does nothing. We use IoC container lifecycle services instead.
     *     
* @param arg0 ignored
     *     
* @throws ServletException ignored
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    public void setExpiredUrl(String expiredUrl) {
        this.expiredUrl = expiredUrl;
    }
}
