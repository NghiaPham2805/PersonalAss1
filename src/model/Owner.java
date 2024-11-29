package model;

import java.util.ArrayList;
import java.util.List;

public class Owner extends Person {
    private List<Property> ownedProperties;     // List of properties owned by this owner
    private List<Host> managingHosts;           // List of hosts managing the properties
    private List<RentalAgreement> rentalAgreements; // List of rental agreements associated with this owner

    // Constructors
    public Owner() {
        super(); // Call the default constructor of Person
        this.ownedProperties = new ArrayList<>();
        this.managingHosts = new ArrayList<>();
        this.rentalAgreements = new ArrayList<>();
    }

    public Owner(String id, String fullName, java.util.Date birthDate, String contactInfo, String role) {
        super(id, fullName, birthDate, contactInfo, role); // Call the parent constructor
        this.ownedProperties = new ArrayList<>();
        this.managingHosts = new ArrayList<>();
        this.rentalAgreements = new ArrayList<>();
    }

    // Getters and Setters
    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(List<Property> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public List<Host> getManagingHosts() {
        return managingHosts;
    }

    public void setManagingHosts(List<Host> managingHosts) {
        this.managingHosts = managingHosts;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    // Methods

    /**
     * Adds a property to the list of owned properties.
     * @param property The property to be added.
     * @return True if the property is successfully added; false otherwise.
     */
    public boolean addProperty(Property property) {
        if (property != null && !ownedProperties.contains(property)) {
            ownedProperties.add(property);
            return true;
        }
        return false;
    }

    /**
     * Assigns a host to manage properties.
     * @param host The host to be assigned.
     * @return True if the host is successfully added; false otherwise.
     */
    public boolean assignHost(Host host) {
        if (host != null && !managingHosts.contains(host)) {
            managingHosts.add(host);
            return true;
        }
        return false;
    }

    /**
     * Associates a rental agreement with this owner.
     * @param rentalAgreement The rental agreement to be added.
     * @return True if the rental agreement is successfully added; false otherwise.
     */
    public boolean addRentalAgreement(RentalAgreement rentalAgreement) {
        if (rentalAgreement != null && !rentalAgreements.contains(rentalAgreement)) {
            rentalAgreements.add(rentalAgreement);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Owner {" +
                "ID='" + getId() + '\'' +
                ", Full Name='" + getFullName() + '\'' +
                ", Birth Date=" + getBirthDate() +
                ", Contact Info='" + getContactInfo() + '\'' +
                ", Owned Properties=" + ownedProperties.size() +
                ", Managing Hosts=" + managingHosts.size() +
                ", Rental Agreements=" + rentalAgreements.size() +
                '}';
    }
}