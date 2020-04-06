package com.example.studysystem.service.paper;

import com.example.studysystem.db.InsertDB;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Meeting;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SimplePaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private SimplePaperDao simplePaperDao;
    public void set(PaperDao paperDao,SimplePaperDao simplePaperDao){this.paperDao=paperDao;this.simplePaperDao=simplePaperDao;}

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
    public Response getMeetingTop10(){
        try{
            List<Meeting> meetings =paperDao.getMeetingTop10();
            return Response.buildSuccess(meetings);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
    @Override
    public Response getMeetingById(int id){
        try{
            Meeting meeting =paperDao.getMeetingById(id);
            return Response.buildSuccess(meeting);
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
    public Response searchPapers(SimplePaper simplePaper) {
        try {
            Long start = System.currentTimeMillis();
            List<Integer> num = new ArrayList<>();
            Map<Integer, Integer> map = new HashMap<>();
            String[] title_list = simplePaper.getDocument_title().split(" ");
            String[] author_list = simplePaper.getAuthors().split(" ");
            String[] org_list = simplePaper.getAuthor_Affiliations().split(" ");
            String[] meeting_list = simplePaper.getPublication_Title().split(" ");
            String[] keyword_list = simplePaper.getAuthor_Keywords().split(" ");
            int y=0;

            for (String s : title_list) {
                List<Integer> temp = simplePaperDao.simpleSelect_title(s);
                for (int d : temp) {
                    if (map.containsKey(d)) map.put(d, map.get(d) + 1);
                    else map.put(d, 1);
                }
            }
            for (String s : author_list) {
                List<Integer> temp = simplePaperDao.simpleSelect_author(s);
                for (int d : temp) {
                    if (map.containsKey(d)) map.put(d, map.get(d) + 1);
                    else map.put(d, 1);
                }
            }
            if(!simplePaper.getPublication_Year().isEmpty()) {
                y=1;
                List<Integer> temp = simplePaperDao.simpleSelect_year(simplePaper.getPublication_Year());
                for (int d : temp) {
                    if (map.containsKey(d)) map.put(d, map.get(d) + 1);
                    else map.put(d, 1);
                }
            }
            for (String s : org_list) {
                List<Integer> temp2 = simplePaperDao.simpleSelect_org(s);
                for (int d : temp2) {
                    if (map.containsKey(d)) map.put(d, map.get(d) + 1);
                    else map.put(d, 1);
                }
            }
            for (String s : meeting_list) {
                List<Integer> temp2 = simplePaperDao.simpleSelect_meeting(s);
                for (int d : temp2) {
                    if (map.containsKey(d)) map.put(d, map.get(d) + 1);
                    else map.put(d, 1);
                }
            }
            for (String s : keyword_list) {
                List<Integer> temp2 = simplePaperDao.simpleSelect_keyword(s);
                for (int d : temp2) {
                    if (map.containsKey(d)) map.put(d, map.get(d) + 1);
                    else map.put(d, 1);
                }
            }


            int n = title_list.length + author_list.length + org_list.length + y + meeting_list.length + keyword_list.length;
            for (Map.Entry<Integer, Integer> m : map.entrySet()) {
                if (m.getValue() == n) num.add(m.getKey());
            }

            List newList = num.stream().distinct().collect(Collectors.toList());//System.out.println(newList.size());
            List<Paper> papers = new ArrayList<>();
            if (newList.size() > 0) papers = paperDao.getPapersByIds(newList);

            Long end = System.currentTimeMillis();
            System.out.println("用时" + (end - start) + "ms");
            return Response.buildSuccess(papers);

        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
}
/*  老的破烂搜索方法，必须全部拿出来再一个一个搜
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
            */