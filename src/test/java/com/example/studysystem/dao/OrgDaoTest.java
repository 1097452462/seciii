package com.example.studysystem.dao;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrgDaoTest {
    OrgDao o=new OrgDao();
    @Test
    void sortByAffiliation() {
        List<List<String>> l1=o.sortByAffiliation();
        List<List<String>> l2=o.sortByAffiliation();
        assertEquals(l1,l2);
    }
}