package lk.evenro.even.model;

import java.io.Serializable;

public class UserDetails implements Serializable {

    private String name,mobile,email,userID;



    public UserDetails() {
    }

    public UserDetails(String name, String mobile, String email, String userID) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
