package com.example.lappli;

public class Coupon {
    private String title;

    // Image name (Without extension)
    private String imageName;
    private int reduction;

    public Coupon(String title, String imageName, int reduction) {
        this.title = title;
        this.imageName = imageName;
        this.reduction = reduction;
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName() {
        this.imageName = imageName;
    }

    @Override
    public String toString()  {
        return this.title+" (Réduction: "+ this.reduction +"%)";
    }
}
