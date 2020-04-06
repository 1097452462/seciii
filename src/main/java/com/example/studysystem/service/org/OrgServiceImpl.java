package com.example.studysystem.service.org;
import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrgServiceImpl implements OrgService{
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private AuthorDao authorDao;
    public void set(OrgDao orgDao,PaperDao paperDao,AuthorDao authorDao){this.orgDao=orgDao;this.paperDao=paperDao;this.authorDao=authorDao;}

    @Override
    public Response getOrgs() {
        try{
            return Response.buildSuccess(orgDao.getOrgs());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getOrgById(int id) {
       try{
           return Response.buildSuccess(orgDao.getOrgById(id));
       }catch (Exception e){
           e.printStackTrace();
           return (Response.buildFailure("失败"));
       }
    }

    @Override
    public Response getSimplePaperByOrg(int id) {
        try{
            String[] temp=orgDao.getPaperIdByOrg(id).split(";");
            List<Integer> paperId=new ArrayList<>();
            for(String d:temp)paperId.add(Integer.parseInt(d));
            return Response.buildSuccess(paperDao.getPapersByIds(paperId));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }



    @Override
    public Response searchOrgs(String name, String num) {
        try {
            int d=0;
            if(!num.isEmpty())d=Integer.parseInt(num);
            List<Org> orgs= orgDao.searchOrgs(name,d);
            return Response.buildSuccess(orgs);
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }


    @Override
    public Response getTopPaper(int id) {
        try{
            List<Integer> paperIds=orgDao.getTopPaperIds(id);
            List<Paper> Papers=paperDao.getPapersByIds(paperIds);
            return Response.buildSuccess(Papers);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopKeyword(int id) {
        try{
            List<String> words=orgDao.getKeywords(id);
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
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopAuthor(int id) {
        try{
            String[] temp=orgDao.getAuthors(id).split(";");
            List<String> names=new ArrayList<>();
            for(String s:temp)if(!s.isEmpty())names.add(s);
            return Response.buildSuccess(authorDao.getTopAuthor_byName(names));
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTop10Org(int mode) {
        try{
            List<Org> orgs=new ArrayList<>();
            switch (mode){
                case 1:orgs=orgDao.getTopOrg_paperNum();  // 论文总数排名
                    break;
                case 2:orgs=orgDao.getTopOrg_citationSum(); //引用总数排名
                    break;
                case 3:orgs=orgDao.getTopOrg_point();// 7 3 开
            }
            return Response.buildSuccess(orgs);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRelatedAuthors(int id) {
        try{
            return Response.buildSuccess(authorDao.getRelatedAuthor_byOrgId(id));
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRelatedOrgs(int id) {
        try{
            return Response.buildSuccess(orgDao.getRelatedOrg_byOrgId(id));
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getHistory(int id){
        try{
            List<History> histories=orgDao.getHistory(id);
            List<Integer> ans=new ArrayList<>();
            for(int i=1988;i<=2020;i++) {
                boolean find=false;
                for (History h : histories) {
                    if(h.getYear().isEmpty())continue;
                    if(Integer.parseInt(h.getYear())==i){
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
            List<String> titles=orgDao.getTitles(id);
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
