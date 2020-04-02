package com.example.studysystem.service.author;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AuthorServiceTest {
    private AuthorService mockAuthorService=mock(AuthorService.class);

    @Test
    void getAuthors() {
        mockAuthorService.getAuthors();
        verify(mockAuthorService,times(1)).getAuthors();
    }

    @Test
    void getAuthorById() {
        mockAuthorService.getAuthorById(1);
        verify(mockAuthorService,times(1)).getAuthorById(1);
    }

    @Test
    void getSimplePaperByAuthor() {
        mockAuthorService.getSimplePaperByAuthor(1);
        verify(mockAuthorService,times(1)).getSimplePaperByAuthor(1);
    }

    @Test
    void searchAuthors() {
        mockAuthorService.searchAuthors("Hello world!","1");
        verify(mockAuthorService,times(1)).searchAuthors("Hello world!","1");
    }

    @Test
    void getCitationSum() {
        mockAuthorService.getCitationSum(1);
        verify(mockAuthorService,times(1)).getCitationSum(1);
    }

    @Test
    void getTopPaper() {
        mockAuthorService.getTopPaper(1);
        verify(mockAuthorService,times(1)).getTopPaper(1);
    }

    @Test
    void getTopKeyword() {
        mockAuthorService.getTopKeyword(1);
        verify(mockAuthorService,times(1)).getTopKeyword(1);
    }
}