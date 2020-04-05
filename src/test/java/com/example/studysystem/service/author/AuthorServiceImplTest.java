package com.example.studysystem.service.author;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceImplTest {
    private AuthorDao mockAuthorDao=mock(AuthorDao.class);
    private OrgDao mockOrgDao=mock(OrgDao.class);
    private PaperDao mockPaperDao=mock(PaperDao.class);
    private AuthorServiceImpl authorService=new AuthorServiceImpl();
    private AuthorServiceImpl mockAuthorService=mock(AuthorServiceImpl.class);

    public void prepare(){authorService.set(mockAuthorDao,mockOrgDao,mockPaperDao);}
    @Test
    void getAuthors() {
        prepare();
        mockAuthorService.getAuthors();
        verify(mockAuthorService,times(1)).getAuthors();
        when(mockAuthorDao.getAuthors()).thenThrow(new RuntimeException());
        assertEquals("失败", authorService.getAuthors().getMessage());
        verify(mockAuthorDao,times(1)).getAuthors();
    }

    @Test
    void getAuthorById() {
        prepare();;
        mockAuthorService.getAuthorById(1);
        verify(mockAuthorService,times(1)).getAuthorById(1);
        when(mockAuthorDao.getAuthorById(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getAuthorById(1).getMessage());
        verify(mockAuthorDao,times(1)).getAuthorById(1);
    }

    @Test
    void getSimplePaperByAuthor() {
        prepare();;
        mockAuthorService.getSimplePaperByAuthor(1);
        verify(mockAuthorService,times(1)).getSimplePaperByAuthor(1);
        when(mockAuthorDao.getPaperIdByAuthor(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getSimplePaperByAuthor(1).getMessage());
        verify(mockAuthorDao,times(1)).getPaperIdByAuthor(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void searchAuthors() {
        prepare();
        mockAuthorService.searchAuthors("Hello world!","1");
        verify(mockAuthorService,times(1)).searchAuthors("Hello world!","1");
        when(mockAuthorDao.searchAuthors("Hello world!",1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.searchAuthors("Hello world!","1").getMessage());
        verify(mockAuthorDao,times(1)).searchAuthors("Hello world!",1);
    }

    @Test
    void getPaperNum() {
        prepare();
        mockAuthorService.getPaperNum(1);
        verify(mockAuthorService,times(1)).getPaperNum(1);
        when(mockAuthorDao.getPaperNum(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getPaperNum(1).getMessage());
        verify(mockAuthorDao,times(1)).getPaperNum(1);
    }

    @Test
    void getCitationSum() {
        prepare();
        mockAuthorService.getCitationSum(1);
        verify(mockAuthorService,times(1)).getCitationSum(1);
        when(mockAuthorDao.getCitationSum(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getCitationSum(1).getMessage());
        verify(mockAuthorDao,times(1)).getCitationSum(1);
    }

    @Test
    void getTopPaper() {
        prepare();
        mockAuthorService.getTopPaper(1);
        verify(mockAuthorService,times(1)).getTopPaper(1);
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockAuthorDao.getTopPaperIds(1)).thenReturn(l);
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getTopPaper(1).getMessage());
        verify(mockAuthorDao,times(1)).getTopPaperIds(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void getTopKeyword() {
        prepare();
        mockAuthorService.getTopKeyword(1);
        verify(mockAuthorService,times(1)).getTopKeyword(1);
        when(mockAuthorDao.getKeywords(1)).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getTopKeyword(1).getMessage());
        verify(mockAuthorDao,times(1)).getKeywords(1);
    }

    @Test
    void getTop10Author() {
        prepare();
        mockAuthorService.getTop10Author(1);
        verify(mockAuthorService,times(1)).getTop10Author(1);
        when(mockAuthorDao.getTopAuthor_paperNum()).thenThrow(new RuntimeException());
        when(mockAuthorDao.getTopAuthor_citationSum()).thenReturn(null);
        when(mockAuthorDao.getTopAuthor_point()).thenThrow(new RuntimeException());
        assertEquals("失败",authorService.getTop10Author(1).getMessage());
        verify(mockAuthorDao,times(1)).getTopAuthor_paperNum();
        assertEquals(null,authorService.getTop10Author(2).getContent());
        verify(mockAuthorDao,times(1)).getTopAuthor_citationSum();
        assertEquals("失败",authorService.getTop10Author(3).getMessage());
        verify(mockAuthorDao,times(1)).getTopAuthor_point();
    }

    @Test
    void sortByValueDescending() {
        Map<String,Integer> m1=new TreeMap<>();
        m1.put("a",1);m1.put("b",8);m1.put("c",6);m1.put("d",2);m1.put("e",5);m1.put("f",7);m1.put("g",10);
        Map<String,Integer> m2=new TreeMap<>();
        m2.put("g",10);m2.put("b",8);m2.put("f",7);m2.put("c",6);m2.put("e",5);m2.put("d",2);m2.put("a",1);
        mockAuthorService.sortByValueDescending(m1);
        verify(mockAuthorService,times(1)).sortByValueDescending(m1);
        assertEquals(m2,authorService.sortByValueDescending(m1));
    }
}