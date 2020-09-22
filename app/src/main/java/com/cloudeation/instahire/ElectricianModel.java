package com.cloudeation.instahire;

public class ElectricianModel {
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



    private ElectricianModel (){
        //Empty Constructor

    }
    private ElectricianModel(String first, String mobile, String service, String address,String last ){
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
