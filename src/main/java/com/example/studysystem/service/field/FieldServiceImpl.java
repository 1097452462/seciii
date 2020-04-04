package com.example.studysystem.service.field;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.FieldDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FieldServiceImpl implements FieldService{
    @Autowired FieldDao fieldDao;
    @Autowired PaperDao paperDao;
    @Autowired AuthorDao authorDao;
    @Autowired OrgDao orgDao;
    public void set(FieldDao fieldDao,PaperDao paperDao,OrgDao orgDao){this.fieldDao=fieldDao;this.paperDao=paperDao;this.orgDao=orgDao;}
    @Override
    public Response getFields() {
        try{
            return Response.buildSuccess(fieldDao.getFields());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getFieldById(int id) {
        try {
            return Response.buildSuccess(fieldDao.getFieldById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
    @Override
    public Response getTopAuthors(int id) {//在改方向发文章综合排名前五的作者
        try{
            String[] temp=fieldDao.getPaperId(id).split(";");
            List<Integer> ids=new ArrayList<>();
            for(String s:temp)if(!s.isEmpty())ids.add(Integer.parseInt(s));
            List<Integer> authorIDs=new ArrayList<>();
            if(ids.size()>0)
                  authorIDs=  fieldDao.getAuthorId(ids);
            List<Author> authors=new ArrayList<>();
            if(authorIDs.size()>0)
                authors= authorDao.getTopAuthor(authorIDs);
            return Response.buildSuccess(authors);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopOrgs(int id) {//在改方向发文章综合排名前五的机构
        try{
            String[] temp=fieldDao.getPaperId(id).split(";");
            List<Integer> ids=new ArrayList<>();
            for(String s:temp)if(!s.isEmpty())ids.add(Integer.parseInt(s));
            List<Integer> orgIDs=new ArrayList<>();
            if(ids.size()>0)
                orgIDs=fieldDao.getOrgId(ids);
            List<Org> orgs=new ArrayList<>();
            if(orgIDs.size()>0)
                orgs=orgDao.getTopOrg(orgIDs);
            return Response.buildSuccess(orgs);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopPapers(int id) {//某方向综合排名前5论文
        try{
            String[] temp=fieldDao.getPaperId(id).split(";");
            List<Integer> ids=new ArrayList<>();
            for(String s:temp)if(!s.isEmpty())ids.add(Integer.parseInt(s));
            List<Paper> Papers=paperDao.getTopPaper(ids);
            return Response.buildSuccess(Papers);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }


    @Override
    public Response getTop10Field(int mode) {
        try{
            List<Field> fields=new ArrayList<>();
            switch (mode){
                case 1:fields=fieldDao.getTopField_paperNum();  // 论文总数排名
                    break;
                case 2:fields=fieldDao.getTopField_citationSum(); //引用总数排名
                    break;
                case 3:fields=fieldDao.getTopField_point();// 7 3 开
            }
            return Response.buildSuccess(fields);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }


}
