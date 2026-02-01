package main;


import model.Customer;
import model.Room;
import service.BookingService;
import service.LoginService;
import service.RoomService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LoginService loginService = new LoginService();
        RoomService roomService = new RoomService();
        BookingService bookingService = new BookingService();

        System.out.println("=== Hotel Management System ===");

        // Login
        System.out.print("Username: ");
        String username = sc.next();
        System.out.print("Password: ");
        String password = sc.next();

        if (loginService.login(username, password) == null) {
            System.out.println("Invalid login credentials");
            return;
        }

        while (true) {
            System.out.println("\n1. View All Rooms");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Book Room");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    List<Room> rooms = roomService.getAllRooms();
                    rooms.forEach(r ->
                            System.out.println(r.getRoomId() + " | " +
                                    r.getRoomNumber() + " | " +
                                    r.getRoomType() + " | " +
                                    r.getPrice() + " | " +
                                    r.getStatus()));
                    break;

                case 2:
                    List<Room> availableRooms = roomService.getAvailableRooms();
                    availableRooms.forEach(r ->
                            System.out.println(r.getRoomId() + " | " +
                                    r.getRoomNumber() + " | " +
                                    r.getRoomType() + " | " +
                                    r.getPrice()));
                    break;

                case 3:
                    sc.nextLine();
                    System.out.print("Customer Name: ");
                    String name = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.next();
                    System.out.print("Email: ");
                    String email = sc.next();
                    System.out.print("ID Proof: ");
                    String idProof = sc.next();
                    System.out.print("Room ID: ");
                    int roomId = sc.nextInt();

                    boolean booked = bookingService.bookRoom(
                            new Customer(name, phone, email, idProof),
                            roomId,
                            LocalDate.now(),
                            LocalDate.now().plusDays(2)
                    );

                    System.out.println(booked ? "Room booked successfully"
                            : "Room already booked");
                    break;

                case 4:
                    System.out.print("Customer ID: ");
                    int customerId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Customer Name: ");
                    String custName = sc.nextLine();
                    System.out.print("Room ID: ");
                    int cancelRoomId = sc.nextInt();

                    boolean cancelled = bookingService.cancelBooking(
                            customerId, custName, cancelRoomId);

                    System.out.println(cancelled ? "Booking cancelled successfully"
                            : "Cancellation failed (verification error)");
                    break;

                case 5:
                    System.out.println("Thank you for using the system");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}

