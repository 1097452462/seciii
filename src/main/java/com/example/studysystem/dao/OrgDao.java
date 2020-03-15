package com.example.studysystem.dao;

import com.example.studysystem.csv.MySQLconnection;
import com.example.studysystem.entity.SimplePaper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Mapper
@Component
public class OrgDao {

    public  List<List<String>> sortByAffiliation(){
        List<List<String>> result=new ArrayList<>();
        Connection con;
        try {
            con = MySQLconnection.getConnection();
            if (!con.isClosed()) {
                Statement statement = con.createStatement();
                String sql2="SELECT Author_Affiliations, count(*) AS num from simplepaper group by Author_Affiliations order by num DESC";
                ResultSet rs = statement.executeQuery(sql2);
                while (rs.next()) {
                    List<String> temp=new ArrayList<>();
                    if(!rs.getString(1).equals("")) {
                        temp.add(rs.getString(1));
                        temp.add(Integer.toString(rs.getInt(2)));
                        result.add(temp);
                    }
                }
                MySQLconnection.close(rs);
                MySQLconnection.close(statement);
            }
            MySQLconnection.close(con);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
