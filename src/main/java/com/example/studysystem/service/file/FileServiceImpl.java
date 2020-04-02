package com.example.studysystem.service.file;

import com.example.studysystem.db.InsertDB;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.db.Insert_field;
import com.example.studysystem.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    @Autowired
    private InsertDB insertDB;
    @Autowired
    private Insert_field insert_field;

    @Override
    public Response getFiles() {
        try{
            List<String> files=new ArrayList<>();
            String path="src/main/resources/excel/";
            File file=new File(path);
            File[] fs=file.listFiles();
            for(File f:fs) {
                files.add(f.getName());
            }
            return Response.buildSuccess(files);
        }
        catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response addFile(MultipartFile file) {//System.out.println("24");
        try{
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf('.'));
            String newFileName = new Date().getTime() + suffix;//System.out.println(newFileName);
            File directory = new File("./");
            String path2=directory.getAbsolutePath();
            path2=path2.substring(0,path2.length()-1);
            File newFile = new File(path2 + "src/main/resources/excel/" + fileName);
            try {
                file.transferTo(newFile);
            }
            catch (Exception e){
                e.printStackTrace();
                return (Response.buildFailure("失败"));
            }
            return Response.buildSuccess("上传文件成功");
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response updateDB(){
        try{
            insertDB.tranfData();
            return Response.buildSuccess("数据库更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
}
