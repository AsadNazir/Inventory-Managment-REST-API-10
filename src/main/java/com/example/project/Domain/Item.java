package com.example.project.Domain;

import java.io.Serializable;

public class Item implements Serializable {
    int id;
    String item_name;
    int item_quantity;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public Location getItem_location() {
        return item_location;
    }

    public void setItem_location(Location item_location) {
        this.item_location = item_location;
    }

    public Category getItem_category() {
        return item_category;
    }

    public void setItem_category(Category item_category) {
        this.item_category = item_category;
    }

    public Item(int id, String item_name, int item_quantity, Location item_location, Category item_category) {
        this.id = id;
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.item_location = item_location;
        this.item_category = item_category;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", item_name='" + item_name + '\'' +
                ", item_quantity=" + item_quantity +
                ", item_location=" + item_location +
                ", item_category=" + item_category +
                '}';
    }

    Location item_location;
    Category item_category;

}
