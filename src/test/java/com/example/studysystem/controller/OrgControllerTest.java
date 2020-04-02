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
}