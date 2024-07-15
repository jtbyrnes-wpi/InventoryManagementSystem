package com.example.inventory;

import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    //instance name: beaming-octagon-429300-m2:us-east1:inventory3
    // Database connection parameters


    private static final String jdbcUrl = "jdbc:mysql:///inventory?"
    + "cloudSqlInstance=beaming-octagon-429300-m2:us-east1:inventory3"
    + "&socketFactory=com.google.cloud.sql.mysql.SocketFactory";

    private static final String DB_URL = "jdbc:mysql://34.73.158.84:3306/inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    //https://cloud.google.com/docs/authentication/provide-credentials-adc#local-user-cred

    //private com.example.inventory.Inventory inventory = new com.example.inventory.Inventory(100); // Initialize with a count of 100

    // GET API: Retrieve the current inventory count
    @GetMapping("/count")
    public int getInventoryCount() {
        int count = 0;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            try (Connection conn = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) {
                // Prepare a statement to execute a query
                String sql = "SELECT inventory_count FROM inventory WHERE item_id ='12345';";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    // Execute the query
                    try (ResultSet rs = stmt.executeQuery()) {
                        // Retrieve the inventory count from the result set
                        if (rs.next()) {
                            count = rs.getInt("inventory_count");
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
        return count;
    }

    // POST API: Update the inventory count by reducing it by one each time a purchase is made
    @PostMapping("/decrement")
    public void decrementInventoryCount() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            try (Connection conn = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) {
                // Prepare a statement to execute an update
                String sql = "UPDATE inventory SET inventory_count = inventory_count - 1 WHERE item_id = '12345'";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    // Set any parameters needed for your update statement (e.g., id of the item)
                    //stmt.setInt(12345, 1);

                    // Execute the update
                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Inventory count decremented successfully.");
                    } else {
                        System.out.println("Failed to decrement inventory count.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
    }

    /*
    //PUT API: for testing purposes
    @PutMapping
    public String addInventory() {
        if (inventory.getCount() < 100) {
            inventory.setCount(inventory.getCount() + 1);
            return "Inventory updated. Current count: " + inventory.getCount();
        } else {
            return "Inventory is already full.";
        }
    }

     */

}
