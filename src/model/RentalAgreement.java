package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RentalAgreement {
    private String agreementId;          // Rental Agreement ID
    private String propertyId;           // Property ID
    private String mainTenantId;         // Main Tenant ID
    private List<String> subTenantIds;   // Sub Tenant IDs (comma-separated)
    private String ownerId;              // Owner ID
    private String hostId;               // Host ID
    private String period;               // Period
    private Date contractDate;           // Contract Date
    private double rentingFee;           // Renting Fee
    private String status;               // Status

    // Constructors
    public RentalAgreement(String agreementId, String propertyId, String mainTenantId, List<String> subTenantIds, String ownerId, String hostId, String period, Date contractDate, double rentingFee, String status) {
        this.agreementId = agreementId;
        this.propertyId = propertyId;
        this.mainTenantId = mainTenantId;
        this.subTenantIds = subTenantIds;
        this.ownerId = ownerId;
        this.hostId = hostId;
        this.period = period;
        this.contractDate = contractDate;
        this.rentingFee = rentingFee;
        this.status = status;
    }

    // Getters and Setters
    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getMainTenantId() {
        return mainTenantId;
    }

    public void setMainTenantId(String mainTenantId) {
        this.mainTenantId = mainTenantId;
    }

    public List<String> getSubTenantIds() {
        return subTenantIds;
    }

    public void setSubTenantIds(List<String> subTenantIds) {
        this.subTenantIds = subTenantIds;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public double getRentingFee() {
        return rentingFee;
    }

    public void setRentingFee(double rentingFee) {
        this.rentingFee = rentingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ToString method for displaying rental agreement details
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = (contractDate != null) ? dateFormat.format(contractDate) : "N/A";
        return agreementId + "|" + propertyId + "|" + mainTenantId + "|" + String.join(",", subTenantIds) + "|" + ownerId + "|" + hostId + "|" + period + "|" + formattedDate + "|" + rentingFee + "|" + status;
    }
}