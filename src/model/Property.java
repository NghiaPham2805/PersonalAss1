package model;

import java.util.ArrayList;
import java.util.List;

public class Property {
    private String propertyId;  // Unique identifier for the property
    private String type;        // Type of the property (Residential, Commercial, etc.)
    private String address;     // Address of the property
    private double pricing;     // Rental price of the property
    private String status;      // Status: Available, Rented, Under Maintenance
    private String ownerId;     // Owner ID of the property
    private List<String> hosts; // List of host IDs for the property

    // Constructors
    public Property(String propertyId, String type, String address, double pricing, String status, String ownerId) {
        this.propertyId = propertyId;
        this.type = type;
        this.address = address;
        this.pricing = pricing;
        this.status = status;
        this.ownerId = ownerId;
        this.hosts = new ArrayList<>();
    }

    public Property() {
        this.hosts = new ArrayList<>();
    }

    // Getters and Setters
    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPricing() {
        return pricing;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void addHost(String hostId) {
        this.hosts.add(hostId);
    }
    // Override the toString method to display Property details
    @Override
    public String toString() {
        return propertyId + "|" + type + "|" + address + "|" + pricing + "|" + status + "|" + ownerId + "|" + String.join(",", hosts);
    }
}
