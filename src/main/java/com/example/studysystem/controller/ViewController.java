package com.example.studysystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/view")
public class ViewController {
    @RequestMapping(value = "/home")
    public String jumpToHome(){
        return "index.html";
    }
    @RequestMapping(value = "/manage")
    public String jumpToManage(){
        return "manage.html";
    }
    @RequestMapping(value = "/author")
    public String jumpToAuthor(){
        return "author.html";
    }
    @RequestMapping(value = "/org")
    public String jumpToOrg(){
        return "org.html";
    }
}
