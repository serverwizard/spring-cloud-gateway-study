package org.serverwizard.internal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderApiController {

    @GetMapping
    public String findOrder() {
        log.info(">>> findOrder");
        return "order";
    }
}
