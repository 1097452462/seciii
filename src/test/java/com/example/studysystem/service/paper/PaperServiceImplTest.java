package com.example.studysystem.service.paper;

import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.SimplePaper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaperServiceImplTest {
    private PaperServiceImpl paperService=new PaperServiceImpl();
    private PaperServiceImpl mockPaperService=mock(PaperServiceImpl.class);
    private PaperDao mockPaperDao=mock(PaperDao.class);
    private SimplePaperDao mockSimplePaperDao=mock(SimplePaperDao.class);
    public void prepare(){paperService.set(mockPaperDao,mockSimplePaperDao);}
    @Test
    void getSimplePapers() {
        prepare();
        mockPaperService.getSimplePapers();
        verify(mockPaperService,times(1)).getSimplePapers();
        when(mockSimplePaperDao.getSimplePapers()).thenThrow(new RuntimeException());
        assertEquals("失败",paperService.getSimplePapers().getMessage());
        verify(mockSimplePaperDao,times(1)).getSimplePapers();
    }

    @Test
    void getPapers() {
        prepare();
        mockPaperService.getPapers();
        verify(mockPaperService,times(1)).getPapers();
        when(mockPaperDao.getPapers()).thenThrow(new RuntimeException());
        assertEquals("失败",paperService.getPapers().getMessage());
        verify(mockPaperDao,times(1)).getPapers();
    }

    @Test
    void getPapersById() {
        prepare();
        mockPaperService.getPapersById(1);
        verify(mockPaperService,times(1)).getPapersById(1);
        when(mockPaperDao.getPaperById(1)).thenThrow(new RuntimeException());
        assertEquals("失败",paperService.getPapersById(1).getMessage());
        verify(mockPaperDao,times(1)).getPaperById(1);
    }

    @Test
    void searchPapers() {
        prepare();
        mockPaperService.searchPapers(null);
        verify(mockPaperService,times(1)).searchPapers(null);
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockSimplePaperDao.simpleSelect_author(anyString())).thenReturn(l);
        when(mockSimplePaperDao.simpleSelect_keyword(anyString())).thenReturn(l);
        when(mockSimplePaperDao.simpleSelect_meeting(anyString())).thenReturn(l);
        when(mockSimplePaperDao.simpleSelect_org(anyString())).thenReturn(l);
        when(mockSimplePaperDao.simpleSelect_title(anyString())).thenReturn(l);
        when(mockSimplePaperDao.simpleSelect_year(anyString())).thenReturn(l);
        when(mockPaperDao.getPapersByIds(anyList())).thenThrow(new RuntimeException());
        SimplePaper sp=new SimplePaper();
        sp.setAuthors("Hello world!");sp.setAuthor_Keywords("Hello world!");
        sp.setPublication_Title("Hello world!");sp.setAuthor_Affiliations("Hello world!");
        sp.setDocument_title("Hello world!");sp.setPublication_Year("Hello world!");
        assertEquals("失败",paperService.searchPapers(sp).getMessage());
        verify(mockSimplePaperDao,atLeastOnce()).simpleSelect_author(anyString());
        verify(mockSimplePaperDao,atLeastOnce()).simpleSelect_keyword(anyString());
        verify(mockSimplePaperDao,atLeastOnce()).simpleSelect_meeting(anyString());
        verify(mockSimplePaperDao,atLeastOnce()).simpleSelect_org(anyString());
        verify(mockSimplePaperDao,atLeastOnce()).simpleSelect_title(anyString());
        verify(mockSimplePaperDao,atLeastOnce()).simpleSelect_year(anyString());
    }
}