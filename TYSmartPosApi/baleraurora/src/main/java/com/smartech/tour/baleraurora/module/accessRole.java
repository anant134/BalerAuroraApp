package com.smartech.tour.baleraurora.module;

import com.google.gson.annotations.SerializedName;

public class accessRole {
    @SerializedName("ID")
    String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    @SerializedName("desc")
    String desc;


}
