// Tenant.java
package model;

import java.util.Date;
import java.util.List;

public class Tenant extends Person {
    private List<RentalAgreement> rentalAgreements;// List of rental agreements associated with this tenant
    private List<Payment> paymentsTransactions;// List of payment transactions made by this tenant

    // Constructors
    public Tenant(String id, String fullName, Date birthDate, String contactInfo, String role, List<RentalAgreement> rentalAgreements, List<Payment> paymentsTransactions) {
        super(id, fullName, birthDate, contactInfo, role);
        this.rentalAgreements = rentalAgreements;
        this.paymentsTransactions = paymentsTransactions;
    }

    // Default Constructor
    public Tenant() {
        super();
    }

    // Getters and Setters
    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    public List<Payment> getPaymentsTransactions() {
        return paymentsTransactions;
    }

    public void setPaymentsTransactions(List<Payment> paymentsTransactions) {
        this.paymentsTransactions = paymentsTransactions;
    }

    //Override toString method for better display
    @Override
    public String toString() {
        return super.toString() +
                "\nRental Agreements: " + rentalAgreements +
                "\nPayments Transactions: " + paymentsTransactions;
    }
}