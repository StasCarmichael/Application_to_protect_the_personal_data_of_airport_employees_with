package org.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/")
public class IndexController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring!";
    }

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }
}
