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
public class Insert_org {

    public void excute(List<String[]> relation){
        Long start=System.currentTimeMillis();
        List<String> orgList=dealOrg(relation);
        insertOrg(orgList);
        Long end=System.currentTimeMillis();
        System.out.print("org插入完成   ");
        System.out.println(end-start+"  ms");
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
                String selectSql4="SELECT sum(Article_Citation_Count) FROM paper where id in (select distinct paper_id from simplepaper where Author_Affiliations=\"%s\")";
                String selectSql5="select id,Paper_num,Citation_sum from org";
                PreparedStatement p1=con.prepareStatement("insert into org(Org_name,Author_list,Paper_list,Paper_num,Author_num,Citation_sum)values(?,?,?,?,?,?)");
                PreparedStatement p2=con.prepareStatement("update org set Author_list=?,Paper_list=?,Paper_num=?,Author_num=?,Citation_sum=? where id=?");
                PreparedStatement p3=con.prepareStatement("update org set Point=? where id=?");
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

                    int citation=0;
                    String sql4 = String.format(selectSql4, orgList.get(i));
                    ResultSet rs3 = statement.executeQuery(sql4);
                    while(rs3.next()){citation=rs3.getInt(1);}

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
                        p2.setInt(5,citation);
                        p2.setInt(6, id);
                        p2.addBatch();
                    } else {
//                        String sql4=String.format(insertSql,orgList.get(i),Author_list,Paper_list,Paper_num,Author_num);
//                        statement.execute(sql4);
                        p1.setString(1, orgList.get(i));
                        p1.setString(2, Author_list);
                        p1.setString(3, Paper_list);
                        p1.setInt(4, Paper_num);
                        p1.setInt(5, Author_num);
                        p1.setInt(6,citation);
                        p1.addBatch();
                    }
                    MySQLconnection.close(rs);
                }
                p1.executeBatch();
                p2.executeBatch();
                MySQLconnection.close(p1);
                MySQLconnection.close(p2);

                String sql5 = String.format(selectSql5);
                ResultSet rs5 = statement.executeQuery(sql5);
                while(rs5.next()){
                    int id=rs5.getInt(1);
                    int num=rs5.getInt(2);
                    int citation=rs5.getInt(3);
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

    private static List<String> dealOrg(List<String[]> relation){
        List<String> orgList=new ArrayList<>();
        for(int i=0;i<relation.size();i++){
            if(!orgList.contains(relation.get(i)[1])){
                orgList.add(relation.get(i)[1]);
            }
        }
        return orgList;
    }
}
