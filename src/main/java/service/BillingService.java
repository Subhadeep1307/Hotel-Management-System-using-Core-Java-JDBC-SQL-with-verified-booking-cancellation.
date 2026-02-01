package service;


import dao.PaymentDAO;
import model.Payment;

import java.time.LocalDate;

public class BillingService {

    private PaymentDAO paymentDAO = new PaymentDAO();

    // Generate and save payment
    public void makePayment(int bookingId, double amount) {

        Payment payment = new Payment(
                bookingId,
                amount,
                LocalDate.now(),
                "PAID"
        );

        paymentDAO.addPayment(payment);
    }

    // Fetch payment details
    public Payment getPaymentDetails(int bookingId) {
        return paymentDAO.getPaymentByBookingId(bookingId);
    }
}

