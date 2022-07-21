package com.smartech.tour.gigantestourbooking.module;

import java.util.ArrayList;

public class tripData {
    Integer id;
    String boatName;
    String boatCaptain;
    String dateTime;
    String formPort;
    String endDateTime;
    Boolean istripended;
    ArrayList<guestInfo> guest;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getBoatCaptain() {
        return boatCaptain;
    }

    public void setBoatCaptain(String boatCaptain) {
        this.boatCaptain = boatCaptain;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormPort() {
        return formPort;
    }

    public void setFormPort(String formPort) {
        this.formPort = formPort;
    }

    public ArrayList<guestInfo> getGuest() {
        return guest;
    }

    public void setGuest(ArrayList<guestInfo> guest) {
        this.guest = guest;
    }


    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public String getStartorendtrip() {
        return startorendtrip;
    }

    public void setStartorendtrip(String startorendtrip) {
        this.startorendtrip = startorendtrip;
    }

    String startorendtrip;
    String toPort;

    public Boolean getIstripended() {
        return istripended;
    }

    public void setIstripended(Boolean istripended) {
        this.istripended = istripended;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }


}
