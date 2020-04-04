package com.example.studysystem.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class OrgDaoTest {
    @Autowired
    private OrgDao orgDao;

    @Test
    @Transactional
    @Rollback
    void getOrgById() {
        assert orgDao!=null;
        assertTrue(orgDao.getOrgById(1)==null||orgDao.getOrgById(1).getId()>0);
    }

    @Test
    @Transactional
    @Rollback
    void getOrgs() {
        assert orgDao!=null;
        assertNotNull(orgDao.getOrgs());
    }

    @Test
    @Transactional
    @Rollback
    void searchOrgs() {
        assert orgDao!=null;
        assertNotNull(orgDao.searchOrgs("Hello world!",1));
    }

    @Test
    @Transactional
    @Rollback
    void getPaperIdByOrg() {
        assert orgDao!=null;
        assertTrue(orgDao.getPaperIdByOrg(1)==null||Integer.parseInt(orgDao.getPaperIdByOrg(1))>0);
    }

    @Test
    @Transactional
    @Rollback
    void getPaperNum() {
        assert orgDao!=null;
        assertTrue(orgDao.getPaperNum(1)==null||orgDao.getPaperNum(1)>=0);
    }

    @Test
    @Transactional
    @Rollback
    void getCitationSum() {
        assert orgDao!=null;
        assertTrue(orgDao.getCitationSum(1)==null||orgDao.getCitationSum(1)>0);
    }

//    @Test
//    @Transactional
//    @Rollback
//    void getTopPaperIds() {
//        assert orgDao!=null;
//        assertNotNull(orgDao.getTopPaperIds(1));
//    }

//    @Test
//    @Transactional
//    @Rollback
//    void getKeywords() {
//        assert orgDao!=null;
//        assertNotNull(orgDao.getKeywords(1));
//    }

    @Test
    @Transactional
    @Rollback
    void getTopOrg_paperNum() {
        assert orgDao!=null;
        assertNotNull(orgDao.getTopOrg_paperNum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopOrg_citationSum() {
        assert orgDao!=null;
        assertNotNull(orgDao.getTopOrg_citationSum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopOrg_point() {
        assert orgDao!=null;
        assertNotNull(orgDao.getTopOrg_point());
    }
}