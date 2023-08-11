package com.example.project.Services;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.project.Domain.Category;
import com.example.project.Domain.Location;
import com.example.project.Domain.NewItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.project.Domain.Item;

public class InventoryService {

    Connection C;

    public InventoryService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            C = Db.getDataSource().getConnection();
        } catch (Exception E) {
            E.printStackTrace();
        }

    }

    public String getAll() {
        List<NewItem> itemsFormatted = new ArrayList<>();

        try {
            PreparedStatement statement = this.C.prepareStatement(ItemSQL.getAll);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String itemName = resultSet.getString("item_name");
                int itemQuantity = resultSet.getInt("item_quantity");
                int categoryId = resultSet.getInt("item_category_id");
                int locationId = resultSet.getInt("item_location_id");


                Category category = getCategoryById(categoryId);
                Location location = getLocationById(locationId);

                NewItem newItem = new NewItem(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }


            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();


            //Error JSON
            if (itemsFormatted.isEmpty())
                return objectMapper.writeValueAsString(new ErrorMessage("No Records In DB").getError_message());

            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getRecordbyId(int id) {
        List<NewItem> itemsFormatted = new ArrayList<>();

        try {
            PreparedStatement statement = this.C.prepareStatement(ItemSQL.getById);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String itemName = resultSet.getString("item_name");
                int itemQuantity = resultSet.getInt("item_quantity");
                int categoryId = resultSet.getInt("item_category_id");
                int locationId = resultSet.getInt("item_location_id");


                Category category = getCategoryById(categoryId);
                Location location = getLocationById(locationId);

                NewItem newItem = new NewItem(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }

            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();

            //Error JSON
            if (itemsFormatted.isEmpty())
                return objectMapper.writeValueAsString(new ErrorMessage("No Records In DB").getError_message());

            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String getItemsByCategory(int id) {
        List<NewItem> itemsFormatted = new ArrayList<>();

        try {
            PreparedStatement statement = this.C.prepareStatement(ItemSQL.getByCategory);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String itemName = resultSet.getString("item_name");
                int itemQuantity = resultSet.getInt("item_quantity");
                int categoryId = resultSet.getInt("item_category_id");
                int locationId = resultSet.getInt("item_location_id");

                Category category = getCategoryById(categoryId);
                Location location = getLocationById(locationId);

                NewItem newItem = new NewItem(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }

            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();

            //Error JSON
            if (itemsFormatted.isEmpty())
                return objectMapper.writeValueAsString(new ErrorMessage("No Records In DB").getError_message());

            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getItemsByLocation(int id) {
        List<NewItem> itemsFormatted = new ArrayList<>();

        try {
            PreparedStatement statement = this.C.prepareStatement(ItemSQL.getByLocation);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String itemName = resultSet.getString("item_name");
                int itemQuantity = resultSet.getInt("item_quantity");
                int categoryId = resultSet.getInt("item_category_id");
                int locationId = resultSet.getInt("item_location_id");


                Category category = getCategoryById(categoryId);
                Location location = getLocationById(locationId);

                NewItem newItem = new NewItem(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }

            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getItemByLocationAndCategory(int loc_id, int cat_id) {
        List<NewItem> itemsFormatted = new ArrayList<>();

        try {
            PreparedStatement statement = this.C.prepareStatement(ItemSQL.getByLocationAndCategory);
            statement.setInt(1, loc_id);
            statement.setInt(2, cat_id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String itemName = resultSet.getString("item_name");
                int itemQuantity = resultSet.getInt("item_quantity");
                int categoryId = resultSet.getInt("item_category_id");
                int locationId = resultSet.getInt("item_location_id");

                Category category = getCategoryById(categoryId);
                Location location = getLocationById(locationId);

                NewItem newItem = new NewItem(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }

            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Category getCategoryById(int categoryId) {
        // Implement your logic to retrieve a Category by its ID
        try {
            PreparedStatement statement = this.C.prepareStatement(CategorySQL.get);
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String categoryName = resultSet.getString("category_name");
                return new Category(categoryId, categoryName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Location getLocationById(int locationId) {
        // Implement your logic to retrieve a Location by its ID
        try {
            PreparedStatement statement = this.C.prepareStatement(LocationSQL.get);
            statement.setInt(1, locationId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String locationName = resultSet.getString("location_name");
                return new Location(locationId, locationName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}


class ErrorMessage {
    private String error_message;

    public ErrorMessage(String error_message) {
        this.error_message = error_message;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
