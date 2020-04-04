package com.example.studysystem.db;

import com.example.studysystem.dao.PaperDao;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class Insert_fieldTest {
    Insert_field insert_field=new Insert_field();
    Insert_field spyInsert_field;
    PaperDao mockPaperDao=mock(PaperDao.class);
    public void prepare(){
        insert_field.set(mockPaperDao);
        spyInsert_field=Mockito.spy(insert_field);
    }
    @Test
    void excute() {
        prepare();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        spyInsert_field.excute();
        verify(spyInsert_field,times(1)).excute();
        verify(mockPaperDao,times(1)).getPapers();
        verify(spyInsert_field,times(1)).resolveField(anyList(),anyMap(),anyInt());
        verify(spyInsert_field,times(1)).insertFields(anyMap());
        assertThat(outContent.toString(),containsString("field插入完成"));
    }

    @Test
    void boringWord() {
        prepare();
        assert spyInsert_field.boringWord("And");
        assert !spyInsert_field.boringWord("and");
    }

    @Test
    void insertFields() {
        Insert_field mockInsert_field=mock(Insert_field.class);
        mockInsert_field.insertFields(anyMap());
        verify(mockInsert_field,times(1)).insertFields(anyMap());
    }

    @Test
    void sortByValueDescending() {
        prepare();
        Map<String,Integer> m1=new TreeMap<>();
        m1.put("a",1);m1.put("b",8);m1.put("c",6);m1.put("d",2);m1.put("e",5);m1.put("f",7);m1.put("g",10);
        Map<String,Integer> m2=new TreeMap<>();
        m2.put("g",10);m2.put("b",8);m2.put("f",7);m2.put("c",6);m2.put("e",5);m2.put("d",2);m2.put("a",1);
        spyInsert_field.sortByValueDescending(m1);
        verify(spyInsert_field,times(1)).sortByValueDescending(m1);
        Assert.assertEquals(m2,spyInsert_field.sortByValueDescending(m1));
    }

    @Test
    void resolveField() {
        prepare();
        spyInsert_field.resolveField(anyList(),anyMap(),anyInt());
        verify(spyInsert_field,times(1)).resolveField(anyList(),anyMap(),anyInt());
//        verify(spyInsert_field,times(1)).sortByValueDescending(null);

    }
}