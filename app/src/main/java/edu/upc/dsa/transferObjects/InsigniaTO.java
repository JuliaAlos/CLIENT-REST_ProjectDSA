package edu.upc.dsa.transferObjects;

public class InsigniaTO {
    private String date;
    private String playerName;
    private String name;
    private String type;

    public InsigniaTO() {
    }

    public InsigniaTO(String date, String playerName, String name, String type) {
        this.date = date;
        this.playerName = playerName;
        this.name = name;
        this.type = type;
    }

    public String getDate() { return date;}

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}