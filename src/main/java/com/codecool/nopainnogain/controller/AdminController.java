package com.codecool.nopainnogain.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String testAdminMethod(){
        return "{\"shit\":\"kabbe\"}";
    }





}
