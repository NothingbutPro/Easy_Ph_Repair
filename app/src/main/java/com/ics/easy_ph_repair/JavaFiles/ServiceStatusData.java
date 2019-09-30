package com.ics.easy_ph_repair.JavaFiles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceStatusData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("jobdate")
    @Expose
    private String jobdate;
    @SerializedName("jobid")
    @Expose
    private String jobid;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("colour")
    @Expose
    private String colour;
    @SerializedName("imei_one")
    @Expose
    private String imeiOne;
    @SerializedName("imei_two")
    @Expose
    private String imeiTwo;
    @SerializedName("job_status")
    @Expose
    private String jobStatus;
    @SerializedName("invoice_status")
    @Expose
    private String invoiceStatus;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("warranty")
    @Expose
    private String warranty;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("accessories")
    @Expose
    private String accessories;
    @SerializedName("estimate")
    @Expose
    private String estimate;
    @SerializedName("complant")
    @Expose
    private String complant;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("quotation_amount")
    @Expose
    private String quotation_amount;


    @SerializedName("quotation_desc")
    @Expose
    private String quotation_desc;



    @SerializedName("check_status")
    @Expose
    private String check_status;

    public ServiceStatusData(String id, String userId, String jobdate, String jobid, String customerId, String mobile, String firstName, String lastName, String model, String colour, String imeiOne, String imeiTwo, String jobStatus, String invoiceStatus, String serviceType, String warranty, String remarks, String accessories, String estimate, String complant, String status,String quotation_amount,String quotation_desc
    ,String check_status) {
        this.id = id;
        this.userId = userId;
        this.jobdate = jobdate;
        this.jobid = jobid;
        this.customerId = customerId;
        this.mobile = mobile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.model = model;
        this.colour = colour;
        this.imeiOne = imeiOne;
        this.imeiTwo = imeiTwo;
        this.jobStatus = jobStatus;
        this.invoiceStatus = invoiceStatus;
        this.serviceType = serviceType;
        this.warranty = warranty;
        this.remarks = remarks;
        this.accessories = accessories;
        this.estimate = estimate;
        this.complant = complant;
        this.status = status;
        this.quotation_amount = quotation_amount;
        this.quotation_desc = quotation_desc;
        this.check_status = check_status;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public String getQuotation_amount() {
        return quotation_amount;
    }

    public void setQuotation_amount(String quotation_amount) {
        this.quotation_amount = quotation_amount;
    }

    public String getQuotation_desc() {
        return quotation_desc;
    }

    public void setQuotation_desc(String quotation_desc) {
        this.quotation_desc = quotation_desc;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobdate() {
        return jobdate;
    }

    public void setJobdate(String jobdate) {
        this.jobdate = jobdate;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getImeiOne() {
        return imeiOne;
    }

    public void setImeiOne(String imeiOne) {
        this.imeiOne = imeiOne;
    }

    public String getImeiTwo() {
        return imeiTwo;
    }

    public void setImeiTwo(String imeiTwo) {
        this.imeiTwo = imeiTwo;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getComplant() {
        return complant;
    }

    public void setComplant(String complant) {
        this.complant = complant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
