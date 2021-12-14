package edu.upc.dsa.transferObjects;

import java.util.HashMap;

import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.models.Player;

public class PlaneTO {
    private String model;
    private String playerName;
    private Integer fuel;
    private Integer enginesLife;
    private Integer velX;
    private Integer velY;
    private Double gravity;

    public PlaneTO() {
    }
    public PlaneTO(String model, String playerName, Integer fuel, Integer enginesLife, Integer velX, Integer velY, Double gravity) {
        this.model = model;
        this.playerName = playerName;
        this.fuel = fuel;
        this.enginesLife = enginesLife;
        this.velX = velX;
        this.velY = velY;
        this.gravity = gravity;
    }

    public String getModel() {
        return model;
    }

    public Integer getEnginesLife() {
        return enginesLife;
    }

    public Integer getVelY() {
        return velY;
    }

    public Integer getVelX() {
        return velX;
    }

    public Double getGravity() {
        return gravity;
    }

    public Integer getFuel() {
        return fuel;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setFuel(Integer fuel) {
        this.fuel = fuel;
    }

    public void setEnginesLife(Integer enginesLife) {
        this.enginesLife = enginesLife;
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
}
