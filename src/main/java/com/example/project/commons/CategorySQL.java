package com.example.project.commons;

public class CategorySQL {
    public static final String insert = "INSERT INTO Category (id, category_name) VALUES (?, ?)";
    public static final String get = "SELECT * FROM Category WHERE id = ?";
}

