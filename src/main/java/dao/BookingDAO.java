package dao;



import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;

public class BookingDAO {

    // Create a new booking
    public int createBooking(int customerId, int roomId,
                             LocalDate checkIn, LocalDate checkOut) {

        int bookingId = 0;
        String sql = "INSERT INTO bookings (customer_id, room_id, check_in, check_out, status) " +
                "VALUES (?, ?, ?, ?, 'Booked')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, customerId);
            ps.setInt(2, roomId);
            ps.setDate(3, Date.valueOf(checkIn));
            ps.setDate(4, Date.valueOf(checkOut));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bookingId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingId;
    }

    // Check if room is already booked
    public boolean isRoomBooked(int roomId) {
        String sql = "SELECT 1 FROM bookings WHERE room_id = ? AND status = 'Booked'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cancel booking
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get booking ID by customer and room (used in cancellation)
    public int getBookingId(int customerId, int roomId) {
        String sql = "SELECT booking_id FROM bookings " +
                "WHERE customer_id = ? AND room_id = ? AND status = 'Booked'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.setInt(2, roomId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("booking_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
