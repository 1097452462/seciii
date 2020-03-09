package com.example.studysystem.csv;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import com.example.studysystem.entity.SimplePaper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

@Mapper
@Component
public class ReadCSV {
    ArrayList<String> allreadyUpdate=new ArrayList<>();

    private boolean alreadyPlus(String name){
        if(allreadyUpdate.size()==0)return false;
        for(String s:allreadyUpdate){
            if(s.equals(name))
                return true;
        }
        return false;
    }

    public Response tranfData()
    {
        try{
            String path="src/main/resources/excel/";
            File file=new File(path);
            File[] fs=file.listFiles();
            for(File f:fs){
                //检查是否该文件已经更新
                if(alreadyPlus(f.getName()))continue;
                if(!f.isDirectory()&&f.getName().substring(f.getName().length()-4).equals(".csv")){
                    String s=f.getPath();
                    readCSV_to_MySQL(s);
                    allreadyUpdate.add(f.getName());
                }
            }
            System.out.println("插入完成！");
            return Response.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }

    }
    public static void readCSV_to_MySQL(String file_address1){
        try{
            BufferedReader reader1=new BufferedReader(new FileReader(file_address1));
            readCSV(reader1);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void insert(List<Paper> pList){
        Long start=System.currentTimeMillis();
        try {
            Connection con = MySQLconnection.getConnection();
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                String selectSQL="SELECT * FROM paper WHERE PDF_Link = \"%s\"";
                String addPaperSQL = "insert into paper(Document_title,Authors,Author_Affiliations,Publication_Title,Date_Added_To_Xplore,Publication_Year,Volume,Issue,Start_Page,End_Page,Abstract,ISSN,ISBNs,DOI,Funding_Information,PDF_Link,Author_Keywords,IEEE_Terms,INSPEC_Controlled_Terms,INSPEC_Non_Controlled_Terms,Mesh_Terms,Article_Citation_Count,Reference_Count,License,Online_Date,Issue_Date,Meeting_Date,Publisher,Document_Identifier) values (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")";
                String addSimplePaperSQL="insert into simplepaper(paper_id,Document_title,Authors,Author_Affiliations,Publication_Title,Publication_Year,Author_Keywords) values (\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")";
                for(int i=0;i<pList.size();i++){
                    Paper temp=pList.get(i);
                    String sql1=String.format(selectSQL, temp.getPDF_Link());
                    ResultSet rs1 = statement.executeQuery(sql1);
                    if(rs1.next()){
                        System.out.println("Repeat!");
                    }
                    else {
                        int key = 0;
                        String sql2 = String.format(addPaperSQL, temp.getDocument_title(), temp.getAuthors(), temp.getAuthor_Affiliations(), temp.getPublication_Title(), temp.getDate_Added_To_Xplore(), temp.getPublication_Year(), temp.getVolume(), temp.getIssue(), temp.getStart_Page(), temp.getEnd_Page(), temp.getAbstract(), temp.getISSN(), temp.getISBNs(), temp.getDOI(), temp.getFunding_Information(), temp.getPDF_Link(), temp.getAuthor_Keywords(), temp.getIEEE_Terms(), temp.getINSPEC_Controlled_Terms(), temp.getINSPEC_Non_Controlled_Terms(), temp.getMesh_Terms(), temp.getArticle_Citation_Count(), temp.getReference_Count(), temp.getLicense(), temp.getOnline_Date(), temp.getIssue_Date(), temp.getMeeting_Date(), temp.getPublisher(), temp.getDocument_Identifier());
                        if (statement.executeUpdate(sql2, statement.RETURN_GENERATED_KEYS) != 0) {
                            ResultSet rs2 = statement.getGeneratedKeys();
                            if (rs2.next()) {
                                key = rs2.getInt(1);
                                System.out.println(key);
                                System.out.println("Paper插入成功");
                                String[] authorList = deleteQuotes(temp.getAuthors()).split("; ");
                                String[] affiliationList = deleteQuotes(temp.getAuthor_Affiliations()).split("; ");
                                for (int j = 0; j < Math.min(authorList.length, affiliationList.length); j++) {
                                    String sql3 = String.format(addSimplePaperSQL, key, temp.getDocument_title(), authorList[j], affiliationList[j], temp.getPublication_Title(), temp.getPublication_Year(), temp.getAuthor_Keywords());
                                    if (statement.executeUpdate(sql3) != 0) {
                                        System.out.println("SimplePaper插入成功");
                                    } else {
                                        System.out.println("SimplePaper插入失败");
                                    }
                                }
                            }
                            MySQLconnection.close(rs2);
                        } else {
                            System.out.println("Paper插入失败");
                        }
                    }
                    MySQLconnection.close(rs1);
                }
                MySQLconnection.close(statement);
                MySQLconnection.close(con);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Long end=System.currentTimeMillis();
        System.out.println(end-start+"ms");
    }

    public static void readCSV(BufferedReader reader){
        List<Paper> pList=new ArrayList<>();
        try {
            reader.readLine();
            String line;
            while((line=reader.readLine())!=null){
                Paper p=new Paper();
                String[] info = line.substring(1,line.length()-1).split("\",\"");
                for(int i=0;i<info.length;i++){
                    info[i]=info[i]+"";
                }
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
                pList.add(p);
            }
            insert(pList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String deleteQuotes(String s){
        for(int i=0;i<s.length()-1;i++){
            if(s.charAt(i)=='"'){
                String s1=s.substring(0,i);
                String s2=s.substring(i+1);
                s=s1+"\""+s2;
            }
        }
        return s;
    }

}
