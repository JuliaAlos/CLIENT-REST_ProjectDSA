package edu.upc.dsa.models;

public class GameResults {
    private Integer collectedBitcoins;
    private Integer distance;
    private Integer timeOfFlight;

    public GameResults() {
    }

    public Integer getCollectedBitcoins() {
        return collectedBitcoins;
    }

    public void setCollectedBitcoins(Integer collectedBitcoins) {
        this.collectedBitcoins = collectedBitcoins;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getTimeOfFlight() {
        return timeOfFlight;
    }

    public void setTimeOfFlight(Integer timeOfFlight) {
        this.timeOfFlight = timeOfFlight;
    }
}
