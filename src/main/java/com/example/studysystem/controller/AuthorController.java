package com.example.studysystem.controller;
//qwrfqet
import com.example.studysystem.entity.Response;

import com.example.studysystem.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"/author"})
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Response getOrgs(){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= authorService.orderAuthors();
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/getByName",method = RequestMethod.GET)
    public Response getSimplePaperByOrg(@RequestParam("name")String name){System.out.println(28);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= authorService.getSimplePaperByOrg(name);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Response searchAuthors(@RequestParam("name") String name, @RequestParam("num") String num){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= authorService.searchAuthors(name,num);
        return  response;
    }

}