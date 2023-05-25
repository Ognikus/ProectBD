package com.example.testbd;

public class Category {
    Integer categoryId;
    String categoryName;

    public Category(String categoryId, String categoryName) {
        this.categoryId = Integer.valueOf(categoryId);
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
