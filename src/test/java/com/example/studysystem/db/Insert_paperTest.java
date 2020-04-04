package com.example.studysystem.db;

import org.junit.Assert;
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

class Insert_paperTest {
    Insert_paper spyInsert_paper= Mockito.spy(new Insert_paper());

    @Test
    void excute() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        spyInsert_paper.excute(anyList());
        verify(spyInsert_paper,times(1)).excute(anyList());
        verify(spyInsert_paper,times(1)).insertPaperAndSimplePaper(anyList());
        assertThat(outContent.toString(),containsString("paper&simplepaper插入完成"));
    }

    @Test
    void insertPaperAndSimplePaper() {
        Insert_paper mockInsert_paper=mock(Insert_paper.class);
        mockInsert_paper.insertPaperAndSimplePaper(anyList());
        verify(mockInsert_paper,times(1)).insertPaperAndSimplePaper(anyList());
        spyInsert_paper.insertPaperAndSimplePaper(anyList());
        Assert.assertNotNull(spyInsert_paper.insertPaperAndSimplePaper(anyList()));
    }

    @Test
    void deleteQuotes() {
        spyInsert_paper.deleteQuotes("\"Hello world!\"");
        verify(spyInsert_paper,times(1)).deleteQuotes("\"Hello world!\"");
        assert "/Hello world!/".equals(spyInsert_paper.deleteQuotes("\"Hello world!\""));
    }
}