package com.project.tourismapp.DataClasses;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;

    public User(String email) {
        this.email = email;
        this.name = email.split("@")[0];
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}