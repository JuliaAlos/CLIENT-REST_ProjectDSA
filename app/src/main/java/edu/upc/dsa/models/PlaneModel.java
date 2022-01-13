package edu.upc.dsa.models;

public class PlaneModel {
    private String model;
    private Integer fuel, minFuel;
    private Integer enginesLife, maxEnginesLife;
    private Integer velX, maxSpeed;
    private Integer velY, maxManoeuvrability;
    private Integer gravity, minWeight;

    public PlaneModel() {
    }

    public PlaneModel(String model, Integer fuel, Integer minFuel, Integer enginesLife, Integer maxEnginesLife,Integer velX, Integer maxSpeed, Integer maxManoeuvrability, Integer velY, Integer gravity, Integer minWeight) {
        this();
        this.model = model;
        this.fuel = fuel;
        this.minFuel = minFuel;
        this.enginesLife = enginesLife;
        this.maxEnginesLife = maxEnginesLife;
        this.velX = velX;
        this.maxSpeed = maxSpeed;
        this.velY = velY;
        this.maxManoeuvrability = maxManoeuvrability;
        this.gravity = gravity;
        this.minWeight = minWeight;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getFuel() {
        return fuel;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public Integer getMinFuel() {
        return minFuel;
    }

    public void setMinFuel(Integer minFuel) {
        this.minFuel = minFuel;
    }


    public Integer getEnginesLife() {
        return enginesLife;
    }

    public void setEnginesLife(Integer enginesLife) {
        this.enginesLife = enginesLife;
    }

    public void setMaxEnginesLife(Integer maxEnginesLife) {
        this.maxEnginesLife = maxEnginesLife;
    }

    public Integer getMaxEnginesLife() {
        return maxEnginesLife;
    }

    public Integer getVelX() {
        return velX;
    }

    public void setVelX(Integer velX) {
        this.velX = velX;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public Integer getVelY() {
        return velY;
    }

    public void setVelY(Integer velY) {
        this.velY = velY;
    }

    public void setMaxManoeuvrability(Integer maxManoeuvrability) {
        this.maxManoeuvrability = maxManoeuvrability;
    }

    public Integer getMaxManoeuvrability() {
        return maxManoeuvrability;
    }

    public Integer getGravity() {
        return gravity;
    }

    public void setGravity(Integer gravity) {
        this.gravity = gravity;
    }

    public void setMinWeight(Integer minWeight) {
        this.minWeight = minWeight;
    }

    public Integer getMinWeight() {
        return minWeight;
    }
}
