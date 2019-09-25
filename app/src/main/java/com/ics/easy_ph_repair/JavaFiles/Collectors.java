package com.ics.easy_ph_repair.JavaFiles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collectors {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Service_Report")
    @Expose
    private String serviceReport;
    @SerializedName("Collected_by")
    @Expose
    private String collectedBy;
    @SerializedName("SC_Received")
    @Expose
    private String sCReceived;
    @SerializedName("Estimated_Approved")
    @Expose
    private String estimatedApproved;
    @SerializedName("Dead_Chance_Approval")
    @Expose
    private String deadChanceApproval;
    @SerializedName("Serviced_By")
    @Expose
    private String servicedBy;
    @SerializedName("SC_Delivered")
    @Expose
    private String sCDelivered;
    @SerializedName("Boy_Collected_on")
    @Expose
    private String boyCollectedOn;
    @SerializedName("SC_Received_on")
    @Expose
    private String sCReceivedOn;
    @SerializedName("Estimate_App_on")
    @Expose
    private String estimateAppOn;
    @SerializedName("Dead_Chance_App_On")
    @Expose
    private String deadChanceAppOn;
    @SerializedName("Service_Deliverd_on")
    @Expose
    private String serviceDeliverdOn;
    @SerializedName("Boy_Deliverd_on")
    @Expose
    private String boyDeliverdOn;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private String status;

    public Collectors(String id, String serviceReport, String collectedBy, String sCReceived, String estimatedApproved, String deadChanceApproval, String servicedBy, String sCDelivered, String boyCollectedOn, String sCReceivedOn, String estimateAppOn, String deadChanceAppOn, String serviceDeliverdOn, String boyDeliverdOn, String userId, String status) {
        this.id = id;
        this.serviceReport = serviceReport;
        this.collectedBy = collectedBy;
        this.sCReceived = sCReceived;
        this.estimatedApproved = estimatedApproved;
        this.deadChanceApproval = deadChanceApproval;
        this.servicedBy = servicedBy;
        this.sCDelivered = sCDelivered;
        this.boyCollectedOn = boyCollectedOn;
        this.sCReceivedOn = sCReceivedOn;
        this.estimateAppOn = estimateAppOn;
        this.deadChanceAppOn = deadChanceAppOn;
        this.serviceDeliverdOn = serviceDeliverdOn;
        this.boyDeliverdOn = boyDeliverdOn;
        this.userId = userId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceReport() {
        return serviceReport;
    }

    public void setServiceReport(String serviceReport) {
        this.serviceReport = serviceReport;
    }

    public String getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(String collectedBy) {
        this.collectedBy = collectedBy;
    }

    public String getSCReceived() {
        return sCReceived;
    }

    public void setSCReceived(String sCReceived) {
        this.sCReceived = sCReceived;
    }

    public String getEstimatedApproved() {
        return estimatedApproved;
    }

    public void setEstimatedApproved(String estimatedApproved) {
        this.estimatedApproved = estimatedApproved;
    }

    public String getDeadChanceApproval() {
        return deadChanceApproval;
    }

    public void setDeadChanceApproval(String deadChanceApproval) {
        this.deadChanceApproval = deadChanceApproval;
    }

    public String getServicedBy() {
        return servicedBy;
    }

    public void setServicedBy(String servicedBy) {
        this.servicedBy = servicedBy;
    }

    public String getSCDelivered() {
        return sCDelivered;
    }

    public void setSCDelivered(String sCDelivered) {
        this.sCDelivered = sCDelivered;
    }

    public String getBoyCollectedOn() {
        return boyCollectedOn;
    }

    public void setBoyCollectedOn(String boyCollectedOn) {
        this.boyCollectedOn = boyCollectedOn;
    }

    public String getSCReceivedOn() {
        return sCReceivedOn;
    }

    public void setSCReceivedOn(String sCReceivedOn) {
        this.sCReceivedOn = sCReceivedOn;
    }

    public String getEstimateAppOn() {
        return estimateAppOn;
    }

    public void setEstimateAppOn(String estimateAppOn) {
        this.estimateAppOn = estimateAppOn;
    }

    public String getDeadChanceAppOn() {
        return deadChanceAppOn;
    }

    public void setDeadChanceAppOn(String deadChanceAppOn) {
        this.deadChanceAppOn = deadChanceAppOn;
    }

    public String getServiceDeliverdOn() {
        return serviceDeliverdOn;
    }

    public void setServiceDeliverdOn(String serviceDeliverdOn) {
        this.serviceDeliverdOn = serviceDeliverdOn;
    }

    public String getBoyDeliverdOn() {
        return boyDeliverdOn;
    }

    public void setBoyDeliverdOn(String boyDeliverdOn) {
        this.boyDeliverdOn = boyDeliverdOn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
