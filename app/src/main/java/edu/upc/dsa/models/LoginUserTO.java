package edu.upc.dsa.models;

public class LoginUserTO {
    private String userName;
    private String password;

    public LoginUserTO() {}

    public LoginUserTO(String userName, String password) {
        this();
        this.userName=userName;
        this.password=password;
    }


    /**********************************************************************
     **********************    Getters & Setters   ************************
     **********************************************************************/
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
