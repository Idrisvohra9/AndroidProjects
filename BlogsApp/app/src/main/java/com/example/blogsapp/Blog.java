package com.example.blogsapp;

public class Blog {
    private int id;
    private String title;
    private String body;
    private int byUser;
    private String createdAt;
    private String updatedAt;

    // Constructor
    public Blog(int id, String title, String body, int byUser, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.byUser = byUser;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getByUser() {
        return byUser;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
