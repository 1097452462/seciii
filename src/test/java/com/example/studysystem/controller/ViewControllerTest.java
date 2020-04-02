package com.example.studysystem.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class ViewControllerTest {
    private ViewController mockViewController=mock(ViewController.class);

    @Test
    void jumpToHome() {
        mockViewController.jumpToHome();
        verify(mockViewController,times(1)).jumpToHome();
    }

    @Test
    void jumpToManage() {
        mockViewController.jumpToManage();
        verify(mockViewController,times(1)).jumpToManage();
    }

    @Test
    void jumpToAuthor() {
        mockViewController.jumpToAuthor();
        verify(mockViewController,times(1)).jumpToAuthor();
    }

    @Test
    void jumpToOrg() {
        mockViewController.jumpToOrg();
        verify(mockViewController,times(1)).jumpToOrg();
    }

    @Test
    void jumpToPaperDetail() {
        mockViewController.jumpToPaperDetail();
        verify(mockViewController,times(1)).jumpToPaperDetail();
    }

    @Test
    void jumpToOrgDetail() {
        mockViewController.jumpToOrgDetail();
        verify(mockViewController,times(1)).jumpToOrgDetail();
    }

    @Test
    void jumpToAuthorDetail() {
        mockViewController.jumpToAuthorDetail();
        verify(mockViewController,times(1)).jumpToAuthorDetail();
    }
}