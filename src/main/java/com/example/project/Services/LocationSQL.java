package com.example.project.Services;

public class LocationSQL {
    public static final String insert = "INSERT INTO Location (id, location_name) VALUES (?, ?)";
    public static final String get = "SELECT * FROM Location WHERE id = ?";
}
