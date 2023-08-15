package com.example.project.Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.project.Domain.Category;
import com.example.project.Domain.Location;
import com.example.project.commons.Db;
import com.example.project.commons.ItemSQL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.project.Domain.Item;
import com.github.benmanes.caffeine.cache.Cache;

public class InventoryService {

    Cache<Integer, Item> cache;

    public InventoryService(Cache<Integer, Item> Cache) {
        cache = Cache;

    }

    public String getAll() {
        List<Item> itemsFormatted = new ArrayList<>();
// Convert the List of items to the requested JSON format using Jackson
        ObjectMapper objectMapper = new ObjectMapper();


        try (
                Connection C = Db.getDataSource();
                PreparedStatement statement = C.prepareStatement(ItemSQL.getAll);
                ResultSet resultSet = statement.executeQuery();

        ) {




            while (resultSet.next()) {
                int itemId = resultSet.getInt("id");
                String itemName = resultSet.getString("item_name");
                int itemQuantity = resultSet.getInt("item_quantity");
                int categoryId = resultSet.getInt("item_category_id");
                int locationId = resultSet.getInt("item_location_id");


                Category category = new CategoryService().getCategoryById(categoryId);
                Location location = new LocationService().getLocationById(locationId);

                Item newItem = new Item(itemId, itemName, itemQuantity, location, category);
                // Store the retrieved data in the cache

                if(cache.getIfPresent(itemId)==null)
                    cache.put(itemId, newItem);

                itemsFormatted.add(newItem);

            }
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

        // Convert the List of items to the requested JSON format using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        if(cache.estimatedSize()>0) System.out.println("Greater than 0");


        try {
            Item cachedItem = cache.getIfPresent(id);

            if (cachedItem != null) {
                System.out.println(cachedItem.toString());
                System.out.println("Present");
                // Return cached data
                return objectMapper.writeValueAsString(cachedItem);
            }

            System.out.println("not present");

        } catch (Exception E) {
            E.printStackTrace();
        }

        //Try with resources
        try (
                Connection C = Db.getDataSource();
                PreparedStatement statement = C.prepareStatement(ItemSQL.getById);

        ) {

            statement.setInt(1, id);  // Set the parameter here

            try (ResultSet resultSet = statement.executeQuery()) {


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
                    // Store the retrieved data in the cache
                    cache.put(itemId, newItem);
                }
            }

            //Error JSON
            if (itemsFormatted.isEmpty())
                return objectMapper.writeValueAsString(new ErrorMessage("No Records In DB").getError_message());

            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return null;
    }


    public String getItemsByCategory(int id) {
        List<Item> itemsFormatted = new ArrayList<>();
        // Convert the List of items to the requested JSON format using Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<Integer, Item> cachedItems = cache.asMap();
            if (cachedItems != null) {
                for (Map.Entry<Integer, Item> entry : cachedItems.entrySet()) {

                    Item value = entry.getValue();

                    if (value.getItem_category().getId() == id) {
                        System.out.println(value.toString());
                        return objectMapper.writeValueAsString(value);
                    }
                }

            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try (
                Connection C = Db.getDataSource();
                PreparedStatement statement = C.prepareStatement(ItemSQL.getById);

        ) {

            statement.setInt(1, id);

            //Try with resources
            try (
                    ResultSet resultSet = statement.executeQuery();

            ) {

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
                    // Store the retrieved data in the cache
                    cache.put(itemId, newItem);
                }

            }
            //Error JSON
            if (itemsFormatted.isEmpty()) return objectMapper.writeValueAsString(new ErrorMessage("No Records In DB"));

            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getItemsByLocation(int id) {
        List<Item> itemsFormatted = new ArrayList<>();

        // Convert the List of items to the requested JSON format using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            Map<Integer, Item> cachedItems = cache.asMap();
            if (cachedItems != null) {
                for (Map.Entry<Integer, Item> entry : cachedItems.entrySet()) {

                    Item value = entry.getValue();

                    if (value.getItem_location().getId() == id) {
                        return objectMapper.writeValueAsString(value);
                    }
                }

            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        //Try with resources
        try (
                Connection C = Db.getDataSource();
                PreparedStatement statement = C.prepareStatement(ItemSQL.getById);

        ) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {


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

                    // Store the retrieved data in the cache
                    cache.put(itemId, newItem);
                }
            }

            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getItemByLocationAndCategory(int loc_id, int cat_id) {
        List<Item> itemsFormatted = new ArrayList<>();
        // Convert the List of items to the requested JSON format using Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            Map<Integer, Item> cachedItems = cache.asMap();
            if (cachedItems != null) {
                for (Map.Entry<Integer, Item> entry : cachedItems.entrySet()) {

                    Item value = entry.getValue();

                    if (value.getItem_category().getId() == cat_id && value.getItem_location().getId() == loc_id) {
                        return objectMapper.writeValueAsString(value);
                    }
                }

            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try (
                Connection C = Db.getDataSource();
                PreparedStatement statement = C.prepareStatement(ItemSQL.getById);

        ) {

            statement.setInt(1, loc_id);
            statement.setInt(2, cat_id);
            try (ResultSet resultSet = statement.executeQuery()) {
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

                    // Store the retrieved data in the cache
                    cache.put(itemId, newItem);
                }
            }

            return objectMapper.writeValueAsString(itemsFormatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String InsertItem(Item item) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (
                Connection C = Db.getDataSource();
                PreparedStatement statement = C.prepareStatement(ItemSQL.getById);

        ) {

            Category Cat = new CategoryService().getCategoryById(item.getItem_category().getId());
            Location L = new LocationService().getLocationById(item.getItem_location().getId());
            if (Cat == null || L == null) {
                return objectMapper.writeValueAsString(new ErrorMessage("Invalid Payload"));
            }

            statement.setString(1, item.getItem_name());
            statement.setInt(2, item.getItem_quantity());
            statement.setInt(3, item.getItem_category().getId());
            statement.setInt(4, item.getItem_location().getId());

            int rowsAffected = statement.executeUpdate();
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
        try (
                Connection C = Db.getDataSource();
                PreparedStatement statement = C.prepareStatement(ItemSQL.getById);

        ) {

            Category Cat = new CategoryService().getCategoryById(item.getItem_category().getId());
            Location L = new LocationService().getLocationById(item.getItem_location().getId());

            if (Cat == null || L == null) {
                return objectMapper.writeValueAsString(new ErrorMessage("Invalid Payload").getError_message());
            }

            statement.setString(1, item.getItem_name());
            statement.setInt(2, item.getItem_quantity());
            statement.setInt(3, item.getItem_category().getId());
            statement.setInt(4, item.getItem_location().getId());
            statement.setInt(5, id);

            int rowsAffected = statement.executeUpdate();


            if (rowsAffected > 0) {
                return objectMapper.writeValueAsString(new ResponseMessage("OK"));
            }



            return objectMapper.writeValueAsString(new ErrorMessage("Invalid Payload"));

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    public String deleteItem(int id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (
                Connection C = Db.getDataSource();
                PreparedStatement statement = C.prepareStatement(ItemSQL.getById);

        ) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return objectMapper.writeValueAsString(new ResponseMessage("OK"));
            }

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
