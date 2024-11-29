package model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Person {
    private String id; // Unique identifier for the person
    private String fullName;// Full name of the person
    private Date birthDate;// Date of birth
    private String contactInfo;// Contact information
    private String role;// Role of the person (e.g., Owner, Host, Tenant)
    public Person() {
    }
    // Parameterized Constructor
    public Person(String id, String fullName, Date birthDate, String contactInfo, String role) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.contactInfo = contactInfo;
        this.role = role;
    }
    //Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    // Override toString method for better display
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = (birthDate != null) ? dateFormat.format(birthDate) : "N/A";
        return id + "|" + fullName + "|" + formattedDate + "|" + contactInfo + "|" + role;
    }

}
