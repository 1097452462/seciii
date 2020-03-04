package com.example.studysystem.controller;

import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SearchForm;
import com.example.studysystem.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/paper"})
public class PaperController {

    @Autowired
    private PaperService paperService;
    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Response getPapers(){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= paperService.getPapers();
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Response searchUser(@RequestBody SearchForm searchForm){
        Response response= paperService.searchPapers(searchForm);
        return  response;
    }
}
