package com.example.studysystem.db;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class Insert_orgTest {
    Insert_org spyInsert_org= Mockito.spy(new Insert_org());

    @Test
    void excute() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        spyInsert_org.excute(anyList());
        verify(spyInsert_org,times(1)).excute(anyList());
        verify(spyInsert_org,times(1)).dealOrg(anyList());
        verify(spyInsert_org,times(1)).insertOrg(anyList());
        assertThat(outContent.toString(),containsString("org插入完成"));
    }

    @Test
    void insertOrg() {
        Insert_org mockInsert_org=mock(Insert_org.class);
        mockInsert_org.insertOrg(anyList());
        verify(mockInsert_org,times(1)).insertOrg(anyList());
    }

    @Test
    void dealOrg() {
        List<String[]> l=new ArrayList<>();
        l.add(new String[]{"a","1"});l.add(new String[]{"b","2"});
        spyInsert_org.dealOrg(l);
        verify(spyInsert_org, times(1)).dealOrg(l);
        int num=0;
        for(int i=0;i<2;i++){
            boolean exist=false;
            for(int j=0;j<spyInsert_org.dealOrg(l).size();j++){
                if(l.get(i)[1].equals(spyInsert_org.dealOrg(l).get(j))){
                    exist=true;
                    break;
                }
            }
            if(exist){num++;}
            else{break;}
        }
        assert num==2;
    }
}