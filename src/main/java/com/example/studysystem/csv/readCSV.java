package com.example.studysystem.csv;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Mapper
@Component
public class readCSV {
    //用与记录哪些文件以及插入到数据库，避免重复
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
                    String s=f.getPath();//System.out.println(s);
                    readCSV_to_MySQL(s);
                    allreadyUpdate.add(f.getName());
                }
            }

            return Response.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }

    }
    public static void readCSV_to_MySQL(String file_address1){
       try{
            BufferedReader reader1=new BufferedReader(new FileReader(file_address1));
            List<Paper> ase=readCSV(reader1);
            for(int i=0;i<ase.size();i++){
               Paper p=ase.get(i);
               Add(p);
           }
       }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static List<Paper> Select(String stuid) {
            ArrayList<Paper> stu = new ArrayList<>();
            Connection con;//声明一个连接对象
            //遍历查询结果集
            try {
                con = MySQLconnection.getConnection();//1.调用方法返回连接
                if(!con.isClosed())
                    System.out.println("Succeeded connecting to the Database!");
                Statement statement = con.createStatement(); //2.创建statement类对象，用来执行SQL语句！！
                String sql = "SELECT * FROM paper WHERE Document_title = \"%s\"";//要执行的SQL语句
                ResultSet rs = statement.executeQuery(String.format(sql,stuid));
                while(rs.next()){
                    Paper p=new Paper();
                    p.setId(rs.getInt("Paper_id"));
                    p.setDocument_title(rs.getString("Document_title"));
                    stu.add(p);
                    //stu.add(rs.getString("stuid").trim());
                   // stu.add(rs.getString("name").trim());
                }
                MySQLconnection.close(rs);
                MySQLconnection.close(statement);
                MySQLconnection.close(con);
                for(int i=0;i<stu.size();i++){
                    System.out.print("id:");
                    System.out.println(stu.get(i).getId());
                }
                return stu;
            }	catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return stu;
        }
    public static List<Paper> readCSV(BufferedReader reader){
        List<Paper> docs=new ArrayList<Paper>();
        try {
            reader.readLine();
            String line;
            int ik=0;
            while((line=reader.readLine())!=null){
                Paper p=new Paper();
                String[] info = line.substring(1,line.length()-1).split("\",\"");
                for(int i=0;i<info.length;i++){
                    info[i]=info[i]+"";
                }
                ik++;
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
                docs.add(p);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return docs;
    }
    public static void Add(Paper p) {
        Connection con;//声明一个连接对象
        //遍历查询结果集
        try {
            con = MySQLconnection.getConnection();//1.调用方法返回连接
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            Statement statement = con.createStatement(); //2.创建statement类对象，用来执行SQL语句！！
            String sql="insert into paper(Document_title,Authors,Author_Affiliations,Publication_Title,Date_Added_To_Xplore,Publication_Year,Volume,Issue,Start_Page,End_Page,Abstract,ISSN,ISBNs,DOI,Funding_Information,PDF_Link,Author_Keywords,IEEE_Terms,INSPEC_Controlled_Terms,INSPEC_Non_Controlled_Terms,Mesh_Terms,Article_Citation_Count,Reference_Count,License,Online_Date,Issue_Date,Meeting_Date,Publisher,Document_Identifier) values (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")";
            //要执行的SQL语句
            if(statement.executeUpdate(String.format(sql,p.getDocument_title(),p.getAuthors(),p.getAuthor_Affiliations(),p.getPublication_Title(),p.getDate_Added_To_Xplore(),p.getPublication_Year(),p.getVolume(),p.getIssue(),p.getStart_Page(),p.getEnd_Page(),p.getAbstract(),p.getISSN(),p.getISBNs(),p.getDOI(),p.getFunding_Information(),p.getPDF_Link(),p.getAuthor_Keywords(),p.getIEEE_Terms(),p.getINSPEC_Controlled_Terms(),p.getINSPEC_Non_Controlled_Terms(),p.getMesh_Terms(),p.getArticle_Citation_Count(),p.getReference_Count(),p.getLicense(),p.getOnline_Date(),p.getIssue_Date(),p.getMeeting_Date(),p.getPublisher(),p.getDocument_Identifier()))!=0){
                System.out.println("插入成功");}
            else
                System.out.println("插入失败");
            MySQLconnection.close(statement);
            MySQLconnection.close(con);
        }	catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public static String deleteQuotes(String s){
//        for(int i=0;i<s.length()-1;i++){
//            if(s.charAt(i)=='"'&&s.charAt(i+1)=='"'){
//                String s1=s.substring(0,i+1);
//                String s2=s.substring(i+2);
//                s=s1+s2;
//                i--;
//            }
//        }
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
