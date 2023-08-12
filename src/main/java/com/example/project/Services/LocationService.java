package com.example.project.Services;

import com.example.project.Domain.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationService {
    private Connection C;
    public LocationService()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            C = Db.getDataSource().getConnection();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public Location getLocationById(int locationId) {
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
