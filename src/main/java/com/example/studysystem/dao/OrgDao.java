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

        List<SimplePaper> kkk=new ArrayList<>();
        Connection con;
        try{
            con = MySQLconnection.getConnection();
            if(!con.isClosed()){
                Statement statement = con.createStatement();
                String sql="SELECT * FROM simplepaper";
                ResultSet rs=statement.executeQuery(sql);
                while(rs.next()){
                    SimplePaper sp=new SimplePaper();
                    sp.setPaper_id(rs.getInt("paper_id"));
//                    System.out.println(rs.getInt("paper_id"));
                    sp.setAuthor_Affiliations(rs.getString("Author_Affiliations"));
                    kkk.add(sp);
                }
                MySQLconnection.close(rs);
                MySQLconnection.close(statement);
                MySQLconnection.close(con);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        HashMap<String, Integer> yyy=new HashMap<>();

        for(int i=0;i<kkk.size();i++){
            String s=kkk.get(i).getAuthor_Affiliations();
            if(yyy.containsKey(s)){
                int value=yyy.get(s);
                yyy.put(s,value+1);
            }
            else{
                yyy.put(s,1);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(yyy.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<List<String>> orgs=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getKey().isEmpty())continue;
            List<String> temp=new ArrayList<>();
            temp.add(list.get(i).getKey());
            temp.add(list.get(i).getValue().toString());
            orgs.add(temp);
        }

        return orgs;
    }


}
