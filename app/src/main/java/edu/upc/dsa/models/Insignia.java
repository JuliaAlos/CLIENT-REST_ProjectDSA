package edu.upc.dsa.models;

public class Insignia extends ViewModel{

    private String insigniaID;
    private String name;


    public Insignia(int image, String description, Double qualification, String insigniaID, String name) {
        super(image, description, qualification);
        this.insigniaID = insigniaID;
        this.name = name;
    }

    public String getInsigniaID() {
        return insigniaID;
    }

    public void setInsigniaID(String insigniaID) {
        this.insigniaID = insigniaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
