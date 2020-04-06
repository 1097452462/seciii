package com.example.studysystem.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class PaperDaoTest {
    @Autowired
    private PaperDao paperDao;

    @Test
    @Transactional
    @Rollback
    void getPapers() {
        assert paperDao!=null;
//        assertNotNull(paperDao.getPapers());
    }

    @Test
    @Transactional
    @Rollback
    void getPaperById() {
        assert paperDao!=null;
        assertTrue(paperDao.getPaperById(1)==null||!paperDao.getPaperById(1).getPDF_Link().equals(null));
    }

    @Test
    @Transactional
    @Rollback
    void getPapersByIds() {
        assert paperDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        assertNotNull(paperDao.getPapersByIds(l));
    }

    @Test
    @Transactional
    @Rollback
    void getTopPaper() {
        assert paperDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);
        assertNotNull(paperDao.getTopPaper(l));
    }

    @Test
    @Transactional
    @Rollback
    void getMeetingTop10() {
        assert paperDao!=null;
        assertNotNull(paperDao.getMeetingTop10());
    }

    @Test
    @Transactional
    @Rollback
    void getMeetingById() {
        assert paperDao!=null;
        assert paperDao.getMeetingById(1)==null||paperDao.getMeetingById(1).getCitation_sum()>=0;
    }
}