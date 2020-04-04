package com.example.studysystem.service.field;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceTest {
    private FieldService mockFieldService=mock(FieldService.class);
    @Test
    void getFields() {
        mockFieldService.getFields();
        verify(mockFieldService,times(1)).getFields();
    }

    @Test
    void getTopAuthors() {
        mockFieldService.getTopAuthors(1);
        verify(mockFieldService,times(1)).getTopAuthors(1);
    }

    @Test
    void getTopOrgs() {
        mockFieldService.getTopOrgs(1);
        verify(mockFieldService,times(1)).getTopOrgs(1);
    }

    @Test
    void getTopPapers() {
        mockFieldService.getTopPapers(1);
        verify(mockFieldService,times(1)).getTopPapers(1);
    }


    @Test
    void getTop10Field() {
        mockFieldService.getTop10Field(anyInt());
        verify(mockFieldService,times(1)).getTop10Field(anyInt());
    }
}