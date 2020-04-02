package com.example.studysystem.controller;


import com.example.studysystem.entity.Response;
import com.example.studysystem.service.org.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"/org"})
public class OrgController {
    @Autowired
    private OrgService orgService;
    public void set(OrgService orgService){this.orgService=orgService;}

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Response getOrgs(){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= orgService.getOrgs();
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public Response getOrgById(@RequestParam("id")int id){
        Response response= orgService.getOrgById(id);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/getSimplepaperById",method = RequestMethod.GET)
    public Response getSimplePaperByOrg(@RequestParam("id")int id){//System.out.println(28);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= orgService.getSimplePaperByOrg(id);
        return  response;
    }


    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Response searchOrgs(@RequestParam("name") String name,@RequestParam("num") String num){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= orgService.searchOrgs(name,num);
        return  response;
    }

}
