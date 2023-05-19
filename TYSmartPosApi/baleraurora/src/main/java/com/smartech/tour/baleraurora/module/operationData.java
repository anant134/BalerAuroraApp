package com.smartech.tour.baleraurora.module;

import com.google.gson.annotations.SerializedName;

public class operationData {


    public String getSenddata() {
        return senddata;
    }

    public void setSenddata(String senddata) {
        this.senddata = senddata;
    }

    public String getReceivedata() {
        return receivedata;
    }

    public void setReceivedata(String receivedata) {
        this.receivedata = receivedata;
    }
    @SerializedName("senddata")
    String senddata;
    @SerializedName("receivedata")
    String receivedata;

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    @SerializedName("fromname")
    String fromname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("id")
    Integer id;
    @SerializedName("name")
    String  name;

    public operationData() {
        senddata="";
        receivedata="";
        fromname="";
    }
}
