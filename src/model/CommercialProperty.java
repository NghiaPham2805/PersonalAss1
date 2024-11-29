package model;

public class CommercialProperty extends Property {
    private String businessType;
    private int parkingSpaces;
    private double squareFootage;

    // Constructor with all attributes
    public CommercialProperty(String propertyId, String address, double pricing, String status,
                              String businessType, int parkingSpaces, double squareFootage) {
        this.businessType = businessType;
        this.parkingSpaces = parkingSpaces;
        this.squareFootage = squareFootage;
    }


   
    // Default Constructor
    public CommercialProperty() {
        super();
    }

    // Getters and Setters
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public double getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }

    // ToString method for displaying property details
    @Override
    public String toString() {
        return super.toString() + // Call to the superclass toString
                "\nBusiness Type: " + businessType +
                "\nParking Spaces: " + parkingSpaces +
                "\nSquare Footage: " + squareFootage;
    }
}
