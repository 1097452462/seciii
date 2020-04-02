package com.example.studysystem.service.field;

import com.example.studysystem.dao.FieldDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.entity.Field;
import com.example.studysystem.entity.Org;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FieldServiceImpl implements FieldService{
    @Autowired FieldDao fieldDao;
    @Autowired PaperDao paperDao;

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
    public Response getTopAuthors(int id) {
        try{

            return Response.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopOrgs(int id) {
        try{

            return Response.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopPapers(int id) {
        try{
            List<Integer> paperIds=fieldDao.getTopPaperIds(id);
            List<Paper> Papers=paperDao.getPapersByIds(paperIds);
            return Response.buildSuccess(Papers);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getPaperNum(int id) {
        try{
            return Response.buildSuccess(fieldDao.getPaperNum(id));
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getCitationSum(int id) {
        try{
            return Response.buildSuccess(fieldDao.getCitationSum(id));
        }catch (Exception e) {
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
