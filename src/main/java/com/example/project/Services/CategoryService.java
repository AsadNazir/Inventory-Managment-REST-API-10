package com.example.project.Services;

import java.sql.*;

import com.example.project.Domain.Category;

public class CategoryService {
    Connection C;
    public CategoryService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            C = Db.getDataSource().getConnection();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
        public Category getCategoryById(int categoryId) {
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

}
