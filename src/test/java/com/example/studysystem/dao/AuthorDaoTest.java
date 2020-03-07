package com.example.studysystem.dao;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDaoTest {
    AuthorDao a=new AuthorDao();

    @Test
    void sortByAuthor() {
        List<List<String>> l1=a.sortByAuthor();
        List<List<String>> l2=a.sortByAuthor();
        assertEquals(l1,l2);
    }
}