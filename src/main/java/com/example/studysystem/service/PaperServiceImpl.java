package com.example.studysystem.service;

import com.example.studysystem.csv.readCSV;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SearchForm;
import com.example.studysystem.entity.SimplePaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private readCSV readCSV;

    @Override
    public Response plugPapers(){
        try{
            readCSV.tranfData();
            return Response.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    public Response getPapers() {
        try{
            List<Paper> papers= paperDao.getPapers();//System.out.println(papers.size());
            //System.out.println(papers.get(0).getAuthors());
            return Response.buildSuccess(papers);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
    @Override
    public Response searchPapers(SearchForm searchForm){
        try{
            List<Paper> papers= paperDao.getPapers();
            List<Paper> ans=new ArrayList<>();
            for(Paper paper:papers){
                if((searchForm.getAuthors().isEmpty()||paper.getAuthors().contains(searchForm.getAuthors()))&&
                        (searchForm.getAuthor_Affiliations().isEmpty()||paper.getAuthor_Affiliations().contains(searchForm.getAuthor_Affiliations()))&&
                        (searchForm.getPublication_Title().isEmpty()||paper.getPublication_Title().contains(searchForm.getPublication_Title()))&&
                        (searchForm.getAuthor_Keywords().isEmpty()||paper.getAuthor_Keywords().contains(searchForm.getAuthor_Keywords()))
                )ans.add(paper);
            }
            System.out.println(ans.size());
            return Response.buildSuccess(ans);
        }catch (Exception e){
        e.printStackTrace();
        return (Response.buildFailure("失败"));
    }
    }
}
