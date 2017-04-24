package com.hellojd.servletapi.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"},filterName ="filter1",dispatcherTypes ={DispatcherType.REQUEST,DispatcherType.FORWARD} )
public class Filter1 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        System.out.println("filter1===" + httpServletRequest.getRequestURI());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
