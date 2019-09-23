package com.ics.easy_ph_repair.JavaFiles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceStatus {

    @SerializedName("data")
    @Expose
    private ServiceStatusData data;
    @SerializedName("responce")
    @Expose
    private Boolean responce;

    public ServiceStatusData getData() {
        return data;
    }

    public void setData(ServiceStatusData data) {
        this.data = data;
    }

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }
}
