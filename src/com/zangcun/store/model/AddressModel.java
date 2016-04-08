package com.zangcun.store.model;


public class AddressModel {
    private String address;
    private String mobile;
    private String region_id;//区域id
    private String consignee;//收件人姓名

    public AddressModel() {
    }

    public AddressModel(String address, String mobile, String region_id, String consignee) {
        this.address = address;
        this.mobile = mobile;
        this.region_id = region_id;
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
}
