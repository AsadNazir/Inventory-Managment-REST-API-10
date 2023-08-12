package com.example.project.Domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testGetId() {
        Category category = new Category(1, "Electronics");
        assertEquals(1, category.getId());
    }

    @Test
    void testSetId() {
        Category category = new Category();
        category.setId(2);
        assertEquals(2, category.getId());
    }

    @Test
    void testGetCategory_name() {
        Category category = new Category(1, "Electronics");
        assertEquals("Electronics", category.getCategory_name());
    }

    @Test
    void testSetCategory_name() {
        Category category = new Category();
        category.setCategory_name("Clothing");
        assertEquals("Clothing", category.getCategory_name());
    }
}
