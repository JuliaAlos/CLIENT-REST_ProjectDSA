package edu.upc.dsa.models;

public class Player extends ViewModel {

    private String playerName;
    private Integer maxDistance;
    private String rol;
    private Float timeOfFlight;
    private Integer bitcoins;

    public Player(int image, String description, Double qualification, String playerName, Integer maxDistance, String rol, Float timeOfFlight, Integer bitcoins){
      super(image, description, qualification);
        this.playerName=playerName;
        this.maxDistance=maxDistance;
        this.rol = rol;
        this.timeOfFlight=timeOfFlight;
        this.bitcoins=bitcoins;
    }

    /**********************************************************************
     **********************    Getters & Setters   ************************
     **********************************************************************/
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Float getTimeOfFlight() {
        return timeOfFlight;
    }

    public void setTimeOfFlight(Float timeOfFlight) {
        this.timeOfFlight = timeOfFlight;
    }

    public Integer getBitcoins() {
        return bitcoins;
    }

    public void setBitcoins(Integer bitcoins) {
        this.bitcoins = bitcoins;
    }
}
