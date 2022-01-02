package edu.upc.dsa.transferObjects;

public class PlanePlayerTO {
    private String playerName;
    private String planeModel;

    public PlanePlayerTO(){}
    public PlanePlayerTO(String playerName, String planeModel){
        this.playerName = playerName;
        this.planeModel = planeModel;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlaneModel() {
        return planeModel;
    }
}

