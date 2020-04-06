package com.example.studysystem.dao;

import com.example.studysystem.entity.Author;
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
class AuthorDaoTest {
    @Autowired
    private AuthorDao authorDao;

    @Test
    @Transactional
    @Rollback
    void getAuthors() {
        assert authorDao!=null;
        assertNotNull(authorDao.getAuthors());
    }

    @Test
    @Transactional
    @Rollback
    void getAuthorById() {
        assert authorDao!=null;
        assertTrue(authorDao.getAuthorById(1)==null||authorDao.getAuthorById(1).getId()>0);
    }

    @Test
    @Transactional
    @Rollback
    void searchAuthors() {
        assert authorDao!=null;
        assertNotNull(authorDao.searchAuthors("Hello world!",1));
    }

    @Test
    @Transactional
    @Rollback
    void getPaperIdByAuthor() {
        assert authorDao!=null;
        assertTrue(authorDao.getPaperIdByAuthor(1)==null||authorDao.getPaperIdByAuthor(1).length()>=1);
    }

    @Test
    @Transactional
    @Rollback
    void getPaperNum() {
        assert authorDao!=null;
        assertTrue(authorDao.getPaperNum(1)==null||authorDao.getPaperNum(1)>=0);
    }

    @Test
    @Transactional
    @Rollback
    void getCitationSum() {
        assert authorDao!=null;
        assertTrue(authorDao.getCitationSum(1)==null||authorDao.getCitationSum(1)>=0);
    }

    @Test
    @Transactional
    @Rollback
    void getTopPaperIds() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTopPaperIds(1));
    }

    @Test
    @Transactional
    @Rollback
    void getKeywords() {
        assert authorDao!=null;
        assertNotNull(authorDao.getKeywords(1));
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_paperNum() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTopAuthor_paperNum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_citationSum() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTopAuthor_citationSum());
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_point() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTopAuthor_point());
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_byName() {
        assert authorDao!=null;
        List<String> l=new ArrayList<>();l.add("Hello world!");
        assertNotNull(authorDao.getTopAuthor_byName(l));
    }

    @Test
    @Transactional
    @Rollback
    void getTopAuthor_byId() {
        assert authorDao!=null;
        List<Integer> l=new ArrayList<>();l.add(1);
        assertNotNull(authorDao.getTopAuthor_byId(l));
    }

    @Test
    @Transactional
    @Rollback
    void getRelatedAuthor_byAuthorId() {
        assert authorDao!=null;
        assertNotNull(authorDao.getRelatedAuthor_byAuthorId(1));
    }

    @Test
    @Transactional
    @Rollback
    void getRelatedAuthor_byOrgId() {
        assert authorDao!=null;
        assertNotNull(authorDao.getRelatedAuthor_byOrgId(1));
    }

    @Test
    @Transactional
    @Rollback
    void getHistory() {
        assert authorDao!=null;
        assertNotNull(authorDao.getHistory(1));
    }

    @Test
    @Transactional
    @Rollback
    void getTitles() {
        assert authorDao!=null;
        assertNotNull(authorDao.getTitles(1));
    }
}