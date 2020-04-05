package com.example.studysystem.db;

import com.example.studysystem.entity.Paper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class InsertDBTest {
    InsertDB insertDB=new InsertDB();
    InsertDB spyInsertDB;
    Insert_field spyInsert_field= spy(new Insert_field());
    Insert_author spyInsert_author=spy(new Insert_author());
    Insert_paper spyInsert_paper=spy(new Insert_paper());
    Insert_org spyInsert_org=spy(new Insert_org());
    ArrayList<String> a=new ArrayList<>();
    public void prepare() {
        insertDB.set(a, spyInsert_field, spyInsert_paper, spyInsert_author, spyInsert_org);
        spyInsertDB = Mockito.spy(insertDB);
    }


    @Test
    void alreadyPlus() {
        prepare();
        spyInsertDB.alreadyPlus("a.csv");
        verify(spyInsertDB,times(1)).alreadyPlus("a.csv");
        assert !spyInsertDB.alreadyPlus("a.csv");
        spyInsertDB.alreadyPlus("a.csv");
        this.a.add("a.csv");
        assert spyInsertDB.alreadyPlus("a.csv");
        this.a.clear();
    }

    @Test
    void tranfData() {
        Insert_paper mp=mock(Insert_paper.class);
        Insert_org mo=mock(Insert_org.class);
        Insert_author ma=mock(Insert_author.class);
        Insert_field mf=mock(Insert_field.class);
        InsertDB md=mock(InsertDB.class);
        InsertDB i=new InsertDB();i.set(a,mf,mp,ma,mo);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        md.tranfData();
        verify(md,times(1)).tranfData();
//        i.tranfData();
//        verify(mp,atLeastOnce()).excute(anyList());
//        verify(ma,atLeastOnce()).excute(anyList());
//        verify(mo,atLeastOnce()).excute(anyList());
//        verify(mf,atLeastOnce()).excute();
//        assertThat(outContent.toString(),containsString("插入完成！"));
    }

    @Test
    void readCSV_to_MySQL() {
        prepare();
        String[] info=new String[]{"a","b","c","d","a","b","c","d","a","b","c","d","","","","","","","","","","","","","","","","",""};
        Paper p1=new Paper();p1.setEnd_Page("aaaaaa");p1.setStart_Page("a");
        when(spyInsertDB.dealPaper(info)).thenReturn(p1);
        when(spyInsertDB.legalPaper(p1)).thenReturn(true);
        spyInsertDB.readCSV_to_MySQL(anyString(),anyList());
        verify(spyInsertDB,times(1)).readCSV_to_MySQL(anyString(),anyList());
    }

    @Test
    void legalPaper() {
        prepare();
        Paper p1=new Paper();Paper p2=new Paper();Paper p3=new Paper();
        p1.setEnd_Page("aaaaaa");p1.setStart_Page("a");
        p2.setStart_Page("aaaaaa");p2.setEnd_Page("aaaaaa");
        p3.setEnd_Page("a");p3.setStart_Page("a");
        spyInsertDB.legalPaper(p1);
        verify(spyInsertDB,times(1)).legalPaper(p1);
        assert !spyInsertDB.legalPaper(p1);
        assert !spyInsertDB.legalPaper(p2);
        assert spyInsertDB.legalPaper(p3);
    }

    @Test
    void dealPaper() {
        prepare();
        String[] info=new String[]{"a","b","c","d","a","b","c","d","a","b","c","d","","","","","","","","","","","","","","","","",""};
        spyInsertDB.dealPaper(info);
        verify(spyInsertDB,times(1)).dealPaper(info);
        verify(spyInsertDB,times(29)).deleteQuotes(anyString());
        assert spyInsertDB.dealPaper(info).getDocument_title().equals("a");
    }

    @Test
    void deleteQuotes() {
        prepare();
        spyInsertDB.deleteQuotes("\"Hello world!\"");
        verify(spyInsertDB,times(1)).deleteQuotes("\"Hello world!\"");
        assert "/Hello world!/".equals(spyInsertDB.deleteQuotes("\"Hello world!\""));

    }

    @Test
    void ignoreRepeat() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        List<Paper> l=new ArrayList<>();
        Paper p1=new Paper();p1.setPDF_Link("a");
        Paper p2=new Paper();p2.setPDF_Link("b");
        Paper p3=new Paper();p3.setPDF_Link("a");
        l.add(p1);l.add(p2);l.add(p3);
        prepare();
        spyInsertDB.ignoreRepeat(l);
        verify(spyInsertDB,times(1)).ignoreRepeat(l);
        assert 2==spyInsertDB.ignoreRepeat(l).size();
        assertThat(outContent.toString(),containsString("repeat!"));
    }

}