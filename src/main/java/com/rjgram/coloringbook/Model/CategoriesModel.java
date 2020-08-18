package com.rjgram.coloringbook.Model;

public class CategoriesModel {
    private int imgCategories;
    private String title;

    public CategoriesModel(int imgCategories, String title) {
        this.imgCategories = imgCategories;
        this.title = title;
    }

    public int getImgCategories() {
        return imgCategories;
    }

    public void setImgCategories(int imgCategories) {
        this.imgCategories = imgCategories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
