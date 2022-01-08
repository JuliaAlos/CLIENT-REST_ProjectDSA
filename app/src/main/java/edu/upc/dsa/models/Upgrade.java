package edu.upc.dsa.models;

public class Upgrade {
    private String id;
    private String modificationCode;
    private String playerName;
    private String planeModelModel;

    public Upgrade(){}

    public Upgrade(String modification, String playerName, String planeModelModel) {
        this.modificationCode = modification;
        this.playerName = playerName;
        this.planeModelModel = planeModelModel;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setModificationCode(String modificationCode) {
        this.modificationCode = modificationCode;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlaneModelModel(String planeModelModel) {
        this.planeModelModel = planeModelModel;
    }

    public String getId() {
        return id;
    }

    public String getModificationCode() {
        return modificationCode;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlaneModelModel() {
        return planeModelModel;
    }

}
