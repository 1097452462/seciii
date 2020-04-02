package com.example.studysystem.controller;

import com.example.studysystem.entity.Response;
import com.example.studysystem.service.file.FileService;
import com.example.studysystem.service.paper.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = {"/file"})
public class FileController {
    @Autowired
    private FileService fileService;
    public void set(FileService fileService){this.fileService=fileService;}

    @ResponseBody
    @RequestMapping(value = "/getFiles",method = RequestMethod.GET)
    public Response getFiles(){
        return fileService.getFiles();
    }

    //新增文件
    @ResponseBody
    @RequestMapping(value = "/addFile",method = RequestMethod.POST)
    public Response uploadFile(@RequestParam("file") MultipartFile file){//System.out.println("uploadController");
        return(fileService.addFile(file));
    }

    @ResponseBody
    @RequestMapping(value = "/updateDB",method = RequestMethod.POST)
    public Response updateDB(){
        return fileService.updateDB();
    }

}
