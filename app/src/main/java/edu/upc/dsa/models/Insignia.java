package edu.upc.dsa.models;

public class Insignia {
    private String name;
    private String date;
    private String type;

    public Insignia(){}
    public Insignia(String name, String date, String type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }

    /**********************************************************************
     **********************    Getters & Setters   ************************
     **********************************************************************/
    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
}
