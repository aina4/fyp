package com.example.fypg;

public class Upload {
    private String name;
    private String address;
    private String imgUrl;

    public Upload(){

    }

    public Upload(String name, String address, String imgUrl) {
        if(name.trim().equals("")){
            name = "No name";
            address = "No address";
        }
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

}
