package com.example.studysystem.db;

import com.example.studysystem.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Mapper
@Component
public class Insert_paper {

    public void excute(List<Paper> paperList){
        Long start=System.currentTimeMillis();
        insertPaperAndSimplePaper(paperList);
        Long end=System.currentTimeMillis();
        System.out.print("paper&simplepaper插入完成   ");
        System.out.println(end-start+"  ms");
    }

    private static List<Paper> insertPaperAndSimplePaper(List<Paper> paperList){
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
            return paperList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String deleteQuotes(String s){
        return s.replace('"','/');
    }

}
