package edu.upc.dsa.models;

public class PlaneModel {
    private String model;
    private Integer fuel;
    private Integer enginesLife;
    private Integer velX;
    private Integer velY;
    private Integer gravity;

    public PlaneModel() {
    }

    public PlaneModel(String model, Integer fuel, Integer enginesLife, Integer velX, Integer velY, Integer gravity) {
        this();
        this.model = model;
        this.fuel = fuel;
        this.enginesLife = enginesLife;
        this.velX = velX;
        this.velY = velY;
        this.gravity = gravity;
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

    public Integer getEnginesLife() {
        return enginesLife;
    }

    public void setEnginesLife(Integer enginesLife) {
        this.enginesLife = enginesLife;
    }

    public Integer getVelX() {
        return velX;
    }

    public void setVelX(Integer velX) {
        this.velX = velX;
    }

    public Integer getVelY() {
        return velY;
    }

    public void setVelY(Integer velY) {
        this.velY = velY;
    }

    public Integer getGravity() {
        return gravity;
    }

    public void setGravity(Integer gravity) {
        this.gravity = gravity;
    }
}
