package com.example.meowgallery;

// GalleryItem.java
public class GalleryItem {
    private int imageResource;
    private String description;

    public GalleryItem(int imageResource, String description) {
        this.imageResource = imageResource;
        this.description = description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }
}

