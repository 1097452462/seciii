package com.example.studysystem.controller;

import com.example.studysystem.entity.Response;
import com.example.studysystem.service.field.FieldService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"/field"})
public class FieldController {
    @Autowired
    private FieldService fieldService;
    public void set(FieldService f){this.fieldService=f;}

    @ResponseBody
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public Response getById(@Param("id") int id){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= fieldService.getFieldById(id);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/getTop10",method = RequestMethod.GET)
    public Response getTop10Field(@Param("mode") int mode){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= fieldService.getTop10Field(mode);
        return  response;
    }

    @ResponseBody
    @RequestMapping(value = "/getTopAuthors",method = RequestMethod.GET)
    public Response getTopAuthors(@Param("id") int id){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= fieldService.getTopAuthors(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/getTopOrgs",method = RequestMethod.GET)
    public Response getTopOrgs(@Param("id") int id){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= fieldService.getTopOrgs(id);
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/getTopPapers",method = RequestMethod.GET)
    public Response getTopPapers(@Param("id") int id){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= fieldService.getTopPapers(id);
        return  response;
    }

}
