package com.innotree.bcs.bp.study.jpa.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {
    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
