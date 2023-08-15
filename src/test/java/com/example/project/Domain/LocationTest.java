package com.example.project.Domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void testGetId() {

        int id = 1;
        Location location = new Location(id, "Test Location");


        int result = location.getId();


        assertEquals(id, result);
    }

    @Test
    void testSetId() {

        int id = 1;
        Location location = new Location();
        location.setId(id);
        assertEquals(id, location.getId());
    }

    @Test
    void testGetLocationName() {

        String locationName = "Test Location";
        Location location = new Location(1, locationName);
        String result = location.getLocation_name();
        assertEquals(locationName, result);
    }

    @Test
    void testSetLocationName() {
        String locationName = "Test Location";
        Location location = new Location();
        location.setLocation_name(locationName);
        assertEquals(locationName, location.getLocation_name());
    }
}
