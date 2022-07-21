package com.smartech.tour.gigantestourbooking.module;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class user {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<accessRole> getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(ArrayList<accessRole> accessRole) {
        this.accessRole = accessRole;
    }

    String userName;
    ArrayList<accessRole> accessRole;

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    Date lastlogin;

    public String getRoleid() {
        return Roleid;
    }

    public void setRoleid(String roleid) {
        Roleid = roleid;
    }

    String Roleid;

    public user(){

    }

}
