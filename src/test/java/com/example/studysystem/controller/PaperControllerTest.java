package com.example.studysystem.controller;

import com.example.studysystem.entity.SimplePaper;
import com.example.studysystem.service.paper.PaperService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class PaperControllerTest {
    private PaperController paperController=new PaperController();
    private PaperController mockPaperController=mock(PaperController.class);
    private PaperService mockPaperService=mock(PaperService.class);
    public void prepare(){paperController.set(mockPaperService);}

    @Test
    void getpapers() {
        prepare();
        mockPaperController.getpapers();
        verify(mockPaperController,times(1)).getpapers();
        paperController.getpapers();
        verify(mockPaperService,times(1)).getPapers();
    }

    @Test
    void getpapersById() {
        prepare();
        mockPaperController.getpapersById(1);
        verify(mockPaperController,times(1)).getpapersById(1);
        paperController.getpapersById(1);
        verify(mockPaperService,times(1)).getPapersById(1);
    }

    @Test
    void getSimplepapers() {
        prepare();
        mockPaperController.getSimplepapers();
        verify(mockPaperController,times(1)).getSimplepapers();
        paperController.getSimplepapers();
        verify(mockPaperService,times(1)).getSimplePapers();
    }

    @Test
    void searchUser() {
        prepare();
        SimplePaper sp=new SimplePaper();
        mockPaperController.searchUser(sp);
        verify(mockPaperController,times(1)).searchUser(sp);
        paperController.searchUser(sp);
        verify(mockPaperService,times(1)).searchPapers(sp);
    }
}