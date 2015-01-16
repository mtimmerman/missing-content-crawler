package com.mtimmerman.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by maarten on 16.01.15.
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @RequestMapping(value="/login")
    public String login(HttpServletRequest request, Model model) {
        return "login";
    }
}
