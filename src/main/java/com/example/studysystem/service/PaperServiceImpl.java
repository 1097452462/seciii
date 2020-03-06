package com.example.studysystem.service;

import com.example.studysystem.csv.readCSV;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SimplePaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private SimplePaperDao simplePaperDao;
    @Autowired
    private readCSV readCSV;

    @Override
    public Response addFile(MultipartFile file) {//System.out.println("26");
       try{
           String fileName = file.getOriginalFilename();
           String suffix = fileName.substring(fileName.lastIndexOf('.'));
           String newFileName = new Date().getTime() + suffix;//System.out.println(newFileName);
           File directory = new File("./");
           String path2=directory.getAbsolutePath();
           path2=path2.substring(0,path2.length()-1);
           File newFile = new File(path2 + "src/main/resources/excel/" + newFileName);
           try {
               file.transferTo(newFile);
           }
           catch (Exception e){
               e.printStackTrace();
               return (Response.buildFailure("失败"));
           }
           return Response.buildSuccess();
       }catch (Exception e){
           e.printStackTrace();
           return (Response.buildFailure("失败"));
       }
    }

    @Override
    public Response plugPapers(){
        try{
            readCSV.tranfData();
            return Response.buildSuccess(simplePaperDao.getSimplePapers());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getSimplePapers() {
        try{
            List<SimplePaper> simplePapers =simplePaperDao.getSimplePapers();
            return Response.buildSuccess(simplePapers);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public void handle(List<SimplePaper> origin) {
       int n=origin.size();
       List<SimplePaper> after=new ArrayList<>();
       after.add(origin.get(0));
       for(int i=1;i<n;i++){

       }

    }


    public Response getPaperById(int id) {
        try{
            return (Response.buildSuccess(paperDao.getPapersById(id)));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
    @Override
    public Response searchPapers(SimplePaper simplePaper){
        try{
            List<SimplePaper> simplePapers= simplePaperDao.getSimplePapers();
            List<SimplePaper> ans=new ArrayList<>();

            for(SimplePaper p:simplePapers) {
                boolean flag1=true, flag2=true, flag3=true, flag4 = true;

                if (!simplePaper.getAuthors().isEmpty()) {
                    String temp = simplePaper.getAuthors().toLowerCase();
                    temp.replaceAll(";", " ");
                    String list[] = temp.split(" ");
                    for (String x : list) {
                        if (!p.getAuthors().toLowerCase().contains(x)){
                            flag1=false;
                            break;
                        }
                    }
                }
                if (!simplePaper.getAuthor_Affiliations().isEmpty()) {
                    String temp = simplePaper.getAuthor_Affiliations().toLowerCase();
                    temp.replaceAll(";", " ");
                    String list[] = temp.split(" ");
                    for (String x : list) {
                        if (!p.getAuthor_Affiliations().toLowerCase().contains(x)){
                            flag2=false;
                            break;
                        }
                    }
                }
                if (!simplePaper.getPublication_Title().isEmpty()) {
                    String temp = simplePaper.getPublication_Title().toLowerCase();
                    temp.replaceAll(";", " ");
                    String list[] = temp.split(" ");
                    for (String x : list) {
                        if (!p.getPublication_Title().toLowerCase().contains(x)){
                            flag3=false;
                            break;
                        }
                    }
                }
                if (!simplePaper.getAuthor_Keywords().isEmpty()) {
                    String temp = simplePaper.getAuthor_Keywords().toLowerCase();
                    temp.replaceAll(";", " ");
                    String list[] = temp.split(" ");
                    for (String x : list) {
                        if (!p.getAuthor_Keywords().toLowerCase().contains(x)){
                            flag4=false;
                            break;
                        }
                    }
                }
                if(flag1&&flag2&&flag3&&flag4)ans.add(p);
            }

            //System.out.println(ans.size());
            return Response.buildSuccess(ans);
        }catch (Exception e){
        e.printStackTrace();
        return (Response.buildFailure("失败"));
    }
    }
}
