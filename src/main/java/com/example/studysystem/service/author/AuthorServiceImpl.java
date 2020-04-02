package com.example.studysystem.service.author;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Author;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SimplePaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorServiceImpl implements AuthorService{
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private SimplePaperDao simplePaperDao;
    @Autowired
    private PaperDao paperDao;

    @Override
    public Response getAuthors() {
        try{
            return Response.buildSuccess(authorDao.getAuthors());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getAuthorById(int id) {
        try{
            return Response.buildSuccess(authorDao.getAuthorById(id));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getSimplePaperByAuthor(int id) {
        try{
            String[] temp=authorDao.getPaperIdByAuthor(id).split(";");
            List<Integer> paperId=new ArrayList<>();
            for(String d:temp)paperId.add(Integer.parseInt(d));
            return Response.buildSuccess(paperDao.getPapersByIds(paperId));
//            return Response.buildSuccess(simplePaperDao.getSimplePaperByAuthor(name));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response searchAuthors(String name, String num) {
        try {
            int d=0;
            if(!num.isEmpty())d=Integer.parseInt(num);
            List<Author> authors=authorDao.searchAuthors(name,d);
            return Response.buildSuccess(authors);
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getCitationSum(int id) {  //返回某作者的文章被引用数总和
        try{
            List<String> temp=authorDao.getCitationSum(id);
            int n=0;
            for(String s:temp){
                if(!s.isEmpty())
                    n+=Integer.parseInt(s);
            }
            return Response.buildSuccess(n);
        }
        catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopPaper(int id) { //根据作者id获得被引用数最多的前5个paper
        try{
            List<Integer> paperIds=authorDao.getTopSimplePaperId(id);
            List<Paper> simplePapers=paperDao.getPapersByIds(paperIds);
            return Response.buildSuccess(simplePapers);
        }
        catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopKeyword(int id) { //返回该作者所有文章关键词最高频前5，是词组不是单个单词
        try{
            List<String> words=authorDao.getKeywords(id);
            List<String> ans=new ArrayList<>();
            Map<String,Integer> map=new TreeMap<>();
            for(String w:words){
                for(String s:w.split(";")){
                    if(s.isEmpty())continue;
                    if(map.containsKey(s))map.put(s,map.get(s)+1);
                    else map.put(s,1);
                }
            }
            map=sortByValueDescending(map);
            int n=0;
            for(Map.Entry<String,Integer> a:map.entrySet()){
                //System.out.println(a.getKey()+"  "+a.getValue());
                ans.add(a.getKey());
                n++;
                if(n==5)break;
            }

            return Response.buildSuccess(ans);
        }
        catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
