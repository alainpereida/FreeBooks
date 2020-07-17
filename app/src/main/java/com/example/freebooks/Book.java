package com.example.freebooks;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("synopsis")
    private String mSynopsis;
    @SerializedName("year")
    private String mYear;

    public Book(int id, String title, String author, String synopsis, String year) {
        this.mId = id;
        this.mTitle = title;
        this.mAuthor = author;
        this.mSynopsis = synopsis;
        this.mYear = year;
    }

    public Book(String title, String author, String synopsis, String year) {
        this.mTitle = title;
        this.mAuthor = author;
        this.mSynopsis = synopsis;
        this.mYear = year;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        this.mYear = year;
    }
}

