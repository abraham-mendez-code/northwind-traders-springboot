package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class JdbcProductDao implements IProductDao{

    // This is the DataSource that we will use to connect to the database.
    // The DataSource is created in our DbConfiguration class.
    private final DataSource dataSource;
    private final Scanner scanner = new Scanner(System.in);

    // This is a constructor.
    // Spring will automatically call this constructor and pass in the DataSource.
    // The @Autowired annotation tells Spring to "inject" the DataSource Bean here.
    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // This method will add a new Product to the database.
    // It is required because we are implementing the IProductDao interface.
    @Override
    public void add(Product product) {

        // This is a SQL INSERT statement we will run.
        String sql = """
                INSERT INTO
                	Products(ProductName, CategoryID, UnitPrice)
                VALUES(?, ?, ?);
                """;

        // This is a "try-with-resources" block.
        // It ensures that the Connection and PreparedStatement are closed automatically after we are done.
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            // Set the first parameter (?) to the product's name.
            preparedStatement.setString(1, product.getProductName());

            // Set the second parameter (?) to the product's category id.
            preparedStatement.setInt(2, product.getCategoryId());

            // Set the third parameter (?) to the product's unit price.
            preparedStatement.setDouble(3, product.getUnitPrice());

            // Execute the INSERT statement — this will add the row to the database.
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.out.println("ERROR: Unable to add product to the database " + e);
        }

    }

    // This method will return a list of all Products from the database.
    // It is required because we are implementing the IProductDao interface.
    @Override
    public List<Product> getAll() {
        // Create an empty list to hold the Product objects we will retrieve.
        List<Product> products = new ArrayList<>();

        // This is the SQL SELECT statement we will run.
        String sql = """
                SELECT
                    ProductID,
                    ProductName,
                    CategoryID,
                    UnitPrice
                FROM
                    Products
                """;

        // This is a "try-with-resources" block.
        // It ensures that the Connection, Statement, and ResultSet are closed automatically after we are done.
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet results = preparedStatement.executeQuery()
                ) {

            // Loop through each row in the ResultSet
            while(results.next()) {
                // Create a Product object
                Product product = new Product();

                // Set the product's ID from the "ProductID" column.
                product.setProductId(results.getInt("ProductID"));

                // Set the product's name from the "ProductName" column.
                product.setProductName(results.getString("ProductName"));

                // Set the product's category id from the "CategoryID" column.
                product.setCategoryId(results.getInt("CategoryID"));

                // Set the product's unit price from the "UnitPrice" column.
                product.setUnitPrice(results.getDouble("UnitPrice"));

                // Add the Product object to the list
                products.add(product);
            }
        }catch (SQLException e){
            // If something goes wrong (SQL error), print a message and the stack trace to help debug.
            System.out.println("ERROR: Unable to retrieve products list from DB " + e);
        }

        // Return the list of Product objects
        return products;
    }

    // This method will remove a Product from the database.
    // It is required because we are implementing the IProductDao interface.
    @Override
    public void deleteByID(int productId) {

        // This is the SQL UPDATE statement we will run.
        String sql = """
                DELETE FROM
                    Products
                WHERE
                    ProductID = ?
                """;

        // This is a "try-with-resources" block.
        // It ensures that the Connection, Statement, and ResultSet are closed automatically after we are done.
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            // Set the first parameter (?) to the product's id.
            preparedStatement.setInt(1, productId);

            // Execute the DELETE statement — this will delete the row from the database.
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR: Unable to remove product from the db " + e);
        }
    }

    // This method will remove a Product from the database.
    // It is required because we are implementing the IProductDao interface.
    @Override
    public List<Product> findById(int productId) {

        // Create an empty list to hold the Product objects we will retrieve.
        List<Product> products = new ArrayList<>();

        // This is the SQL SELECT statement we will run.
        String sql = """
                SELECT
                    ProductID,
                    ProductName,
                    CategoryID,
                    UnitPrice
                FROM
                    Products
                WHERE
                    ProductID = ?
                """;

        // This is a "try-with-resources" block.
        // It ensures that the Connection, Statement, and ResultSet are closed automatically after we are done.
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            // Set the first parameter (?) to the product's id.
            preparedStatement.setInt(1, productId);

            // Execute the SELECT statement — this will retrieve matching the row from the database.
            try (ResultSet results = preparedStatement.executeQuery()) {

                // Loop through each row in the ResultSet
                while(results.next()) {
                    // Create a Product object
                    Product product = new Product();

                    // Set the product's ID from the "ProductID" column.
                    product.setProductId(results.getInt("ProductID"));

                    // Set the product's name from the "ProductName" column.
                    product.setProductName(results.getString("ProductName"));

                    // Set the product's category id from the "CategoryID" column.
                    product.setCategoryId(results.getInt("CategoryID"));

                    // Set the product's unit price from the "UnitPrice" column.
                    product.setUnitPrice(results.getDouble("UnitPrice"));

                    // Add the Product object to the list
                    products.add(product);
                }

            }

        }catch (SQLException e){
            // If something goes wrong (SQL error), print a message and the stack trace to help debug.
            System.out.println("ERROR: Unable to retrieve products list from DB " + e);
        }

        // Return the list of Product objects
        return products;

    }

    // This method will update a Product in the database.
    // It is required because we are implementing the IProductDao interface.
    @Override
    public void update(Product product) {

        // This is the SQL UPDATE statement we will run.
        String sql = """
                UPDATE
                    Products
                SET
                    ProductName = ?,
                    CategoryID = ?,
                    UnitPrice = ?
                WHERE
                    ProductID = ?
                """;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {

            // Set the first parameter (?) to the product's name.
            preparedStatement.setString(1, product.getProductName());

            // Set the second parameter (?) to the product's category id.
            preparedStatement.setInt(2, product.getCategoryId());

            // Set the third parameter (?) to the product's unit price.
            preparedStatement.setDouble(3, product.getUnitPrice());

            // Set the fourth parameter (?) to the product's id.
            preparedStatement.setInt(4, product.getProductId());

            // Execute the UPDATE statement — this will update the row in the database.
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("ERROR: Unable to update product " + e);
        }
    }
}
