package com.example.project.Domain;

import com.example.project.Services.CategorySQL;
import com.example.project.Services.LocationSQL;

public class NewItem {
    int item_id;
    String item_name;
    int item_quantity;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
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

    public NewItem(int item_id, String item_name, int item_quantity, Location item_location, Category item_category) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.item_location = item_location;
        this.item_category = item_category;
    }

    Location item_location;
    Category item_category;

}
