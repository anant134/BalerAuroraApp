package com.smartech.tour.gigantestourbooking.module;

import java.util.ArrayList;

public class userSelectedTripdData {
    Integer boatId;

    public Integer getTripid() {
        return tripid;
    }

    public void setTripid(Integer tripid) {
        this.tripid = tripid;
    }

    Integer tripid;
    String boatName;
    Integer captainId;
    String captainName;
    ArrayList<userinfo> userinfo;

    public Integer getBoatId() {
        return boatId;
    }

    public void setBoatId(Integer boatId) {
        this.boatId = boatId;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public Integer getCaptainId() {
        return captainId;
    }

    public void setCaptainId(Integer captainId) {
        this.captainId = captainId;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public ArrayList<com.smartech.tour.gigantestourbooking.module.userinfo> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(ArrayList<com.smartech.tour.gigantestourbooking.module.userinfo> userinfo) {
        this.userinfo = userinfo;
    }


}
