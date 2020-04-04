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

    @RequestMapping(value = "/field")
    public String jumpToField(){
        return "field.html";
    }

    @RequestMapping(value = "/field-detail")
    public String jumpToFieldDetail(){
        return "fieldDetail.html";
    }

    @RequestMapping(value = "/meeting")
    public String jumpToMeeting(){
        return "meeting.html";
    }

    @RequestMapping(value = "/paper-detail")
    public String jumpToPaperDetail(){
        return "paperDetail.html";
    }

    @RequestMapping(value = "/org-detail")
    public String jumpToOrgDetail(){
        return "orgDetail.html";
    }

    @RequestMapping(value = "/author-detail")
    public String jumpToAuthorDetail(){
        return "authorDetail.html";
    }
}
