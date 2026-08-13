package com.mycompany.ai;

public class Paper {
    private String title;
    private String author;
    private String year;
    private String journal;
    private String content;

    public Paper(String title, String author, String year, String journal, String content) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.journal = journal;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getYear() { return year; }
    public String getJournal() { return journal; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return title + " — " + author + ", " + year;
    }
}