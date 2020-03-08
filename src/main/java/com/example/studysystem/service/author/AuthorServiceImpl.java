package com.example.studysystem.service.author;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private SimplePaperDao simplePaperDao;

    @Override
    public Response getSimplePaperByOrg(String name) {
        try{
            return Response.buildSuccess(simplePaperDao.getSimplePaperByAuthor(name));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response orderAuthors() {
        try{
            return Response.buildSuccess(authorDao.sortByAuthor());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response searchAuthors(String name, String num) {
        try {
            List<List<String>> authors = authorDao.sortByAuthor();
            List<List<String>> ans = new ArrayList<>();
            for (List<String> t : authors) {
                boolean flag1 = true, flag2 = true;
                if (!name.isEmpty()) {
                    //System.out.println(t.get(0).toLowerCase() +"   "+name.toLowerCase());
                    /*if (!t.get(0).toLowerCase().contains(name.toLowerCase())) {
                        flag1 = false;
                    }*/
                    if (!name.isEmpty()) {
                        String temp = name.toLowerCase();
                        temp.replaceAll(";", " ");
                        String list[] = temp.split(" ");
                        for (String x : list) {
                            if (!t.get(0).toLowerCase().contains(x)) {
                                flag1 = false;
                                break;
                            }
                        }
                    }
                }
                if (!flag1) continue;
                if (!num.isEmpty()) {
                    if (Integer.parseInt(t.get(1)) < Integer.parseInt(num)) flag2 = false;
                }
                if (flag2) ans.add(t);

            }System.out.println(ans.size());
            return Response.buildSuccess(ans);
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
}
