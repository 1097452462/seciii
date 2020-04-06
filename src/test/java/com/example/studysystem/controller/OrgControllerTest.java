package com.example.studysystem.controller;

import com.example.studysystem.service.org.OrgService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class OrgControllerTest {
    private OrgController orgController=new OrgController();
    private OrgService mockOrgService=mock(OrgService.class);
    private OrgController mockOrgController=mock(OrgController.class);
    public void prepare(){orgController.set(mockOrgService);}

    @Test
    void getOrgs() {
        prepare();
        mockOrgController.getOrgs();
        verify(mockOrgController,times(1)).getOrgs();
        orgController.getOrgs();
        verify(mockOrgService,times(1)).getOrgs();
    }

    @Test
    void getOrgById() {
        prepare();
        mockOrgController.getOrgById(1);
        verify(mockOrgController,times(1)).getOrgById(1);
        orgController.getOrgById(1);
        verify(mockOrgService,times(1)).getOrgById(1);
    }

    @Test
    void getSimplePaperByOrg() {
        prepare();
        mockOrgController.getSimplePaperByOrg(1);
        verify(mockOrgController,times(1)).getSimplePaperByOrg(1);
        orgController.getSimplePaperByOrg(1);
        verify(mockOrgService,times(1)).getSimplePaperByOrg(1);
    }

    @Test
    void searchOrgs() {
        prepare();;
        mockOrgController.searchOrgs("Hello world!","1");
        verify(mockOrgController,times(1)).searchOrgs("Hello world!","1");
        orgController.searchOrgs("Hello world!","1");
        verify(mockOrgService,times(1)).searchOrgs("Hello world!","1");
    }



    @Test
    void getTopPaper() {
        prepare();
        mockOrgController.getTopPaper(1);
        verify(mockOrgController,times(1)).getTopPaper(1);
        orgController.getTopPaper(1);
        verify(mockOrgService,times(1)).getTopPaper(1);
    }

    @Test
    void getTopKeyword() {
        prepare();
        mockOrgController.getTopKeyword(1);
        verify(mockOrgController,times(1)).getTopKeyword(1);
        orgController.getTopKeyword(1);
        verify(mockOrgService,times(1)).getTopKeyword(1);
    }

    @Test
    void getTopAuthor() {
        prepare();
        mockOrgController.getTopAuthor(1);
        verify(mockOrgController,times(1)).getTopAuthor(1);
        orgController.getTopAuthor(1);
        verify(mockOrgService,times(1)).getTopAuthor(1);
    }

    @Test
    void getTop10Author() {
        prepare();
        mockOrgController.getTop10Author(1);
        verify(mockOrgController,times(1)).getTop10Author(1);
        orgController.getTop10Author(1);
        verify(mockOrgService,times(1)).getTop10Org(1);
    }

    @Test
    void getRelatedAuthors() {
        prepare();
        mockOrgController.getRelatedAuthors(1);
        verify(mockOrgController,times(1)).getRelatedAuthors(1);
        orgController.getRelatedAuthors(1);
        verify(mockOrgService,times(1)).getRelatedAuthors(1);
    }

    @Test
    void getRelatedOrgs() {
        prepare();
        mockOrgController.getRelatedOrgs(1);
        verify(mockOrgController,times(1)).getRelatedOrgs(1);
        orgController.getRelatedOrgs(1);
        verify(mockOrgService,times(1)).getRelatedOrgs(1);
    }

    @Test
    void getHistory() {
        prepare();
        mockOrgController.getHistory(1);
        verify(mockOrgController,times(1)).getHistory(1);
        orgController.getHistory(1);
        verify(mockOrgService,times(1)).getHistory(1);
    }

    @Test
    void getInterest() {
        prepare();
        mockOrgController.getInterest(1);
        verify(mockOrgController,times(1)).getInterest(1);
        orgController.getInterest(1);
        verify(mockOrgService,times(1)).getInterest(1);
    }
}