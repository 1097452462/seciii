package com.example.studysystem.service.field;

import com.example.studysystem.dao.FieldDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.entity.Paper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {
    private FieldServiceImpl mockFieldServiceImpl=mock(FieldServiceImpl.class);
    private FieldServiceImpl fieldService=new FieldServiceImpl();
    private FieldDao mockFieldDao=mock(FieldDao.class);
    private PaperDao mockPaperDao=mock(PaperDao.class);
    private OrgDao mockOrgDao=mock(OrgDao.class);
    public void prepare(){fieldService.set(mockFieldDao,mockPaperDao,mockOrgDao);}
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
    }

    @Test
    void getTopOrgs() {
    }

    @Test
    void getTopPapers() {
        prepare();
        mockFieldServiceImpl.getTopPapers(1);
        verify(mockFieldServiceImpl,times(1)).getTopPapers(1);
        when(mockFieldDao.getTopPaperIds(1)).thenReturn(null);
        when(mockPaperDao.getPapersByIds(null)).thenThrow(new RuntimeException());
        assertEquals("失败",fieldService.getTopPapers(1).getMessage());
        verify(mockFieldDao,times(1)).getTopPaperIds(1);
        verify(mockPaperDao,times(1)).getPapersByIds(null);
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
}