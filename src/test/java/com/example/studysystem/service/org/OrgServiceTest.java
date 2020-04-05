package com.example.studysystem.service.org;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class OrgServiceTest {
    private OrgService mockOrgService=mock(OrgService.class);
    @Test
    void getOrgs() {
        mockOrgService.getOrgs();
        verify(mockOrgService,times(1)).getOrgs();
    }

    @Test
    void getOrgById() {
        mockOrgService.getOrgById(1);
        verify(mockOrgService,times(1)).getOrgById(1);
    }

    @Test
    void getSimplePaperByOrg() {
        mockOrgService.getSimplePaperByOrg(1);
        verify(mockOrgService,times(1)).getSimplePaperByOrg(1);
    }

    @Test
    void searchOrgs() {
        mockOrgService.searchOrgs("Hello world!","1");
        verify(mockOrgService,times(1)).searchOrgs("Hello world!","1");
    }

    @Test
    void getPaperNum() {
        mockOrgService.getPaperNum(1);
        verify(mockOrgService,times(1)).getPaperNum(1);
    }

    @Test
    void getCitationSum() {
        mockOrgService.getCitationSum(1);
        verify(mockOrgService,times(1)).getCitationSum(1);
    }

    @Test
    void getTopPaper() {
        mockOrgService.getTopPaper(1);
        verify(mockOrgService,times(1)).getTopPaper(1);
    }

    @Test
    void getTopKeyword() {
        mockOrgService.getTopKeyword(1);
        verify(mockOrgService,times(1)).getTopKeyword(1);
    }

    @Test
    void getTop10Org() {
        mockOrgService.getTop10Org(anyInt());
        verify(mockOrgService,times(1)).getTop10Org(anyInt());
    }

    @Test
    void getAuthorNum() {
        mockOrgService.getAuthorNum(1);
        verify(mockOrgService,times(1)).getAuthorNum(1);
    }

    @Test
    void getTopAuthor() {
        mockOrgService.getTopAuthor(1);
        verify(mockOrgService,times(1)).getTopAuthor(1);
    }
}