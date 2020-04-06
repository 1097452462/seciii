package com.example.studysystem.service.org;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrgServiceImplTest {
    private OrgServiceImpl orgService=new OrgServiceImpl();
    private OrgServiceImpl mockOrgService=mock(OrgServiceImpl.class);
    private OrgDao mockOrgDao=mock(OrgDao.class);
    private PaperDao mockPaperDao=mock(PaperDao.class);
    private AuthorDao mockAuthorDao=mock(AuthorDao.class);
    public void prepare(){orgService.set(mockOrgDao,mockPaperDao,mockAuthorDao);}
    @Test
    void getOrgs() {
        prepare();
        mockOrgService.getOrgs();
        verify(mockOrgService,times(1)).getOrgs();
        when(mockOrgDao.getOrgs()).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getOrgs().getMessage());
        verify(mockOrgDao,times(1)).getOrgs();
    }

    @Test
    void getOrgById() {
        prepare();
        mockOrgService.getOrgById(1);
        verify(mockOrgService,times(1)).getOrgById(1);
        when(mockOrgDao.getOrgById(1)).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getOrgById(1).getMessage());
        verify(mockOrgDao,times(1)).getOrgById(1);
    }

    @Test
    void getSimplePaperByOrg() {
        prepare();
        mockOrgService.getSimplePaperByOrg(1);
        verify(mockOrgService,times(1)).getSimplePaperByOrg(1);
        when(mockOrgDao.getPaperIdByOrg(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getSimplePaperByOrg(1).getMessage());
        verify(mockOrgDao,times(1)).getPaperIdByOrg(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void searchOrgs() {
        prepare();
        mockOrgService.searchOrgs("Hello world!","1");
        verify(mockOrgService,times(1)).searchOrgs("Hello world!","1");
        when(mockOrgDao.searchOrgs("Hello world!",1)).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.searchOrgs("Hello world!","1").getMessage());
        verify(mockOrgDao,times(1)).searchOrgs("Hello world!",1);
    }


    @Test
    void getTopPaper() {
        prepare();
        mockOrgService.getTopPaper(1);
        verify(mockOrgService,times(1)).getTopPaper(1);
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockOrgDao.getTopPaperIds(1)).thenReturn(l);
        when(mockPaperDao.getPapersByIds(l)).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getTopPaper(1).getMessage());
        verify(mockOrgDao,times(1)).getTopPaperIds(1);
        verify(mockPaperDao,times(1)).getPapersByIds(l);
    }

    @Test
    void getTopKeyword() {
        prepare();
        mockOrgService.getTopKeyword(1);
        verify(mockOrgService,times(1)).getTopKeyword(1);
        when(mockOrgDao.getKeywords(1)).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getTopKeyword(1).getMessage());
        verify(mockOrgDao,times(1)).getKeywords(1);
    }

    @Test
    void getTop10Org() {
        prepare();
        mockOrgService.getTop10Org(anyInt());
        verify(mockOrgService,times(1)).getTop10Org(anyInt());
        when(mockOrgDao.getTopOrg_paperNum()).thenThrow(new RuntimeException());
        when(mockOrgDao.getTopOrg_citationSum()).thenReturn(null);
        when(mockOrgDao.getTopOrg_point()).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getTop10Org(1).getMessage());
        verify(mockOrgDao,times(1)).getTopOrg_paperNum();
        assertEquals(null,orgService.getTop10Org(2).getContent());
        verify(mockOrgDao,times(1)).getTopOrg_citationSum();
        assertEquals("失败",orgService.getTop10Org(3).getMessage());
        verify(mockOrgDao,times(1)).getTopOrg_point();
    }

    @Test
    void sortByValueDescending() {
        Map<String,Integer> m1=new TreeMap<>();
        m1.put("a",1);m1.put("b",8);m1.put("c",6);m1.put("d",2);m1.put("e",5);m1.put("f",7);m1.put("g",10);
        Map<String,Integer> m2=new TreeMap<>();
        m2.put("g",10);m2.put("b",8);m2.put("f",7);m2.put("c",6);m2.put("e",5);m2.put("d",2);m2.put("a",1);
        mockOrgService.sortByValueDescending(m1);
        verify(mockOrgService,times(1)).sortByValueDescending(m1);
        assertEquals(m2,orgService.sortByValueDescending(m1));
    }


    @Test
    void getTopAuthor() {
        prepare();
        mockOrgService.getTopAuthor(1);
        verify(mockOrgService,times(1)).getTopAuthor(1);
        when(mockOrgDao.getAuthors(1)).thenReturn("a;b;c");
        List<String> l=new ArrayList<>();l.add("a");l.add("b");l.add("c");
        when(mockAuthorDao.getTopAuthor_byName(l)).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getTopAuthor(1).getMessage());
        verify(mockOrgDao,times(1)).getAuthors(1);
        verify(mockAuthorDao,times(1)).getTopAuthor_byName(l);
    }

    @Test
    void getRelatedAuthors() {
        prepare();
        mockOrgService.getRelatedAuthors(1);
        verify(mockOrgService,times(1)).getRelatedAuthors(1);
        when(mockAuthorDao.getRelatedAuthor_byOrgId(1)).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getRelatedAuthors(1).getMessage());
        verify(mockAuthorDao,times(1)).getRelatedAuthor_byOrgId(1);
    }

    @Test
    void getRelatedOrgs() {
        prepare();
        mockOrgService.getRelatedOrgs(1);
        verify(mockOrgService,times(1)).getRelatedOrgs(1);
        when(mockOrgDao.getRelatedOrg_byOrgId(1)).thenThrow(new RuntimeException());
        assertEquals("失败",orgService.getRelatedOrgs(1).getMessage());
        verify(mockOrgDao,times(1)).getRelatedOrg_byOrgId(1);
    }
}