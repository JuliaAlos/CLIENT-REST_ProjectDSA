package edu.upc.dsa.models;

public class Plane {
    private String model;
    private Integer velX;
    private Integer velY;
    private Double gravity;
    private Integer enginesLife; //The value of the integer indicates the number of health bars.
    private Integer fuelConsumptionRate;


    public Plane(){}
    public Plane(String model, Integer velX, Integer velY, Double gravity, Integer enginesLife, Integer fuelConsumptionRate){
        this.model = model;
        this.velX = velX;
        this.velY = velY;
        this.gravity = gravity;
        this.enginesLife = enginesLife;
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    /**********************************************************************
     **********************    Getters & Setters   ************************
     **********************************************************************/

    public void setModel(String model) {
        this.model = model;
    }

    public void setVelX(Integer velX) {
        this.velX = velX;
    }

    public void setVelY(Integer velY) {
        this.velY = velY;
    }

    public void setGravity(Double gravity) {
        this.gravity = gravity;
    }

    public void setEnginesLife(Integer enginesLife) {
        this.enginesLife = enginesLife;
    }

    public void setFuelConsumptionRate(Integer fuelConsumptionRate) {
        this.fuelConsumptionRate = fuelConsumptionRate;
    }

    public String getModel() {
        return model;
    }

    public Integer getVelX() {
        return velX;
    }

    public Integer getVelY() {
        return velY;
    }

    public Double getGravity() {
        return gravity;
    }

    public Integer getEnginesLife() {
        return enginesLife;
    }

    public Integer getFuelConsumptionRate() {
        return fuelConsumptionRate;
    }
}
