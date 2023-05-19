package com.smartech.tour.baleraurora.module;

public class userinfo {


    //id,username,email,rfid
    Integer id;
    String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    String email;
    Integer roleid;
    String rolename;
    Boolean isactive;

    public Boolean getIsmapped() {
        return ismapped;
    }

    public void setIsmapped(Boolean ismapped) {
        this.ismapped = ismapped;
    }

    Boolean ismapped;
}
