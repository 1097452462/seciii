package com.example.studysystem.entity;

public class Author {
    private int id=0;
    private String Author_name;
    private String Org;
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

    public String getAuthor_name() {
        return Author_name;
    }

    public void setAuthor_name(String author_name) {
        Author_name = author_name;
    }

    public String getOrg() {
        return Org;
    }

    public void setOrg(String org_name) {
        Org = org_name;
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

    public double getPoint() {
        return Point;
    }

    public void setPoint(float rank) {
        Point = rank;
    }

}
