package com.example.historyquiz;

public class CategoryScore {

    private int id;
    private String difficulty;
    private int bestscore;
    private int categoryID;

    public CategoryScore() {

    }

    public CategoryScore(int id, String difficulty, int bestscore, int categoryID) {
        this.id = id;
        this.difficulty = difficulty;
        this.categoryID = categoryID;
        this.bestscore = bestscore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getBestscore() {
        return bestscore;
    }

    public void setBestscore(int bestscore) {
        this.bestscore = bestscore;
    }
}
