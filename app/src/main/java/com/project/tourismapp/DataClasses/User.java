package com.project.tourismapp.DataClasses;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private AttractionRating attractionRating;

    public User(String email) {
        this.email = email;
        this.name = email.split("@")[0];
        this.attractionRating = new AttractionRating();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public AttractionRating getAttractionRating() {
        return attractionRating;
    }
}