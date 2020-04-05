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

import java.util.Date;

@Controller
@RequestMapping(value = {"/author"})
public class AuthorController {
//    aaa
    @Autowired
    private AuthorService authorService;
    public void set(AuthorService authorService){
        this.authorService=authorService;
    }

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Response getOrgs(){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= authorService.getAuthors();
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public Response getOrgs(@RequestParam("id")int id){//System.out.println(21);
        Response response= authorService.getAuthorById(id);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/getCitationSum",method = RequestMethod.GET)
    public Response getCitationSum(@RequestParam("id")int id){
        Response response= authorService.getCitationSum(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/getTopPaper",method = RequestMethod.GET)
    public Response getTopPaper(@RequestParam("id")int id){
        Response response= authorService.getTopPaper(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/getTopKeyword",method = RequestMethod.GET)
    public Response getTopKeyword(@RequestParam("id")int id){
        Response response= authorService.getTopKeyword(id);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/getSimplepaperById",method = RequestMethod.GET)
    public Response getSimplePaperByOrg(@RequestParam("id")int id){//System.out.println(28);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= authorService.getSimplePaperByAuthor(id);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Response searchAuthors(@RequestParam("name") String name, @RequestParam("num") String num){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= authorService.searchAuthors(name,num);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public Response getTop10Author(@RequestParam("methodId") int methodId){
        return authorService.getTop10Author(methodId);
    }

    @ResponseBody
    @RequestMapping(value = "/relevant-author", method = RequestMethod.GET)
    public Response getRelevantAuthor(@RequestParam("id") int id){
        return authorService.getRelatedAuthors(id);
    }

    @ResponseBody
    @RequestMapping(value = "/relevant-org", method = RequestMethod.GET)
    public Response getRelevantOrg(@RequestParam("id") int id){
        return authorService.getRelatedOrgs(id);
    }

}