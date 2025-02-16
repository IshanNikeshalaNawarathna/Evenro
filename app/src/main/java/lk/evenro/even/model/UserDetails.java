package lk.evenro.even.model;

import java.io.Serializable;

public class UserDetails implements Serializable {

    private String email;

    private String name;

    public UserDetails() {
    }

    public UserDetails(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
