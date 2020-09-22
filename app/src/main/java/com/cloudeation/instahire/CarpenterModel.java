package com.cloudeation.instahire;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CarpenterModel {

    private String first;




    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    private String last;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String mobile;
    private String service;

    public String getTaddress() {
        return Taddress;
    }

    public void setTaddress(String taddress) {
        Taddress = taddress;
    }

    private String Taddress;


    private CarpenterModel() {
        //Empty Constructor

    }

    private CarpenterModel(String first, String mobile, String service, String address, String last) {

        this.first = first;
        this.mobile = mobile;
        this.service = service;
        this.Taddress = address;
        this.last = last;


    }


    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }


    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


}

