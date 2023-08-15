package com.example.project.commons;

public class ItemSQL {
    public static final String insert = "INSERT INTO item (item_name, item_quantity, item_category_id, item_location_id) VALUES (?, ?, ?, ?)";
    public static final String getAll = "SELECT * FROM item";
    public static final String getById = "SELECT * FROM item where id = ?";
    public static final String getByLocationAndCategory = "SELECT * FROM item WHERE item_location_id = ? AND item_category_id = ?";
    public static final String update = "UPDATE item SET item_name = ?, item_quantity = ?, item_category_id = ?, item_location_id = ? WHERE id = ?";
    public static final String getByCategory = "SELECT * FROM item WHERE item_category_id = ?";
    public static final String getByLocation = "SELECT * FROM item WHERE item_location_id = ?";
    public static final String delete = "DELETE FROM item WHERE id = ?";
}
