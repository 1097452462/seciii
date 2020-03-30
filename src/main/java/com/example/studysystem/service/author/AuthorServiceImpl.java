package com.example.studysystem.service.author;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Author;
import com.example.studysystem.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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
            return Response.buildSuccess(simplePaperDao.getSimplePapersByIds(paperId));
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
}
