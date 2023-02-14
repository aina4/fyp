package com.example.fypg;

public class Gasing {
    String mKey;
    String type;
    String description;
    String video;

    public Gasing() {
    }

    public Gasing(String type, String description, String video) {
        this.type = type;
        this.description = description;
        this.video = video;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
