package edu.upc.dsa.models;

public class InsigniaModel {
    private String name;
    private String type;

    public InsigniaModel(){}
    public InsigniaModel(String name, String type) {
        this();
        this.name = name;
        this.type = type;
    }

    /**********************************************************************
     **********************    Getters & Setters   ************************
     **********************************************************************/
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
