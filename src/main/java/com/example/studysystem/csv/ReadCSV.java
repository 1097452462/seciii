package com.example.studysystem.csv;
import com.example.studysystem.entity.Author;
import com.example.studysystem.entity.Org;
import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.Response;
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
    private ArrayList<String> allreadyUpdate=new ArrayList<>();

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



    public static void readCSV(BufferedReader reader){
        List<Paper> pList=new ArrayList<>();
        HashMap<String, ArrayList<String>> Org_list_map = new HashMap<>();
        HashMap<String, ArrayList<Integer>> Author_Paper_list_map = new HashMap<>();
        HashMap<String, Integer> Author_Paper_num_map = new HashMap<>();
        HashMap<String, ArrayList<String>> Author_list_map = new HashMap<>();
        HashMap<String, ArrayList<Integer>> Org_Paper_list_map = new HashMap<>();
        HashMap<String, Integer> Org_Paper_num_map = new HashMap<>();
        try {
            reader.readLine();
            String line;
            while((line=reader.readLine())!=null){
                String[] info = line.substring(1,line.length()-1).split("\",\"");
                for(int i=0;i<info.length;i++){
                    info[i]=info[i]+"";
                }
                Paper p=dealPaper(info);
                pList.add(p);
                int paper_id=insertPaper(p);
                dealAuthor(Org_list_map,Author_Paper_list_map,Author_Paper_num_map,paper_id,info);
                dealOrg(Author_list_map,Org_Paper_list_map,Org_Paper_num_map,paper_id,info);
            }
            ArrayList<Author> authors= produceAuthor(Org_list_map,Author_Paper_list_map,Author_Paper_num_map);
            for(Author author:authors){
                if(author.getAuthor_name().isEmpty())continue;
                int author_id=insertAuthor(author);
            }
            ArrayList<Org> orgs= produceOrg(Author_list_map,Org_Paper_list_map,Org_Paper_num_map);
            for(Org org:orgs){
                if(org.getOrg_name().isEmpty())continue;
                int org_id=insertOrg(org);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int insertOrg(Org org){
        int org_id = 0;
        try {
            Connection con = MySQLconnection.getConnection();
            if (!con.isClosed()) {
                Statement statement = con.createStatement();
                String addOrgSQL = "insert into org(Org_name,Author_list,Paper_list,Paper_num) values (\"%s\",\"%s\",\"%s\",\"%d\")";
                String sql = String.format(addOrgSQL, org.getOrg_name(), org.getAuthor_list(), org.getPaper_list(), org.getPaper_num());
                if (statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS) != 0) {
                    ResultSet rs2 = statement.getGeneratedKeys();
                    if (rs2.next()) {
                        org_id = rs2.getInt(1);
                        //System.out.println(key);
                        System.out.println("Org插入成功");
                    }
                    MySQLconnection.close(rs2);
                } else {
                    System.out.println("Org插入失败");
                }
                MySQLconnection.close(statement);
                MySQLconnection.close(con);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return org_id;
    }

    private static ArrayList<Org> produceOrg(HashMap<String, ArrayList<String>> Author_list_map,
                                             HashMap<String, ArrayList<Integer>> Org_Paper_list_map,
                                             HashMap<String, Integer> Org_Paper_num_map){

        ArrayList<Org> orgs=new ArrayList<>();
        for(String Org_name:Author_list_map.keySet()){
            Org org =new Org();
            ArrayList<String> Author_list=Author_list_map.get(Org_name);
            String author_list_string="";
            for(String author:Author_list)author_list_string+=author+";";
            ArrayList<Integer> paper_list=Org_Paper_list_map.get(Org_name);
            String paper_list_string="";
            for(int paperId:paper_list)paper_list_string+=Integer.toString(paperId)+";";
            int num=Org_Paper_num_map.get(Org_name);
            org.setOrg_name(Org_name);
            org.setAuthor_list(author_list_string);
            org.setPaper_list(paper_list_string);
            org.setPaper_num(num);
            orgs.add(org);
        }
        return orgs;
    }

    private static void dealOrg(HashMap<String, ArrayList<String>> Author_list_map,
                                   HashMap<String, ArrayList<Integer>> Org_Paper_list_map,
                                   HashMap<String, Integer> Org_Paper_num_map,
                                   int paper_id, String[] info){
        String[] Author_list_list = deleteQuotes(info[1]).split("; ");
        String[] Org_name_list = deleteQuotes(info[2]).split("; ");
        for (int j = 0; j < Math.min(Org_name_list.length, Author_list_list.length); j++){
            String org=Org_name_list[j];
            String new_author=Author_list_list[j];
            ArrayList<String> Author_list=Author_list_map.get(org);
            if(Author_list==null){
                ArrayList<String> temp=new ArrayList<>();
                temp.add(new_author);
                Author_list_map.put(org,temp);
            }
            else {
                boolean find = false;
                for (int k = 0; k < Author_list.size(); k++) {
                    String author = Author_list.get(k);
                    if (author.equals(new_author)) {
                        find = true;
                        break;
                    }
                }
                if (!find) Author_list.add(new_author);
            }

            ArrayList<Integer> Paper_list=Org_Paper_list_map.get(org);
            if(Paper_list==null){
                ArrayList<Integer> temp=new ArrayList<>();
                temp.add(paper_id);
                Org_Paper_list_map.put(org,temp);
                Org_Paper_num_map.put(org,1);
            }
            else{
                boolean find2 = false;
                for (int q = 0; q < Paper_list.size(); q++) {
                    int old_paper_id = Paper_list.get(q);
                    if (old_paper_id == paper_id) {
                        find2 = true;
                        break;
                    }
                }
                if (!find2) {
                    Paper_list.add(paper_id);
                    if (Org_Paper_num_map.containsKey(org)) {
                        int value = Org_Paper_num_map.get(org);
                        Org_Paper_num_map.put(org, value + 1);
                    } else {
                        Org_Paper_num_map.put(org, 1);
                    }
                }
            }

        }
    }

    private static int insertAuthor(Author author){
        int author_id = 0;
        try {
            Connection con = MySQLconnection.getConnection();
            if (!con.isClosed()) {
                Statement statement = con.createStatement();
                String addAuthorSQL = "insert into author(Author_name,Org_list,Paper_list,Paper_num) values (\"%s\",\"%s\",\"%s\",\"%d\")";
                String sql = String.format(addAuthorSQL, author.getAuthor_name(), author.getOrg_list(), author.getPaper_list(), author.getPaper_num());
                if (statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS) != 0) {
                    ResultSet rs2 = statement.getGeneratedKeys();
                    if (rs2.next()) {
                        author_id = rs2.getInt(1);
                        //System.out.println(key);
                        System.out.println("Author插入成功");
                    }
                    MySQLconnection.close(rs2);
                } else {
                    System.out.println("Author插入失败");
                }
                MySQLconnection.close(statement);
                MySQLconnection.close(con);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return author_id;
    }

    private static ArrayList<Author> produceAuthor(HashMap<String, ArrayList<String>> Org_list_map,
                              HashMap<String, ArrayList<Integer>> Author_Paper_list_map,
                              HashMap<String, Integer> Author_Paper_num_map){

        ArrayList<Author> authors=new ArrayList<>();
        for(String Author_name:Org_list_map.keySet()){
            Author author=new Author();
            ArrayList<String> org_list=Org_list_map.get(Author_name);
            String org_list_string="";
            for(String org:org_list)org_list_string+=org+";";
            ArrayList<Integer> paper_list=Author_Paper_list_map.get(Author_name);
            String paper_list_string="";
            for(int paperId:paper_list)paper_list_string+=Integer.toString(paperId)+";";
            int num=Author_Paper_num_map.get(Author_name);
            author.setAuthor_name(Author_name);
            author.setOrg_list(org_list_string);
            author.setPaper_list(paper_list_string);
            author.setPaper_num(num);
            authors.add(author);
        }
        return authors;
    }

    private static void dealAuthor(HashMap<String, ArrayList<String>> Org_list_map,
                                   HashMap<String, ArrayList<Integer>> Paper_list_map,
                                   HashMap<String, Integer> Paper_num_map,
                                   int paper_id, String[] info){
        String[] Author_name_list = deleteQuotes(info[1]).split("; ");
        String[] Org_list_list = deleteQuotes(info[2]).split("; ");
        for (int j = 0; j < Math.min(Author_name_list.length, Org_list_list.length); j++){
            String author=Author_name_list[j];
            String new_org=Org_list_list[j];
            ArrayList<String> Org_list=Org_list_map.get(author);
            if(Org_list==null){
                ArrayList<String> temp=new ArrayList<>();
                temp.add(new_org);
                Org_list_map.put(author,temp);
            }
            else {
                boolean find = false;
                for (int k = 0; k < Org_list.size(); k++) {
                    String org = Org_list.get(k);
                    if (org.equals(new_org)) {
                        find = true;
                        break;
                    }
                }
                if (!find) Org_list.add(new_org);
            }

            ArrayList<Integer> Paper_list=Paper_list_map.get(author);
            if(Paper_list==null){
                ArrayList<Integer> temp=new ArrayList<>();
                temp.add(paper_id);
                Paper_list_map.put(author,temp);
                Paper_num_map.put(author,1);
            }
            else{
                boolean find2 = false;
                for (int q = 0; q < Paper_list.size(); q++) {
                    int old_paper_id = Paper_list.get(q);
                    if (old_paper_id == paper_id) {
                        find2 = true;
                        break;
                    }
                }
                if (!find2) {
                    Paper_list.add(paper_id);
                    if (Paper_num_map.containsKey(author)) {
                        int value = Paper_num_map.get(author);
                        Paper_num_map.put(author, value + 1);
                    } else {
                        Paper_num_map.put(author, 1);
                    }
                }
            }

        }
    }

    private static int insertPaper(Paper temp){
        Long start=System.currentTimeMillis();
        int paper_id = 0;
        try {
            Connection con = MySQLconnection.getConnection();
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                String selectSQL = "SELECT * FROM paper WHERE PDF_Link = \"%s\"";
                String addPaperSQL = "insert into paper(Document_title,Authors,Author_Affiliations,Publication_Title,Date_Added_To_Xplore,Publication_Year,Volume,Issue,Start_Page,End_Page,Abstract,ISSN,ISBNs,DOI,Funding_Information,PDF_Link,Author_Keywords,IEEE_Terms,INSPEC_Controlled_Terms,INSPEC_Non_Controlled_Terms,Mesh_Terms,Article_Citation_Count,Reference_Count,License,Online_Date,Issue_Date,Meeting_Date,Publisher,Document_Identifier) values (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")";
                String addSimplePaperSQL = "insert into simplepaper(paper_id,Document_title,Authors,Author_Affiliations,Publication_Title,Publication_Year,Author_Keywords) values (\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")";

                //Paper temp=pList.get(i);
                String sql1 = String.format(selectSQL, temp.getPDF_Link());
                ResultSet rs1 = statement.executeQuery(sql1);
                if (rs1.next()) {
                    System.out.println("Repeat!");
                } else {
                    int key = 0;
                    String sql2 = String.format(addPaperSQL, temp.getDocument_title(), temp.getAuthors(), temp.getAuthor_Affiliations(), temp.getPublication_Title(), temp.getDate_Added_To_Xplore(), temp.getPublication_Year(), temp.getVolume(), temp.getIssue(), temp.getStart_Page(), temp.getEnd_Page(), temp.getAbstract(), temp.getISSN(), temp.getISBNs(), temp.getDOI(), temp.getFunding_Information(), temp.getPDF_Link(), temp.getAuthor_Keywords(), temp.getIEEE_Terms(), temp.getINSPEC_Controlled_Terms(), temp.getINSPEC_Non_Controlled_Terms(), temp.getMesh_Terms(), temp.getArticle_Citation_Count(), temp.getReference_Count(), temp.getLicense(), temp.getOnline_Date(), temp.getIssue_Date(), temp.getMeeting_Date(), temp.getPublisher(), temp.getDocument_Identifier());
                    if (statement.executeUpdate(sql2, statement.RETURN_GENERATED_KEYS) != 0) {
                        ResultSet rs2 = statement.getGeneratedKeys();
                        if (rs2.next()) {
                            key = rs2.getInt(1);
                            paper_id=key;
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
                MySQLconnection.close(statement);
                MySQLconnection.close(con);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Long end=System.currentTimeMillis();
        //System.out.println(end-start+"ms");
        return paper_id;
    }

    private static Paper dealPaper(String[] info){//System.out.println(190);
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
        p.setDocument_Identifier(deleteQuotes(info[28]));//System.out.println(p.getAuthors());
        return p;
    }

    private static String deleteQuotes(String s){
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
