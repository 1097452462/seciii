package com.example.studysystem.db;

import com.example.studysystem.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Mapper
@Component
public class Insert_author {
    public List<String[]> excute(List<Paper> paperList){
        Long start=System.currentTimeMillis();
        List<String[]> relation=dealRelation(paperList);
        insertAuthor(relation);
        Long end=System.currentTimeMillis();
        System.out.print("author插入完成   ");
        System.out.println(end-start+"  ms");
        return relation;
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
                if(!r[0].isEmpty()&&!r[1].isEmpty()&&!exist){
                    relation.add(r);
                }
            }
        }
        return relation;
    }

    private static void insertAuthor(List<String[]> relation){
        try{
            Connection con = MySQLconnection.getConnection();
            con.setAutoCommit(false);
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                String selectSql1="select distinct paper_id from simplepaper where Authors=\"%s\" and Author_Affiliations=\"%s\"";
                String selectSql2="select id from author where Author_name=\"%s\" and Org=\"%s\"";
                String selectSql3="SELECT sum(Article_Citation_Count) FROM paper where id in (select distinct paper_id from simplepaper where Authors=\"%s\" and Author_Affiliations=\"%s\")";
                String selectSql4="select id,Paper_num,Citation_sum from author";
                PreparedStatement p1=con.prepareStatement("insert into author(Author_name,Org,Paper_list,Paper_num,Citation_sum) values (?,?,?,?,?)");
                PreparedStatement p2=con.prepareStatement("update author set Paper_list=?,Paper_num=?,Citation_sum=? where id=?");
                PreparedStatement p3=con.prepareStatement("update author set Point=? where id=?");
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

                    int citation=0;
                    String sql3 = String.format(selectSql3, r[0], r[1]);
                    ResultSet rs3 = statement.executeQuery(sql3);
                    while(rs3.next()){citation=rs3.getInt(1);}

                    String sql2=String.format(selectSql2,r[0],r[1]);
                    rs=statement.executeQuery(sql2);
                    if(rs.next()){
                        int id=rs.getInt(1);
                        p2.setString(1,Paper_list);
                        p2.setInt(2,Paper_num);
                        p2.setInt(3,citation);
                        p2.setInt(4,id);
                        p2.addBatch();
                    }
                    else{
                        p1.setString(1,r[0]);
                        p1.setString(2,r[1]);
                        p1.setString(3,Paper_list);
                        p1.setInt(4,Paper_num);
                        p1.setInt(5,citation);
                        p1.addBatch();
                    }
                    MySQLconnection.close(rs);
                }
                p1.executeBatch();
                p2.executeBatch();
                MySQLconnection.close(p1);
                MySQLconnection.close(p2);

                String sql4 = String.format(selectSql4);
                ResultSet rs4 = statement.executeQuery(sql4);
                while(rs4.next()){
                    int id=rs4.getInt(1);
                    int num=rs4.getInt(2);
                    int citation=rs4.getInt(3);
                    float p= (float) (0.7*num+0.3*citation);
                    p3.setInt(2,id);
                    p3.setDouble(1,p);
                    p3.addBatch();
                }
                p3.executeBatch();
                MySQLconnection.close(p3);

                MySQLconnection.close(statement);
            }
            con.commit();
            con.setAutoCommit(true);
            MySQLconnection.close(con);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
