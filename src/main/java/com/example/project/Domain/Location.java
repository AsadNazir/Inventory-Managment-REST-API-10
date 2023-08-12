package com.example.project.Domain;

import java.io.Serializable;

public class Location implements Serializable{
    int id;

    public Location() {
    }

    String location_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", location_name='" + location_name + '\'' +
                '}';
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public Location(int id, String location_name) {
        this.id = id;
        this.location_name = location_name;
    }
}
