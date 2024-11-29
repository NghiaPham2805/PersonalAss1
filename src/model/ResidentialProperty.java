package model;

public class ResidentialProperty extends Property {
    private int numBedrooms;// Number of bedrooms
    private boolean gardenAvailable;// Garden available
    private boolean petFriendly;// Pet friendly

    // Constructor with all attributes
    public ResidentialProperty(String propertyId, String address, double pricing, String status, int numBedrooms, boolean gardenAvailable, boolean petFriendly) {
        this.numBedrooms = numBedrooms;
        this.gardenAvailable = gardenAvailable;
        this.petFriendly = petFriendly;
    }

    // Default Constructor
    public ResidentialProperty() {
        super();
    }
    public int getNumBedrooms() {
        return numBedrooms;
    }
    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }
    public boolean isGardenAvailable() {
        return gardenAvailable;
    }
    public void setGardenAvailable(boolean gardenAvailable) {
        this.gardenAvailable = gardenAvailable;
    }
    public boolean isPetFriendly() {
        return petFriendly;
    }
    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    // ToString method for displaying property details
    @Override
    public String toString() {
        return super.toString() +
                "\nNumber of Bedrooms: " + numBedrooms +
                "\nGarden Available: " + gardenAvailable +
                "\nPet Friendly: " + petFriendly;
    }
}
