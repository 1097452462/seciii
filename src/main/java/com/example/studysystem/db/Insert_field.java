package com.example.studysystem.db;

import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Mapper
@Component
public class Insert_field {

    @Autowired
    private PaperDao paperDao;

    public void excute(){
        Long start=System.currentTimeMillis();
        List<Paper> paperList=paperDao.getPapers();
        Map<String,List<Integer>> map=new TreeMap<>();
        Map<String,String> paperField=resolveField(paperList,map, (int) (0.1*paperList.size()));
        insertFields(paperField);
        Long end=System.currentTimeMillis();
        System.out.println(end-start+"  ms");
    }

    private static boolean boringWord(String s){
        String[] list={"","And","Of","It"};
        for(String l:list) {
            if (l.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private static void insertFields(Map<String,String> paperField){
        try{
            Connection con = MySQLconnection.getConnection();
            con.setAutoCommit(false);
            if(!con.isClosed()) {
                Statement statement = con.createStatement();
                PreparedStatement p1=con.prepareStatement("insert into field(Field_name,Paper_list,Paper_num)values(?,?,?)");
                for(Map.Entry<String,String> a:paperField.entrySet()){//System.out.println(a.getKey()+" "+a.getValue());
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

    private static Map<String,String> resolveField(List<Paper> papers, Map<String,List<Integer>> paperField, int cnt) {
        List<String> fields = new ArrayList<>();
        Map<String, Integer> map = new TreeMap<>();

        for (Paper p : papers) {
            for (String s : p.getAuthor_Keywords().split(";")) {
                if(s.isEmpty()||s.length()>60)continue;
                s=s.substring(0,1).toUpperCase().concat(s.substring(1).toLowerCase());
                if (boringWord(s)) continue;
                if (map.containsKey(s)) map.put(s, map.get(s) + 1);
                else map.put(s, 1);
            }
        }

        map = sortByValueDescending(map);
        int n = 0;
        for (Map.Entry<String, Integer> a : map.entrySet()) {
            fields.add(a.getKey());
            paperField.put(a.getKey(), new ArrayList<>());
            n++;
            if (n == cnt) break;
        }


        for (Paper p : papers) {
            int find=0;
            for (String s : p.getAuthor_Keywords().split(";")) {
                if(s.isEmpty())continue;
                s = s.substring(0,1).toUpperCase().concat(s.substring(1).toLowerCase());
                for (Map.Entry<String, List<Integer>> a : paperField.entrySet()) {
                    if (a.getKey().equals(s)) {
                        find++;
                        List<Integer> list = a.getValue();
                        list.add(p.getId());
                        paperField.put(a.getKey(),list);
                    }
                }
            }
        }

        Map<String,String> ans=new HashMap<>();
        for(Map.Entry<String, List<Integer>> a : paperField.entrySet()){
            List<Integer> newList = a.getValue().stream().distinct().collect(Collectors.toList());
            String value="";
            for(int d:newList)value+=Integer.toString(d)+";";
            ans.put(a.getKey(),value);
        }
        return ans;
    }
}