package edu.upc.dsa.transferObjects;

public class PlaneUserTO {
    private String userName;
    private String planeModel;

    public PlaneUserTO(){}
    public PlaneUserTO(String userName, String planeModel){
        this.userName = userName;
        this.planeModel = planeModel;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

    public String getUserName() {
        return userName;
    }

    public String getPlaneModel() {
        return planeModel;
    }
}
