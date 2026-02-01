package service;


import dao.BookingDAO;
import dao.CustomerDAO;
import dao.RoomDAO;
import model.Customer;

import java.time.LocalDate;

public class BookingService {

    private BookingDAO bookingDAO = new BookingDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private RoomDAO roomDAO = new RoomDAO();

    // Book a room
    public boolean bookRoom(Customer customer, int roomId,
                            LocalDate checkIn, LocalDate checkOut) {

        // check if room already booked
        if (bookingDAO.isRoomBooked(roomId)) {
            return false;
        }

        int customerId = customerDAO.addCustomer(customer);
        bookingDAO.createBooking(customerId, roomId, checkIn, checkOut);
        roomDAO.updateRoomStatus(roomId, "Occupied");

        return true;
    }

    // Cancel booking with verification
    public boolean cancelBooking(int customerId, String customerName, int roomId) {

        boolean isValidCustomer = customerDAO.verifyCustomer(customerId, customerName);
        if (!isValidCustomer) {
            return false;
        }

        int bookingId = bookingDAO.getBookingId(customerId, roomId);
        if (bookingId == 0) {
            return false;
        }

        bookingDAO.cancelBooking(bookingId);
        roomDAO.updateRoomStatus(roomId, "Available");

        return true;
    }
}

