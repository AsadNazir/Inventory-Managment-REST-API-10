package com.example.project.Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.project.Domain.Category;
import com.example.project.Domain.Location;
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
        List<Item> itemsFormatted = new ArrayList<Item>();

        try {
            PreparedStatement statement = this.C.prepareStatement(ItemSQL.getAll);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String itemName = resultSet.getString("item_name");
                int itemQuantity = resultSet.getInt("item_quantity");
                int categoryId = resultSet.getInt("item_category_id");
                int locationId = resultSet.getInt("item_location_id");


                Category category = new CategoryService().getCategoryById(categoryId);
                Location location = new LocationService().getLocationById(locationId);

                Item newItem = new Item(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }


            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();

            this.C.close();
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
        List<Item> itemsFormatted = new ArrayList<>();

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


                Category category = new CategoryService().getCategoryById(categoryId);
                Location location = new LocationService().getLocationById(locationId);

                Item newItem = new Item(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }

            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            this.C.close();
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
        List<Item> itemsFormatted = new ArrayList<Item>();

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

                Category category = new CategoryService().getCategoryById(categoryId);
                Location location = new LocationService().getLocationById(locationId);

                Item newItem = new Item(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }

            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();

            this.C.close();
            //Error JSON
            if (itemsFormatted.isEmpty())
                return objectMapper.writeValueAsString(new ErrorMessage("No Records In DB"));

            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getItemsByLocation(int id) {
        List<Item> itemsFormatted = new ArrayList<Item>();

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


                Category category = new CategoryService().getCategoryById(categoryId);
                Location location = new LocationService().getLocationById(locationId);

                Item newItem = new Item(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }

            this.C.close();
            // Convert the List of items to the requested JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getItemByLocationAndCategory(int loc_id, int cat_id) {
        List<Item> itemsFormatted = new ArrayList<Item>();

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

                Category category = new CategoryService().getCategoryById(categoryId);
                Location location = new LocationService().getLocationById(locationId);

                Item newItem = new Item(itemId, itemName, itemQuantity, location, category);
                itemsFormatted.add(newItem);
            }

            this.C.close();
            // Convert the List of items to the JSON format using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String InsertItem(Item item) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            Category C = new CategoryService().getCategoryById(item.getItem_category().getId());
            Location L = new LocationService().getLocationById(item.getItem_location().getId());
            if (C == null || L == null) {
                return objectMapper.writeValueAsString(new ErrorMessage("Invalid Payload"));
            }
            PreparedStatement statement = this.C.prepareStatement(ItemSQL.insert);
            statement.setString(1, item.getItem_name());
            statement.setInt(2, item.getItem_quantity());
            statement.setInt(3, item.getItem_category().getId());
            statement.setInt(4, item.getItem_location().getId());

            int rowsAffected = statement.executeUpdate();


            this.C.close();

            if (rowsAffected > 0) {
                return objectMapper.writeValueAsString(new ResponseMessage("OK"));
            }

            return objectMapper.writeValueAsString(new ErrorMessage("Invalid Payload"));

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public String updateItem(int id, Item item) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            Category C = new CategoryService().getCategoryById(item.getItem_category().getId());
            Location L = new LocationService().getLocationById(item.getItem_location().getId());

            if (C == null || L == null) {
                return objectMapper.writeValueAsString(new ErrorMessage("Invalid Payload").getError_message());
            }

            PreparedStatement statement = this.C.prepareStatement(ItemSQL.update);
            statement.setString(1, item.getItem_name());
            statement.setInt(2, item.getItem_quantity());
            statement.setInt(3, item.getItem_category().getId());
            statement.setInt(4, item.getItem_location().getId());
            statement.setInt(5, id);

            int rowsAffected = statement.executeUpdate();


            if (rowsAffected > 0) {
                return objectMapper.writeValueAsString(new ResponseMessage("OK"));
            }

            this.C.close();

            return objectMapper.writeValueAsString(new ErrorMessage("Invalid Payload"));

        } catch (Exception e) {
            e.printStackTrace();

        }


        return null;

    }

    public String deleteItem(int id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PreparedStatement statement = this.C.prepareStatement(ItemSQL.delete);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return objectMapper.writeValueAsString(new ResponseMessage("OK"));
            }

            this.C.close();

            return objectMapper.writeValueAsString(new ErrorMessage("Invalid ID"));

        } catch (Exception e) {
            e.printStackTrace();

        }


        return null;
    }


}

class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
