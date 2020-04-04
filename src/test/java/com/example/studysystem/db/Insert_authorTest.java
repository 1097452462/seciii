package com.example.studysystem.db;
import com.example.studysystem.entity.Paper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.validation.constraints.Negative;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class Insert_authorTest {
    Insert_author spyInsert_author= Mockito.spy(new Insert_author());

    @Test
    void excute() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        spyInsert_author.excute(anyList());
        verify(spyInsert_author,times(1)).excute(anyList());
        verify(spyInsert_author,times(1)).dealRelation(anyList());
        verify(spyInsert_author,times(1)).insertAuthor(anyList());
        assertThat(outContent.toString(),containsString("author插入完成"));
    }

    @Test
    void dealRelation() {
        Paper p1=new Paper();Paper p2=new Paper();
        p1.setAuthors("a; b; c");p1.setAuthor_Affiliations("1; 2; 3");
        p2.setAuthors("a; c; e");p2.setAuthor_Affiliations("1; 2; 3; 4");
        List<Paper> l1=new ArrayList<>();l1.add(p1);l1.add(p2);
        List<String[]> l2=new ArrayList<>();
        l2.add(new String[]{"a","1"});l2.add(new String[]{"b","2"});
        l2.add(new String[]{"c","3"});l2.add(new String[]{"c","2"});
        l2.add(new String[]{"e","3"});
        spyInsert_author.dealRelation(l1);
        verify(spyInsert_author,times(1)).dealRelation(l1);
        int num=0;
        for(int i=0;i<5;i++){
            boolean exist=false;
            for(int j=0;j<spyInsert_author.dealRelation(l1).size();j++){
                if(l2.get(i)[0].equals(spyInsert_author.dealRelation(l1).get(j)[0])
                &&l2.get(i)[1].equals(spyInsert_author.dealRelation(l1).get(j)[1])){
                    exist=true;
                    break;
                }
            }
            if(exist){num++;}
            else{break;}
        }
        assert num==5;
    }

    @Test
    void insertAuthor() {
        Insert_author mockInsert_Author=mock(Insert_author.class);
        mockInsert_Author.insertAuthor(anyList());
        verify(mockInsert_Author,times(1)).insertAuthor(anyList());
    }
}