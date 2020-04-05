package com.example.studysystem.controller;

import com.example.studysystem.service.author.AuthorService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AuthorControllerTest {
    private AuthorService mockAuthorService=mock(AuthorService.class);
    private AuthorController authorController =new AuthorController();
    private AuthorController mockAuthorController=mock(AuthorController.class);
    public void prepare(){ authorController.set(mockAuthorService);}
    @Test
    void getOrgs() {
        prepare();
        mockAuthorController.getOrgs();
        verify(mockAuthorController,times(1)).getOrgs();
        authorController.getOrgs();
        verify(mockAuthorService,times(1)).getAuthors();
    }

    @Test
    void testGetOrgs() {
        prepare();
        mockAuthorController.getOrgs(1);
        verify(mockAuthorController,times(1)).getOrgs(1);
        authorController.getOrgs(1);
        verify(mockAuthorService,times(1)).getAuthorById(1);
    }

    @Test
    void getCitationSum() {
        prepare();
        mockAuthorController.getCitationSum(1);
        verify(mockAuthorController,times(1)).getCitationSum(1);
        authorController.getCitationSum(1);
        verify(mockAuthorService,times(1)).getCitationSum(1);
    }

    @Test
    void getTopPaper() {
        prepare();
        mockAuthorController.getTopPaper(1);
        verify(mockAuthorController,times(1)).getTopPaper(1);
        authorController.getTopPaper(1);
        verify(mockAuthorService,times(1)).getTopPaper(1);
    }

    @Test
    void getTopKeyword() {
        prepare();
        mockAuthorController.getTopKeyword(1);
        verify(mockAuthorController,times(1)).getTopKeyword(1);
        authorController.getTopKeyword(1);
        verify(mockAuthorService,times(1)).getTopKeyword(1);
    }

    @Test
    void getSimplePaperByOrg() {
        prepare();
        mockAuthorController.getSimplePaperByOrg(1);
        verify(mockAuthorController,times(1)).getSimplePaperByOrg(1);
        authorController.getSimplePaperByOrg(1);
        verify(mockAuthorService,times(1)).getSimplePaperByAuthor(1);
    }

    @Test
    void searchAuthors() {
        prepare();
        mockAuthorController.searchAuthors("Hello world!","1");
        verify(mockAuthorController,times(1)).searchAuthors("Hello world!","1");
        authorController.searchAuthors("Hello world!","1");
        verify(mockAuthorService,times(1)).searchAuthors("Hello world!","1");
    }

    @Test
    void getTop10Author() {
        prepare();
        mockAuthorController.getTop10Author(1);
        verify(mockAuthorController,times(1)).getTop10Author(1);
        authorController.getTop10Author(1);
        verify(mockAuthorService,times(1)).getTop10Author(1);
    }
}