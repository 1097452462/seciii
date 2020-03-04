package com.example.studysystem.entity;

public class SearchForm {
    private int id;
    private String Authors;
    private String Author_Affiliations;
    private String Publication_Title;
    private String Author_Keywords;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthors() {
        return Authors;
    }

    public void setAuthors(String authors) {
        Authors = authors;
    }

    public String getAuthor_Affiliations() {
        return Author_Affiliations;
    }

    public void setAuthor_Affiliations(String author_Affiliations) {
        Author_Affiliations = author_Affiliations;
    }

    public String getPublication_Title() {
        return Publication_Title;
    }

    public void setPublication_Title(String publication_Title) {
        Publication_Title = publication_Title;
    }

    public String getAuthor_Keywords() {
        return Author_Keywords;
    }

    public void setAuthor_Keywords(String author_Keywords) {
        Author_Keywords = author_Keywords;
    }
}
