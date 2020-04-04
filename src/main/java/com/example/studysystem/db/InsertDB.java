package com.example.studysystem.db;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

@Mapper
@Component
public class InsertDB {

    private ArrayList<String> allreadyUpdate=new ArrayList<>();

    @Autowired
    private Insert_field insert_field;
    @Autowired
    private Insert_paper insert_paper;
    @Autowired
    private Insert_author insert_author;
    @Autowired
    private Insert_org insert_org;

    private boolean alreadyPlus(String name){
        if(allreadyUpdate.size()==0) {
            return false;
        }
        for(String s:allreadyUpdate){
            if(s.equals(name))
                return true;
        }
        return false;
    }

    public Response tranfData() {
        try{/*
            List<Paper> papers=new ArrayList<>();
            String path="src/main/resources/excel/";
            File file=new File(path);
            File[] fs=file.listFiles();
            for(File f:fs){
                //检查是否该文件已经更新
                if(alreadyPlus(f.getName()))continue;
                if(!f.isDirectory()&&f.getName().substring(f.getName().length()-4).equals(".csv")){
                    String s=f.getPath();
                    allreadyUpdate.add(f.getName());
                    readCSV_to_MySQL(s,papers);
                }
            }
            insert_paper.excute(papers);
            List<String[]> relation= insert_author.excute(papers);
            insert_org.excute(relation);*/



            insert_field.excute();

            System.out.println("插入完成！");
            return Response.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }



    public void readCSV_to_MySQL(String file_address1,List<Paper> papers){
        try{
            BufferedReader reader=new BufferedReader(new FileReader(file_address1));
            reader.readLine();
            String line;
            while((line=reader.readLine())!=null){
                String[] info = line.substring(1,line.length()-1).split("\",\"");
                for(int i=0;i<info.length;i++){
                    info[i]=info[i]+"";
                }
                Paper p=dealPaper(info);
                if(legalPaper(p))
                    papers.add(p);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean legalPaper(Paper paper){
        if(paper.getStart_Page().length()>5||paper.getEnd_Page().length()>5)
            return false;
        return true;
    }

    public Paper dealPaper(String[] info){
        Paper p=new Paper();
        p.setDocument_title(deleteQuotes(info[0]));
        p.setAuthors(deleteQuotes(info[1]));
        p.setAuthor_Affiliations(deleteQuotes(info[2]));
        p.setPublication_Title(deleteQuotes(info[3]));
        p.setDate_Added_To_Xplore(deleteQuotes(info[4]));
        p.setPublication_Year(deleteQuotes(info[5]));
        p.setVolume(deleteQuotes(info[6]));
        p.setIssue(deleteQuotes(info[7]));
        p.setStart_Page(deleteQuotes(info[8]));
        p.setEnd_Page(deleteQuotes(info[9]));
        p.setAbstract(deleteQuotes(info[10]));
        p.setISSN(deleteQuotes(info[11]));
        p.setISBNs(deleteQuotes(info[12]));
        p.setDOI(deleteQuotes(info[13]));
        p.setFunding_Information(deleteQuotes(info[14]));
        p.setPDF_Link(deleteQuotes(info[15]));
        p.setAuthor_Keywords(deleteQuotes(info[16]));
        p.setIEEE_Terms(deleteQuotes(info[17]));
        p.setINSPEC_Controlled_Terms(deleteQuotes(info[18]));
        p.setINSPEC_Non_Controlled_Terms(deleteQuotes(info[19]));
        p.setMesh_Terms(deleteQuotes(info[20]));
        p.setArticle_Citation_Count(deleteQuotes(info[21]));
        p.setReference_Count(deleteQuotes(info[22]));
        p.setLicense(deleteQuotes(info[23]));
        p.setOnline_Date(deleteQuotes(info[24]));
        p.setIssue_Date(deleteQuotes(info[25]));
        p.setMeeting_Date(deleteQuotes(info[26]));
        p.setPublisher(deleteQuotes(info[27]));
        p.setDocument_Identifier(deleteQuotes(info[28]));
        return p;
    }

    public String deleteQuotes(String s){
        return s.replace('"','/');
    }

    public List<Paper> ignoreRepeat(List<Paper> paperList){
        List<Paper> temp=paperList;
        for(int i=0;i<temp.size()-1;i++){
            for(int j=i+1;j<temp.size();j++){
                if(temp.get(i).getPDF_Link().equals(temp.get(j).getPDF_Link())){
                    System.out.println("repeat!");
                    paperList.remove(i);
                    break;
                }
            }
        }
        try{
            Connection con = MySQLconnection.getConnection();
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                String selectSQL = "SELECT id FROM paper WHERE PDF_Link = \"%s\"";
                for(int i=paperList.size()-1;i>=0;i--){
                    Paper p=paperList.get(i);
                    String sql = String.format(selectSQL, p.getPDF_Link());
                    ResultSet rs = statement.executeQuery(sql);
                    if (rs.next()) {
                        System.out.println("repeat!");
                        paperList.remove(i);
                    }
                }
                MySQLconnection.close(statement);
            }
            MySQLconnection.close(con);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return paperList;
    }
}
