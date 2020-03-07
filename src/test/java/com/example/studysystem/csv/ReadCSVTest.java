package com.example.studysystem.csv;

import com.example.studysystem.entity.Paper;
import com.example.studysystem.entity.SimplePaper;
import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ReadCSVTest {
    ReadCSV r=new ReadCSV();
    String path="src/test/resources/test.csv";

    @Test
    void deleteQuotes() {
        assertEquals("hello world",r.deleteQuotes("hello world"));
        assertEquals("\"hello world\"",r.deleteQuotes("\"hello world\""));
    }

    @Test
    void addAdd() {
        String sep = System.getProperty("line.separator");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        SimplePaper sp=new SimplePaper();
        r.AddAdd(sp);
        assertEquals(outContent.toString(), "simplepaper插入成功"+sep);
    }

    @Test
    void add() {
        Paper p1=new Paper();Paper p2=new Paper();
        assertEquals(r.Add(p1)+1,r.Add(p2));
    }
}