package model;

import java.time.LocalDate;

/**
 * Paper.java
 * -----------
 * This is a simple model class representing one research paper.
 * NOTE: In the real team project, this class technically belongs to
 * "Member 2 - Research Paper Management". It is included here (in Member 3's
 * module) only so that Search & Organization can be tested independently.
 * When merging with Member 2's code, DELETE this file and use their Paper.java
 * instead (as long as it has the same field names, or update the getters below).
 */
public class Paper {

    private String title;
    private String author;
    private int year;
    private String category;
    private String filePath;      // path to PDF file
    private LocalDate dateAdded;  // when the paper was added
    private boolean favorite;     // bookmarked or not
    private LocalDate lastOpened; // for "Recent Papers" feature

    public Paper(String title, String author, int year, String category, String filePath) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.filePath = filePath;
        this.dateAdded = LocalDate.now();
        this.favorite = false;
        this.lastOpened = null;
    }

    // ---------- Getters & Setters ----------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public LocalDate getLastOpened() {
        return lastOpened;
    }

    public void markOpened() {
        this.lastOpened = LocalDate.now();
    }

    @Override
    public String toString() {
        return title + " (" + author + ", " + year + ")";
    }
}
