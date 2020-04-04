package com.example.studysystem.service.paper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaperServiceTest {
    private PaperService mockPaperService=mock(PaperService.class);
    @Test
    void getSimplePapers() {
        mockPaperService.getSimplePapers();
        verify(mockPaperService,times(1)).getSimplePapers();
    }

    @Test
    void getPapers() {
        mockPaperService.getPapers();
        verify(mockPaperService,times(1)).getPapers();
    }

    @Test
    void getPapersById() {
        mockPaperService.getPapersById(1);
        verify(mockPaperService,times(1)).getPapersById(1);
    }

    @Test
    void searchPapers() {
        mockPaperService.searchPapers(null);
        verify(mockPaperService,times(1)).searchPapers(null);
    }
}