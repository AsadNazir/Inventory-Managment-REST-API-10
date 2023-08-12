package com.example.project.Domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testGetId() {
        Item item = new Item(1, "Phone", 10, new Location(24, "Phone"), new Category(7, "Electronics"));
        assertEquals(1, item.getId());
    }

    @Test
    void testGetItem_name() {
        Item item = new Item(1, "Phone", 10, new Location(24, "Phone"), new Category(7, "Electronics"));
        assertEquals("Phone", item.getItem_name());
    }

    @Test
    void testGetItem_quantity() {
        Item item = new Item(1, "Phone", 10, new Location(24, "Phone"), new Category(7, "Electronics"));
        assertEquals(10, item.getItem_quantity());
    }

    @Test
    void testGetItem_location() {
        Location location = new Location(24, "Phone");
        Item item = new Item(1, "Phone", 10, location, new Category(7, "Electronics"));
        assertEquals(location, item.getItem_location());
    }

    @Test
    void testGetItem_category() {
        Category category = new Category(7, "Electronics");
        Item item = new Item(1, "Phone", 10, new Location(24, "Phone"), category);
        assertEquals(category, item.getItem_category());
    }

}
