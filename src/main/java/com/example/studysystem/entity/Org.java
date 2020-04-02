package com.example.studysystem.entity;

public class Org {
    private int id=0;
    private String Org_name;
    private String Author_list;
    private String Paper_list;
    private int Paper_num;
    private int Citation_sum;
    private float Point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrg_name() {
        return Org_name;
    }

    public void setOrg_name(String org_name) {
        Org_name = org_name;
    }

    public String getAuthor_list() {
        return Author_list;
    }

    public void setAuthor_list(String author_list) {
        Author_list = author_list;
    }

    public String getPaper_list() {
        return Paper_list;
    }

    public void setPaper_list(String paper_list) {
        Paper_list = paper_list;
    }

    public int getPaper_num() {
        return Paper_num;
    }

    public void setPaper_num(int paper_num) {
        Paper_num = paper_num;
    }
    public int getCitation_sum() {
        return Citation_sum;
    }

    public void setCitation_sum(int citation_sum) {
        Citation_sum = citation_sum;
    }

    public float getPoint() {
        return Point;
    }

    public void setPoint(float point) {
        Point = point;
    }
}
