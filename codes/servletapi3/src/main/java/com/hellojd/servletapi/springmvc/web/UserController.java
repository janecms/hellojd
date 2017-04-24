package com.hellojd.servletapi.springmvc.web;

import com.hellojd.servletapi.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/4/24.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/user")
    public String hello(Model model) {
        userService.sayHello();
        model.addAttribute("msg", "hello world");
        return "user/hello";
    }
}
