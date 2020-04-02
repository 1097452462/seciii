package com.example.studysystem.service.field;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public class FieldServiceImpl implements FieldService{
    @Override
    public Response getFields() {
        try{

            return Response.buildSuccess();
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

            return Response.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
}
