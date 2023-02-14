package com.example.fypg;

import com.google.firebase.database.Exclude;

public class Store {
    private String name;
    private String address;
    private String imgUrl;
    private String mKey;

    public Store(){

    }

    public Store(String name, String address, String imgUrl) {

        this.name = name;
        this.address = address;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getKey(){
        return mKey;
    }

    public void setKey(String key){ mKey = key;}

}
