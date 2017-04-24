package com.hellojd.servletapi.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * Created by Administrator on 2017/4/24.
 */
@Configuration
@ComponentScan(value = {"com.hellojd.servletapi.**.service"},
               excludeFilters={@ComponentScan.Filter(type =FilterType.ANNOTATION,value = Controller.class)})
public class RootConfiguration {

}
