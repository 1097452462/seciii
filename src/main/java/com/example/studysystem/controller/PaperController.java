package com.example.studysystem.controller;
import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SimplePaper;
import com.example.studysystem.service.paper.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = {"/paper"})
public class PaperController {
    @Autowired
    private PaperService paperService;



    //获得所有论文信息
    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Response getpapers(){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= paperService.getPapers();
        return  response;
    }
    @ResponseBody
    @RequestMapping(value = "/getSimple",method = RequestMethod.GET)
    public Response getSimplepapers(){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        Response response= paperService.getSimplePapers();
        return  response;
    }


    //搜索论文
    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Response searchUser(@RequestBody SimplePaper simplePaper){
        Response response= paperService.searchPapers(simplePaper);
        return  response;
    }

    //新增文件并更新数据库
    @ResponseBody
    @RequestMapping(value = "/addFile",method = RequestMethod.POST)
    @PostMapping(value = "/file2")
    public Response uploadFile(@RequestParam("file") MultipartFile file){//System.out.println("49");
        Response a=paperService.addFile(file);
        if(a.getSuccess())return paperService.plugPapers();
        return a;
    }

}
