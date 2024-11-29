package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment {
    private String paymentId;     // Unique identifier for the payment
    private String tenantId;      // Tenant ID
    private String rentalAgreementId; // Rental Agreement ID
    private double amount;        // Amount of the payment
    private Date date;            // Date of the payment
    private String paymentMethod; // Method of payment (e.g., Credit Card, Bank Transfer)

    // Constructors
    public Payment(String paymentId, String tenantId, String rentalAgreementId, double amount, Date date, String paymentMethod) {
        this.paymentId = paymentId;
        this.tenantId = tenantId;
        this.rentalAgreementId = rentalAgreementId;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRentalAgreementId() {
        return rentalAgreementId;
    }

    public void setRentalAgreementId(String rentalAgreementId) {
        this.rentalAgreementId = rentalAgreementId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Method to validate payment details (extend logic as needed)
    public boolean validatePayment() {
        // Example placeholder: Ensure amount is positive and date is not null
        return amount > 0 && date != null && paymentMethod != null && !paymentMethod.isEmpty();
    }

    // Override toString for better debugging and display
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = (date != null) ? dateFormat.format(date) : "N/A";
        return paymentId + "|" + tenantId + "|" + rentalAgreementId + "|" + amount + "|" + formattedDate + "|" + paymentMethod;
    }
}