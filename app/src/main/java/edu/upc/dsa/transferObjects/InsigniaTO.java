package edu.upc.dsa.transferObjects;

public class InsigniaTO {
    private String userName;
    private String insigniaName;

    public InsigniaTO(String userName, String insigniaName) {
        this.userName = userName;
        this.insigniaName = insigniaName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setInsigniaName(String insigniaName) {
        this.insigniaName = insigniaName;
    }

    public String getUserName() {
        return userName;
    }

    public String getInsigniaName() {
        return insigniaName;
    }
}
