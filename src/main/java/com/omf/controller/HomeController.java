package com.omf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //note - this is a spring-boot controller, not @RestController
public class HomeController {
    @RequestMapping ("/")
    public String home() {
	return "redirect:/swagger-ui/index.html";
    }
}