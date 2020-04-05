package com.example.studysystem.controller;

import com.example.studysystem.service.field.FieldService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class FieldControllerTest {
    FieldController f=new FieldController();
    FieldController mockF=mock(FieldController.class);
    FieldService fs=mock(FieldService.class);
    public void prepare(){f.set(fs);}

    @Test
    void getById() {
        prepare();
        mockF.getById(1);
        verify(mockF,times(1)).getById(1);
        f.getById(1);
        verify(fs,times(1)).getFieldById(1);
    }

    @Test
    void getTop10Field() {
        prepare();
        mockF.getTop10Field(1);
        verify(mockF,times(1)).getTop10Field(1);
        f.getTop10Field(1);
        verify(fs,times(1)).getTop10Field(1);
    }

    @Test
    void getTopAuthors() {
        prepare();
        mockF.getTopAuthors(1);
        verify(mockF,times(1)).getTopAuthors(1);
        f.getTopAuthors(1);
        verify(fs,times(1)).getTopAuthors(1);
    }

    @Test
    void getTopOrgs() {
        prepare();
        mockF.getTopOrgs(1);
        verify(mockF,times(1)).getTopOrgs(1);
        f.getTopOrgs(1);
        verify(fs,times(1)).getTopOrgs(1);
    }

    @Test
    void getTopPapers() {
        prepare();
        mockF.getTopPapers(1);
        verify(mockF,times(1)).getTopPapers(1);
        f.getTopPapers(1);
        verify(fs,times(1)).getTopPapers(1);
    }
}