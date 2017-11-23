package com.example.minhd.drinky;

/**
 * Created by minhd on 17/11/21.
 */

public class Item {

    private String name, loc ;
    private int img ;

    public Item(String name, String loc, int img) {
        this.name = name;
        this.loc = loc;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

