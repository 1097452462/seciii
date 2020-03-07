package com.example.studysystem.service.paper;

import com.example.studysystem.csv.ReadCSV;
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
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private SimplePaperDao simplePaperDao;
    @Autowired
    private ReadCSV readCSV;

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
    public Response getPapers() {
        try{
            List<Paper> papers =paperDao.getPapers();
            return Response.buildSuccess(papers);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getPapersById(int id) {
        try{
            Paper paper=paperDao.getPaperById(id);
            return Response.buildSuccess(paper);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response searchPapers(SimplePaper simplePaper){
        try{
            List<SimplePaper> simplePapers= simplePaperDao.getSimplePapers();
            List<Integer> num=new ArrayList<>();

            for(SimplePaper p:simplePapers) {
                boolean flag00=true,flag0=true,flag1=true, flag2=true, flag3=true, flag4 = true;

                if (!simplePaper.getDocument_title().isEmpty()) {
                    String temp = simplePaper.getDocument_title().toLowerCase();
                    temp.replaceAll(";", " ");
                    String list[] = temp.split(" ");
                    for (String x : list) {
                        if (!p.getDocument_title().toLowerCase().contains(x)){
                            flag00=false;
                            break;
                        }
                    }
                }
                if(!flag00)continue;

                if(!simplePaper.getPublication_Year().isEmpty()) {
                    if (!simplePaper.getPublication_Year().equals(p.getPublication_Year())) {
                        flag0 = false;
                    }
                }
                if(!flag0)continue;
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
                if(!flag1)continue;
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
                if(!flag2)continue;
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
                if(!flag3)continue;
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
                if(flag4)num.add(p.getPaper_id());
            }


            List newList = num.stream().distinct().collect(Collectors.toList());System.out.println(newList.size());
            List<Paper> papers=new ArrayList<>();
            if(newList.size()>0)papers=paperDao.getPapersByIds(newList);
            //System.out.println(papers.size());
            return Response.buildSuccess(papers);

        }catch (Exception e){
        e.printStackTrace();
        return (Response.buildFailure("失败"));
    }
    }
}
