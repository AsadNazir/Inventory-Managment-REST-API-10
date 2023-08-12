package com.example.project.Domain;

import java.io.Serializable;

public class Category implements Serializable {
    int id;

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", category_name='" + category_name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Category(int id, String category_name) {
        this.id = id;
        this.category_name = category_name;
    }

    String category_name;

}
