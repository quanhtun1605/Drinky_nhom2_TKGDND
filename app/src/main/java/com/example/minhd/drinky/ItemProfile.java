package com.example.minhd.drinky;

/**
 * Created by minhd on 17/11/23.
 */

public class ItemProfile {

    private int img ;
    private String review ;


    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public ItemProfile(int img, String review) {

        this.img = img;
        this.review = review;
    }
}
