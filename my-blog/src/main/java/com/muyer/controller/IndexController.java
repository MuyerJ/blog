package com.muyer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 
 * date: 2022/2/16 19:53
 * @author YeJiang
 * @version
 */
@RestController
@Slf4j
public class IndexController {
    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }



}
