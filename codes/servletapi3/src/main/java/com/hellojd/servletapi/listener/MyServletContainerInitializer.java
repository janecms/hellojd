package com.hellojd.servletapi.listener;

import com.hellojd.servletapi.servlet.DynamicServlet;
import com.hellojd.servletapi.servlet.HahaServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/24.
 */
@HandlesTypes(value = HahaServlet.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> classSet, ServletContext sc) throws ServletException {
        System.out.println("MyServletContainerInitializer init");
        /*
        ServletRegistration.Dynamic dynamic = sc.addServlet("dynamicServlet4", DynamicServlet.class);
        dynamic.addMapping("/dynamic4");

        sc.getServletRegistrations().get("dynamicServlet4").addMapping("/dynamic41");
        */
        for(Class<?> clz : classSet) {
            if(!(Modifier.isInterface(clz.getModifiers()) || Modifier.isAbstract(clz.getModifiers()))) {
                ServletRegistration.Dynamic dynamic = sc.addServlet(clz.getName(), (Class<? extends HahaServlet>)clz);
                String pattern = "/" + clz.getSimpleName().substring(0, 1).toLowerCase() + clz.getSimpleName().substring(1);
                System.out.println("patter="+pattern);
                dynamic.addMapping(pattern);
            }
        }

    }
}
