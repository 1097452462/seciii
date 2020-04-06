package com.example.studysystem.service.author;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Author;
import com.example.studysystem.entity.History;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import com.example.studysystem.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private PaperDao paperDao;
    public void set(AuthorDao a,OrgDao o,PaperDao p){
        this.authorDao=a;
        this.orgDao=o;
        this.paperDao=p;
    }

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
    public Response getPaperNum(int id) {
        try{
            return Response.buildSuccess(authorDao.getPaperNum(id));
        }
        catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getCitationSum(int id) {  //返回某作者的文章被引用数总和
        try{
            return Response.buildSuccess(authorDao.getCitationSum(id));
        }
        catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopPaper(int id) { //根据作者id获得被引用数最多的前5个paper
        try{
            List<Integer> paperIds=authorDao.getTopPaperIds(id);
            List<Paper> Papers=paperDao.getPapersByIds(paperIds);
            return Response.buildSuccess(Papers);
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

    public Response getTop10Author(int mode){
        try{
            List<Author> authors=new ArrayList<>();
            switch (mode){
                case 1:authors=authorDao.getTopAuthor_paperNum();  // 论文总数排名
                    break;
                case 2:authors=authorDao.getTopAuthor_citationSum(); //引用总数排名
                    break;
                case 3:authors=authorDao.getTopAuthor_point();// 7 3 开
                    break;
            }
            return Response.buildSuccess(authors);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRelatedAuthors(int id) {// 和这个作者一起发过论文的作者，前五，根据一起发过的论文数量降序
        try{
            return Response.buildSuccess(authorDao.getRelatedAuthor_byAuthorId(id));
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRelatedOrgs(int id) {// 和这个作者一起发过论文的机构，前五，根据一起发过的论文数量降序
        try{
            return Response.buildSuccess(orgDao.getRelatedOrg_byAuthorId(id));
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getHistory(int id){
        try{
            List<History> histories=authorDao.getHistory(id);
            List<Integer> ans=new ArrayList<>();
            for(int i=1988;i<=2020;i++) {
                boolean find=false;
                for (History h : histories) {
                    if(h.getYear().isEmpty())continue;
                    if(Integer.parseInt(h.getYear())==i) {
                        ans.add(h.getNum());
                        find=true;
                        break;
                    }
                }
                if(!find)ans.add(0);
            }
            return Response.buildSuccess(ans);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getInterest(int id){
        try{
            List<String> titles=authorDao.getTitles(id);
            Map<String,Integer> map=new TreeMap<>();
            for(String t:titles){
                for(String s:t.split(" ")){
                    if(s.isEmpty())continue;
                    if(boringWord(s))continue;
                    s=s.toLowerCase();
                    if(map.containsKey(s))map.put(s,map.get(s)+1);
                    else map.put(s,1);
                }
            }
            map=sortByValueDescending(map);
            List<List<String>> ans=new ArrayList<>();
            int i=0;
            for(Map.Entry<String,Integer> a:map.entrySet()){//System.out.println(a.getKey()+"  "+a.getValue());
                List<String> temp=new ArrayList<>();
                temp.add(a.getKey());
                temp.add(Integer.toString(a.getValue()));
                ans.add(temp);
                i++;
                if(i==8)break;
            }
            return Response.buildSuccess(ans);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    private boolean boringWord(String s){
        if(s.contains("(")||s.contains(")"))return true;
        String regex2 = ".*[0-9].*";
        if(s.matches(regex2))return true;
        String[] list={"of","an","for","a","and","from","on","in","the","high","low","over","with","to","through"};
        for(String l:list)if(l.equals(s))return true;
        return false;
    }

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
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
