package com.example.studysystem.entity;

public class Field {
    private int id;
    private String Field_name;
    private String Paper_list;
    private int Paper_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField_name() {
        return Field_name;
    }

    public void setField_name(String field_name) {
        Field_name = field_name;
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
}