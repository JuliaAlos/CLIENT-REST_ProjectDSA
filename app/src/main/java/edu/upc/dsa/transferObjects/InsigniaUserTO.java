package edu.upc.dsa.transferObjects;

public class InsigniaUserTO {
    private String userName;
    private String insigniaName;

    InsigniaUserTO(){}

    public InsigniaUserTO(String userName, String insigniaName) {
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
