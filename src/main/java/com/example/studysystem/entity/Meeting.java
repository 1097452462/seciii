package com.example.studysystem.entity;

public class Meeting {
    private int id=0;
    private String Publication_title;
    private String Publication_year;
    private int Paper_num;
    private int Citation_sum;

    public String getPublication_title() {
        return Publication_title;
    }

    public void setPublication_title(String publication_title) {
        Publication_title = publication_title;
    }

    public String getPublication_year() {
        return Publication_year;
    }

    public void setPublication_year(String publication_year) {
        Publication_year = publication_year;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


}
