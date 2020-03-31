package com.example.studysystem.db;
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
public class InsertDB {

    private ArrayList<String> allreadyUpdate=new ArrayList<>();

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
        try{
            List<List<Paper>> allPapers=new ArrayList<>();
            Map<String,String> paperField=new HashMap<>();
            String path="src/main/resources/excel/";
            File file=new File(path);
            File[] fs=file.listFiles();
            for(File f:fs){
                //检查是否该文件已经更新
                if(alreadyPlus(f.getName()))continue;
                if(!f.isDirectory()&&f.getName().substring(f.getName().length()-4).equals(".csv")){
                    String s=f.getPath();
                    readCSV_to_MySQL(s,allPapers);
                    allreadyUpdate.add(f.getName());
                }
            }
            resolveField(allPapers,paperField,10);
            insertFields(paperField);

            System.out.println("插入完成！");
            return Response.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    private static void insertFields(Map<String,String> paperField){
        try{
            Connection con = MySQLconnection.getConnection();
            con.setAutoCommit(false);
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                PreparedStatement p1=con.prepareStatement("insert into field(Field_name,Paper_list,Paper_num)values(?,?,?)");
                for(Map.Entry<String,String> a:paperField.entrySet()){
                    p1.setString(1,a.getKey());
                    p1.setString(2,a.getValue());
                    int n=a.getValue().length()-a.getValue().replaceAll(";","").length();
                    p1.setInt(3,n);
                    p1.addBatch();
                }
                p1.executeBatch();
                MySQLconnection.close(p1);
                MySQLconnection.close(statement);
            }
            con.commit();
            con.setAutoCommit(true);
            MySQLconnection.close(con);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("field插入成功");
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static void resolveField(List<List<Paper>> allPapers, Map<String,String> paperField,int cnt){
        List<String> fields=new ArrayList<>();
        Map<String,Integer> map=new TreeMap<>();
        for(List<Paper> papers:allPapers){
            for(Paper p:papers){
                for(String s:p.getAuthor_Keywords().split(" ")){
                    if(s.isEmpty())continue;
                    s=s.toUpperCase();
                    if(map.containsKey(s))map.put(s,map.get(s)+1);
                    else map.put(s,1);
                }
            }
        }
        map=sortByValueDescending(map);
        int n=0;
        for(Map.Entry<String,Integer> a:map.entrySet()){
            fields.add(a.getKey());
            paperField.put(a.getKey(),"");
            n++;
            if(n==cnt)break;
        }
        for(List<Paper> papers:allPapers){
            for(Paper p:papers){
                for(String s:p.getAuthor_Keywords().split(" ")){
                    s=s.toUpperCase();
                    for(Map.Entry<String,String> a:paperField.entrySet()){
                        if(a.getKey().equals(s)){
                            String value=a.getValue();
                            value+=Integer.toString(p.getId())+";";
                            paperField.put(a.getKey(),value);
                        }
                    }
                }
            }
        }
    }

    private static void readCSV_to_MySQL(String file_address1,List<List<Paper>> allPapers){
        try{
            BufferedReader reader=new BufferedReader(new FileReader(file_address1));
            List<Paper> paperList=new ArrayList<>();
            /*我必须重写插入author表和org表的方法*/
            List<String[]> relation;
            List<String> orgs;
            /**/
            reader.readLine();
            String line;
            while((line=reader.readLine())!=null){
                String[] info = line.substring(1,line.length()-1).split("\",\"");
                for(int i=0;i<info.length;i++){
                    info[i]=info[i]+"";
                }
                Paper p=dealPaper(info);
                paperList.add(p);
            }
            Long start=System.currentTimeMillis();
            paperList=ignoreRepeat(paperList);/*查重*/
            allPapers.add(paperList);
            relation=dealRelation(paperList);
            orgs=dealOrg(relation);
            insertPaperAndSimplePaper(paperList);/*插入paper和simplepaper*/
            insertAuthor(relation);/*插入author*/
            insertOrg(orgs);/*插入orgs*/
            Long end=System.currentTimeMillis();
            System.out.println("用时"+(end-start)+"ms");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static Paper dealPaper(String[] info){
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

    private static String deleteQuotes(String s){
        return s.replace('"','/');
    }

    private static List<Paper> ignoreRepeat(List<Paper> paperList){
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

    private static List<String[]> dealRelation(List<Paper> paperList){
        List<String[]> relation=new ArrayList<>();
        for(int i=0;i<paperList.size();i++){
            String[] authors=paperList.get(i).getAuthors().split("; ");
            String[] orgs=paperList.get(i).getAuthor_Affiliations().split("; ");
            for(int j=0;j<Math.min(authors.length,orgs.length);j++){
                String[] r=new String[2];
                r[0]=authors[j];
                r[1]=orgs[j];
                boolean exist=false;
                for(int k=0;k<relation.size();k++){
                    if(relation.get(k)[0].equals(r[0])&&relation.get(k)[1].equals(r[1])){
                        exist=true;
                    }
                }
                if(!r[0].isEmpty()&&!exist){
                    relation.add(r);
                }
            }
        }
        return relation;
    }

    private static List<String> dealOrg(List<String[]> relation){
        List<String> orgList=new ArrayList<>();
        for(int i=0;i<relation.size();i++){
            if(!orgList.contains(relation.get(i)[1])){
                orgList.add(relation.get(i)[1]);
            }
        }
        return orgList;
    }

    private static void insertPaperAndSimplePaper(List<Paper> paperList){
        int paper_id = 0;
        try {
            Connection con = MySQLconnection.getConnection();
            con.setAutoCommit(false);
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                String addPaperSQL = "insert into paper(Document_title,Authors,Author_Affiliations,Publication_Title,Date_Added_To_Xplore,Publication_Year,Volume,Issue,Start_Page,End_Page,Abstract,ISSN,ISBNs,DOI,Funding_Information,PDF_Link,Author_Keywords,IEEE_Terms,INSPEC_Controlled_Terms,INSPEC_Non_Controlled_Terms,Mesh_Terms,Article_Citation_Count,Reference_Count,License,Online_Date,Issue_Date,Meeting_Date,Publisher,Document_Identifier) values (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")";
//                String addSimplePaperSQL = "insert into simplepaper(paper_id,Document_title,Authors,Author_Affiliations,Publication_Title,Publication_Year,Author_Keywords) values (\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")";
                PreparedStatement p2=con.prepareStatement("insert into simplepaper(paper_id,Document_title,Authors,Author_Affiliations,Publication_Title,Publication_Year,Author_Keywords) values (?,?,?,?,?,?,?)");
                for (int i = 0; i < paperList.size(); i++) {
                    int key = 0;
                    Paper p = paperList.get(i);
                    String sql1 = String.format(addPaperSQL, p.getDocument_title(), p.getAuthors(), p.getAuthor_Affiliations(), p.getPublication_Title(), p.getDate_Added_To_Xplore(), p.getPublication_Year(), p.getVolume(), p.getIssue(), p.getStart_Page(), p.getEnd_Page(), p.getAbstract(), p.getISSN(), p.getISBNs(), p.getDOI(), p.getFunding_Information(), p.getPDF_Link(), p.getAuthor_Keywords(), p.getIEEE_Terms(), p.getINSPEC_Controlled_Terms(), p.getINSPEC_Non_Controlled_Terms(), p.getMesh_Terms(), p.getArticle_Citation_Count(), p.getReference_Count(), p.getLicense(), p.getOnline_Date(), p.getIssue_Date(), p.getMeeting_Date(), p.getPublisher(), p.getDocument_Identifier());
                    if (statement.executeUpdate(sql1, statement.RETURN_GENERATED_KEYS) != 0) {
                        ResultSet rs = statement.getGeneratedKeys();
                        if (rs.next()) {
                            key = rs.getInt(1);
                            p.setId(key);
                            String[] authorList = deleteQuotes(p.getAuthors()).split("; ");
                            String[] affiliationList = deleteQuotes(p.getAuthor_Affiliations()).split("; ");
                            for (int j = 0; j < Math.min(authorList.length, affiliationList.length); j++) {
//                                String sql2 = String.format(addSimplePaperSQL, key, p.getDocument_title(), authorList[j], affiliationList[j], p.getPublication_Title(), p.getPublication_Year(), p.getAuthor_Keywords());
//                                statement.execute(sql2);
                                p2.setInt(1,key);
                                p2.setString(2,p.getDocument_title());
                                p2.setString(3,authorList[j]);
                                p2.setString(4,affiliationList[j]);
                                p2.setString(5,p.getPublication_Title());
                                p2.setString(6,p.getPublication_Year());
                                p2.setString(7,p.getAuthor_Keywords());
                                p2.addBatch();
                            }
                        }
                        MySQLconnection.close(rs);
                    }
                }
                p2.executeBatch();
                MySQLconnection.close(p2);
                MySQLconnection.close(statement);
            }
            con.commit();
            con.setAutoCommit(true);
            MySQLconnection.close(con);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("paper插入成功"+System.lineSeparator()+"simplepaper插入成功");
    }

    private static void insertAuthor(List<String[]> relation){
        try{
            Connection con = MySQLconnection.getConnection();
            con.setAutoCommit(false);
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                String selectSql1="select paper_id from simplepaper where Authors=\"%s\" and Author_Affiliations=\"%s\"";
                String selectSql2="select id from author where Author_name=\"%s\" and Org=\"%s\"";
//                String insertSql="insert into author(Author_name,Org,Paper_list,Paper_num) values (\"%s\",\"%s\",\"%s\",\"%d\")";
//                String updateSql="update author set Paper_list=\"%s\",Paper_num=\"%d\" where id=\"%d\"";
                PreparedStatement p1=con.prepareStatement("insert into author(Author_name,Org,Paper_list,Paper_num) values (?,?,?,?)");
                PreparedStatement p2=con.prepareStatement("update author set Paper_list=?,Paper_num=? where id=?");
                for(int i=0;i<relation.size();i++) {
                    String[] r = relation.get(i);
                    String sql1 = String.format(selectSql1, r[0], r[1]);
                    ResultSet rs = statement.executeQuery(sql1);
                    int Paper_num = 1;
                    String Paper_list="";
                    while (rs.next()) {
                        if(!rs.isLast()){
                            Paper_list=Paper_list+rs.getString(1)+";";
                        }
                        else{
                            Paper_list=Paper_list+rs.getString(1);
                        }
                    }
                    for (int j = 0; j < Paper_list.length(); j++) {
                        if (Paper_list.charAt(j) == ';') {
                            Paper_num++;
                        }
                    }

                    String sql2=String.format(selectSql2,r[0],r[1]);
                    rs=statement.executeQuery(sql2);
                    if(rs.next()){
                        int id=rs.getInt(1);
                        p2.setString(1,Paper_list);
                        p2.setInt(2,Paper_num);
                        p2.setInt(3,id);
                        p2.addBatch();
//                        String sql3=String.format(updateSql,Paper_list,Paper_num,id);
//                        statement.execute(sql3);
                    }
                    else{
                        p1.setString(1,r[0]);
                        p1.setString(2,r[1]);
                        p1.setString(3,Paper_list);
                        p1.setInt(4,Paper_num);
//                        String sql3=String.format(insertSql,r[0],r[1],Paper_list,Paper_num);
//                        statement.execute(sql3);
                        p1.addBatch();
                    }
                    MySQLconnection.close(rs);
                }
                p1.executeBatch();
                p2.executeBatch();
                MySQLconnection.close(p1);
                MySQLconnection.close(p2);
                MySQLconnection.close(statement);
            }
            con.commit();
            con.setAutoCommit(true);
            MySQLconnection.close(con);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("author插入成功");
    }

    private static void insertOrg(List<String> orgList){
        try{
            Connection con = MySQLconnection.getConnection();
            con.setAutoCommit(false);
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                String selectSql1="select distinct paper_id from simplepaper where Author_Affiliations=\"%s\"";
                String selectSql2="select Authors from simplepaper where Author_Affiliations=\"%s\"";
                String selectSql3="select id from org where Org_name=\"%s\"";
//                String insertSql="insert into org(Org_name,Author_list,Paper_list,Paper_num,Author_num)values(\"%s\",\"%s\",\"%s\",\"%d\",\"%d\")";
//                String updateSql="update org set Author_list=\"%s\",Paper_list=\"%s\",Paper_num=\"%d\",Author_num=\"%d\" where id=\"%d\"";
                PreparedStatement p1=con.prepareStatement("insert into org(Org_name,Author_list,Paper_list,Paper_num,Author_num)values(?,?,?,?,?)");
                PreparedStatement p2=con.prepareStatement("update org set Author_list=?,Paper_list=?,Paper_num=?,Author_num=? where id=?");
                for(int i=0;i<orgList.size();i++) {
                    String Paper_list = "";
                    int Paper_num = 1;
                    String Author_list = "";
                    int Author_num = 1;

                    String sql1 = String.format(selectSql1, orgList.get(i));
                    ResultSet rs = statement.executeQuery(sql1);
                    while (rs.next()) {
                        if (!rs.isLast()) {
                            Paper_list = Paper_list + rs.getString(1) + ";";
                        } else {
                            Paper_list = Paper_list + rs.getString(1);
                        }
                    }
                    for (int j = 0; j < Paper_list.length(); j++) {
                        if (Paper_list.charAt(j) == ';') {
                            Paper_num++;
                        }
                    }

                    String sql2 = String.format(selectSql2, orgList.get(i));
                    rs = statement.executeQuery(sql2);
                    while (rs.next()) {
                        if (!rs.isLast()) {
                            Author_list = Author_list + rs.getString(1) + ";";
                        } else {
                            Author_list = Author_list + rs.getString(1);
                        }
                    }
                    for (int j = 0; j < Author_list.length(); j++) {
                        if (Author_list.charAt(j) == ';') {
                            Author_num++;
                        }
                    }

                    String sql3 = String.format(selectSql3, orgList.get(i));
                    rs = statement.executeQuery(sql3);
                    if (rs.next()) {
                        int id = rs.getInt(1);
//                        String sql4=String.format(updateSql,Author_list,Paper_list,Paper_num,Author_num,id);
//                        statement.execute(sql4);
                        p2.setString(1, Author_list);
                        p2.setString(2, Paper_list);
                        p2.setInt(3, Paper_num);
                        p2.setInt(4, Author_num);
                        p2.setInt(5, id);
                        p2.addBatch();
                    } else {
//                        String sql4=String.format(insertSql,orgList.get(i),Author_list,Paper_list,Paper_num,Author_num);
//                        statement.execute(sql4);
                        p1.setString(1, orgList.get(i));
                        p1.setString(2, Author_list);
                        p1.setString(3, Paper_list);
                        p1.setInt(4, Paper_num);
                        p1.setInt(5, Author_num);
                        p1.addBatch();
                    }
                    MySQLconnection.close(rs);
                }
                p1.executeBatch();
                p2.executeBatch();
                MySQLconnection.close(p1);
                MySQLconnection.close(p2);
                MySQLconnection.close(statement);
            }
            con.commit();
            con.setAutoCommit(true);
            MySQLconnection.close(con);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("org插入成功");
    }

}
