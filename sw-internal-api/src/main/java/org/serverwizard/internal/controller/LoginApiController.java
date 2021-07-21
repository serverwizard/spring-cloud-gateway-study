package org.serverwizard.internal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginApiController {

    @GetMapping
    public String login() throws InterruptedException {
        Thread.sleep(4000);
        log.info(">>> login");
        return "login";
    }
}
