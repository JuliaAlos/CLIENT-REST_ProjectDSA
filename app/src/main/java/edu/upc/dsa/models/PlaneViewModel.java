package edu.upc.dsa.models;

import android.text.method.TimeKeyListener;

public class PlaneViewModel extends Plane{
    String description;
    Double qualification;
    Integer image;

    public PlaneViewModel(int image, String description, Double qualification, String model, int engineLifes, int fuel, int velX, int velY){
        super(model,velX,velY,9.81,engineLifes, fuel);
        this.image = image;
        this.description = description;
        this.qualification = qualification;

    }
    public int getImagePlane(){
        return this.image;
    }
    public String getDescription(){
        return this.description;
    }

    public double getQualification(){
        return this.qualification;
    }
}
