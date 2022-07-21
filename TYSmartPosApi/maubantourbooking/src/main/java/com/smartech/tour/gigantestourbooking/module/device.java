package com.smartech.tour.gigantestourbooking.module;

public class device {


    String deviceID;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getConnectedToDB() {
        return connectedToDB;
    }

    public void setConnectedToDB(String connectedToDB) {
        this.connectedToDB = connectedToDB;
    }

    String port;
    String connectedToDB;

    public boolean isIssync() {
        return issync;
    }

    public void setIssync(boolean issync) {
        this.issync = issync;
    }

    boolean issync;

}
