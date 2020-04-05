package com.example.studysystem.service.field;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.FieldDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.entity.Paper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {
    private FieldServiceImpl mockFieldServiceImpl=mock(FieldServiceImpl.class);
    private FieldServiceImpl fieldService=new FieldServiceImpl();
    private FieldDao mockFieldDao=mock(FieldDao.class);
    private PaperDao mockPaperDao=mock(PaperDao.class);
    private OrgDao mockOrgDao=mock(OrgDao.class);
    private AuthorDao mockAuthorDao=mock(AuthorDao.class);
    public void prepare(){fieldService.set(mockFieldDao,mockPaperDao,mockOrgDao,mockAuthorDao);}
    @Test
    void getFields() {
        prepare();
        mockFieldServiceImpl.getFields();
        verify(mockFieldServiceImpl,times(1)).getFields();
        when(mockFieldDao.getFields()).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getFields().getMessage());
        verify(mockFieldDao,times(1)).getFields();
    }

    @Test
    void getTopAuthors() {
        prepare();
        mockFieldServiceImpl.getTopAuthors(1);
        verify(mockFieldServiceImpl,times(1)).getTopAuthors(1);
        when(mockFieldDao.getPaperId(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockFieldDao.getAuthorId(l)).thenReturn(l);
        when(mockAuthorDao.getTopAuthor_byId(l)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTopAuthors(1).getMessage());
        verify(mockFieldDao,times(1)).getPaperId(1);
        verify(mockFieldDao,times(1)).getAuthorId(l);
        verify(mockAuthorDao,times(1)).getTopAuthor_byId(l);
    }

    @Test
    void getTopOrgs() {
        prepare();
        mockFieldServiceImpl.getTopOrgs(1);
        verify(mockFieldServiceImpl,times(1)).getTopOrgs(1);
        when(mockFieldDao.getPaperId(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        when(mockFieldDao.getOrgId(l)).thenReturn(l);
        when(mockOrgDao.getTopOrg(l)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTopOrgs(1).getMessage());
        verify(mockFieldDao,times(1)).getPaperId(1);
        verify(mockFieldDao,times(1)).getOrgId(l);
        verify(mockOrgDao,times(1)).getTopOrg(l);
    }

    @Test
    void getTopPapers() {
        prepare();
        mockFieldServiceImpl.getTopPapers(1);
        verify(mockFieldServiceImpl,times(1)).getTopPapers(1);
        when(mockFieldDao.getPaperId(1)).thenReturn("1;2;3");
        List<Integer> l=new ArrayList<>();
        l.add(1);l.add(2);l.add(3);
        when(mockPaperDao.getTopPaper(l)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTopPapers(1).getMessage());
        verify(mockFieldDao,times(1)).getPaperId(1);
        verify(mockPaperDao,times(1)).getTopPaper(l);
    }


    @Test
    void getTop10Field() {
        prepare();;
        mockFieldServiceImpl.getTop10Field(anyInt());
        verify(mockFieldServiceImpl,times(1)).getTop10Field(anyInt());
        when(mockFieldDao.getTopField_paperNum()).thenThrow(new RuntimeException());
        when(mockFieldDao.getTopField_citationSum()).thenReturn(null);
        when(mockFieldDao.getTopField_point()).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTop10Field(1).getMessage());
        verify(mockFieldDao,times(1)).getTopField_paperNum();
        assertEquals(null,fieldService.getTop10Field(2).getContent());
        verify(mockFieldDao,times(1)).getTopField_citationSum();
        assertEquals("失败",fieldService.getTop10Field(3).getMessage());
        verify(mockFieldDao,times(1)).getTopField_point();
    }

    @Test
    void getFieldById() {
        prepare();
        mockFieldServiceImpl.getFieldById(1);
        verify(mockFieldServiceImpl,times(1)).getFieldById(1);
        when(mockFieldDao.getFieldById(1)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getFieldById(1).getMessage());
        verify(mockFieldDao,times(1)).getFieldById(1);
    }
}