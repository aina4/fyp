package com.example.fypg;

public class Store {
    private String storeName, storeAddress, storeId;

    public Store() {
    }

    public Store(String storeId, String storeName, String storeAddress) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
      //  this.storeImage = storeImage;
        this.storeId = storeId;
    }

    public String getStoreName() {return storeName;}

    public String getStoreAddress() {
        return storeAddress;
    }

  //  public String getStoreImage() {return storeImage;}

    public String getStoreId() {
        return storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

  //  public void setStoreImage(String storeImage) {this.storeImage = storeImage;}

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
