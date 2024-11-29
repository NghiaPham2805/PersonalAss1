package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Host extends Person {
    private List<Property> managedProperties;  // Properties managed by the host
    private List<Owner> cooperatingOwners;    // Owners the host is working with
    private List<RentalAgreement> rentalAgreements; // Agreements managed by the host

    // Default Constructor
    public Host() {
        super(); // Call the default constructor of Person
        this.managedProperties = new ArrayList<>();
        this.cooperatingOwners = new ArrayList<>();
        this.rentalAgreements = new ArrayList<>();
    }

    // Parameterized Constructor
    public Host(String id, String fullName, Date birthDate, String contactInfo, String role) {
        super(id, fullName, birthDate, contactInfo, role); // Call the parent constructor
        this.managedProperties = new ArrayList<>();
        this.cooperatingOwners = new ArrayList<>();
        this.rentalAgreements = new ArrayList<>();
    }

    // Getters and Setters
    public List<Property> getManagedProperties() {
        return managedProperties;
    }

    public void setManagedProperties(List<Property> managedProperties) {
        this.managedProperties = managedProperties;
    }

    public List<Owner> getCooperatingOwners() {
        return cooperatingOwners;
    }

    public void setCooperatingOwners(List<Owner> cooperatingOwners) {
        this.cooperatingOwners = cooperatingOwners;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    // Methods to manage entities
    public boolean manageProperty(Property property) {
        if (!managedProperties.contains(property)) {
            managedProperties.add(property);
            return true;
        }
        return false; // Property is already being managed
    }

    public boolean addCooperatingOwner(Owner owner) {
        if (!cooperatingOwners.contains(owner)) {
            cooperatingOwners.add(owner);
            return true;
        }
        return false; // Owner is already in the list
    }

    public boolean addRentalAgreement(RentalAgreement rentalAgreement) {
        if (!rentalAgreements.contains(rentalAgreement)) {
            rentalAgreements.add(rentalAgreement);
            return true;
        }
        return false; // Rental agreement is already in the list
    }

    // Override the toString method to display Host details
    @Override
    public String toString() {
        return "Host {" +
                "ID='" + getId() + '\'' +
                ", Full Name='" + getFullName() + '\'' +
                ", Birth Date=" + getBirthDate() +
                ", Contact Info='" + getContactInfo() + '\'' +
                ", Managed Properties=" + managedProperties.size() +
                ", Cooperating Owners=" + cooperatingOwners.size() +
                ", Rental Agreements=" + rentalAgreements.size() +
                '}';
    }
}