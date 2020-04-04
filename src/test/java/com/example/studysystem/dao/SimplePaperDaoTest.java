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
class SimplePaperDaoTest {
    @Autowired
    private SimplePaperDao simplePaperDao;

    @Test
    @Transactional
    @Rollback
    void getSimplePaperByOrg() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.getSimplePaperByOrg("Hello world!"));
    }

    @Test
    @Transactional
    @Rollback
    void getSimplePapers() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.getSimplePapers());
    }

    @Test
    @Transactional
    @Rollback
    void getSimplePapersByIds() {
        assert simplePaperDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);l.add(2);l.add(3);
        assertNotNull(simplePaperDao.getSimplePapersByIds(l));
    }

    @Test
    @Transactional
    @Rollback
    void getSimplePaperByAuthor() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.getSimplePaperByAuthor("Hello world!"));
    }

    @Test
    @Transactional
    @Rollback
    void simpleSelect_title() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.simpleSelect_title("Hello world!"));
    }

    @Test
    @Transactional
    @Rollback
    void simpleSelect_author() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.simpleSelect_author("Hello world!"));
    }

    @Test
    @Transactional
    @Rollback
    void simpleSelect_year() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.simpleSelect_year("Hello world!"));
    }

    @Test
    @Transactional
    @Rollback
    void simpleSelect_org() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.simpleSelect_org("Hello world!"));
    }

    @Test
    @Transactional
    @Rollback
    void simpleSelect_meeting() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.simpleSelect_meeting("Hello world!"));
    }

    @Test
    @Transactional
    @Rollback
    void simpleSelect_keyword() {
        assert simplePaperDao!=null;
        assertNotNull(simplePaperDao.simpleSelect_keyword("Hello world"));
    }
}