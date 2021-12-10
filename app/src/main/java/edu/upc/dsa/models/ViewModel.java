package edu.upc.dsa.models;

import android.text.method.TimeKeyListener;

public class ViewModel {
    String description;
    Double qualification;
    Integer image;

    public ViewModel(int image, String description, Double qualification){
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
