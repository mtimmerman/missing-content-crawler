package com.mtimmerman.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by maarten on 16.01.15.
 */
@Controller
@RequestMapping("/")
public class ErrorController {
    @RequestMapping(value="/error", method = RequestMethod.GET)
    public String error() {
        return "error";
    }
}
