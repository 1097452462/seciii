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

    @ResponseBody
    @RequestMapping(value = "/authorNum",method = RequestMethod.GET)
    public Response getAuthorNum(@RequestParam("id") int id){
        Response response= orgService.getAuthorNum(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/paperNum",method = RequestMethod.GET)
    public Response getPaperNum(@RequestParam("id") int id){
        Response response= orgService.getPaperNum(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/getCitationSum",method = RequestMethod.GET)
    public Response getCitationSum(@RequestParam("id") int id){
        Response response= orgService.getCitationSum(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/getTopPaper",method = RequestMethod.GET)
    public Response getTopPaper(@RequestParam("id") int id){
        Response response= orgService.getTopPaper(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/topKeyword",method = RequestMethod.GET)
    public Response getTopKeyword(@RequestParam("id") int id){
        Response response= orgService.getTopKeyword(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/getRelatedAuthors",method = RequestMethod.GET)
    public Response getRelatedAuthors(@RequestParam("id") int id){
        Response response= orgService.getRelatedAuthors(id);
        return  response;
    }    @ResponseBody
    @RequestMapping(value = "/getRelatedOrgs",method = RequestMethod.GET)
    public Response getRelatedOrgs(@RequestParam("id") int id){
        Response response= orgService.getRelatedOrgs(id);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/topAuthor",method = RequestMethod.GET)
    public Response getTopAuthor(@RequestParam("id") int id){
        Response response= orgService.getTopAuthor(id);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public Response getTop10Author(@RequestParam("methodId") int methodId){
        return orgService.getTop10Org(methodId);
    }
}
