package com.project.tourismapp.DataClasses;

import java.io.Serializable;

public class AttractionRating implements Serializable {
    private int attractionId;
    private float rating;

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}