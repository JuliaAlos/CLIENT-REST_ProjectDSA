package edu.upc.dsa.transferObjects;

public class RegisterUserTO {
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String image_url;

    public RegisterUserTO() {
    }

    public RegisterUserTO(String userName, String password, String fullName, String email, String image_url) {
        this();
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.image_url=image_url;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}