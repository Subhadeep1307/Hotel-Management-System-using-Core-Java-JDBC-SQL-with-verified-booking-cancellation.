package dao;

import model.Customer;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerDAO {

    // Add customer and return generated customer_id
    public int addCustomer(Customer customer) {
        int customerId = 0;
        String sql = "INSERT INTO customers (name, phone, email, id_proof) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getIdProof());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                customerId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerId;
    }

    // Verify customer by ID and Name (for cancellation)
    public boolean verifyCustomer(int customerId, String name) {
        String sql = "SELECT 1 FROM customers WHERE customer_id = ? AND name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.setString(2, name);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}


