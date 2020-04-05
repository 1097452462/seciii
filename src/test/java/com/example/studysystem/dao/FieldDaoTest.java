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
class FieldDaoTest {
    @Autowired
    private FieldDao fieldDao;

    @Test
    @Transactional
    @Rollback
    void getFields() {
        assert fieldDao!=null;
        assertNotNull(fieldDao.getFields());
    }

    @Test
    @Transactional
    @Rollback
    void getTopPaperIds() {
        assert fieldDao!=null;
//        assertNotNull(fieldDao.getTopPaperIds(1));
    }

    @Test
    @Transactional
    @Rollback
    void getTopField_paperNum() {
        assert fieldDao!=null;
        assertNotNull(fieldDao.getTopField_paperNum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopField_citationSum() {
        assert fieldDao!=null;
//        assertNotNull(fieldDao.getTopField_citationSum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopField_point() {
        assert fieldDao!=null;
//        assertNotNull(fieldDao.getTopField_point());
    }

    @Test
    @Transactional
    @Rollback
    void getFieldById() {
        assert fieldDao!=null;
        assert fieldDao.getFieldById(1)==null||fieldDao.getFieldById(1).getId()>0;
    }

    @Test
    @Transactional
    @Rollback
    void getPaperId() {
        assert fieldDao!=null;
        assert fieldDao.getPaperId(1)==null||fieldDao.getPaperId(1).length()>0;
    }

    @Test
    @Transactional
    @Rollback
    void getAuthorId() {
        assert fieldDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);
        assertNotNull(fieldDao.getAuthorId(l));
    }

    @Test
    @Transactional
    @Rollback
    void getOrgId() {
        assert fieldDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);
        assertNotNull(fieldDao.getOrgId(l));
    }
}