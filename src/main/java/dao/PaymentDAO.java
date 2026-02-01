package dao;


import model.Payment;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;

public class PaymentDAO {

    // Add a payment record
    public void addPayment(Payment payment) {

        String sql = "INSERT INTO payments (booking_id, amount, payment_date, payment_status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setDate(3, Date.valueOf(payment.getPaymentDate()));
            ps.setString(4, payment.getPaymentStatus());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get payment details by booking ID
    public Payment getPaymentByBookingId(int bookingId) {
        Payment payment = null;
        String sql = "SELECT * FROM payments WHERE booking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setBookingId(rs.getInt("booking_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentDate(rs.getDate("payment_date").toLocalDate());
                payment.setPaymentStatus(rs.getString("payment_status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }
}

