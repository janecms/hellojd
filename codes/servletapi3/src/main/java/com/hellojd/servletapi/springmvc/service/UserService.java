package com.hellojd.servletapi.springmvc.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/24.
 */
@Service
public class UserService {
    public void sayHello() {
        System.out.println("UserService:hello world");
    }
}
