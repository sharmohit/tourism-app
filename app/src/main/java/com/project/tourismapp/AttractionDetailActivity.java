package com.project.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.project.tourismapp.Adaptors.AttractionDetailAdaptor;
import com.project.tourismapp.DataClasses.Attraction;
import com.project.tourismapp.DataClasses.Session;
import com.project.tourismapp.DataClasses.AttractionRating;
import com.project.tourismapp.Helpers.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AttractionDetailActivity extends AppCompatActivity {

    private Session session;
    private JSONObject jsonRatingObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);

        /* Get data from Intent */
        Intent intent = getIntent();
        this.session = (Session) intent.getSerializableExtra(getString(R.string.session_intent_extra));
        Attraction attraction = (Attraction) intent.getSerializableExtra(getString(R.string.attraction_intent_extra));
        this.session.getUser().getAttractionRating().setAttractionId(attraction.getId());

        /* Read user's existing rating */
        JSONHelper jsonHelper = new JSONHelper();
        try {
            this.jsonRatingObject = jsonHelper.readJSONObjectFromFile(
                    this, session.getUser().getName() + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonRatingObject != null) {
            this.session.getUser().getAttractionRating().setRating(
                    this.jsonObjectToUserRating(this.jsonRatingObject, attraction.getId()));
        } else {
            this.jsonRatingObject = jsonHelper.convertToJSONObject("{}");
        }

        /* Setup ListView */
        ListView lvAttractionDetails = findViewById(R.id.lvAttractionDetails);
        AttractionDetailAdaptor attractionDetailAdaptor = new AttractionDetailAdaptor(
                this, this.session, new Attraction[]{attraction});
        lvAttractionDetails.setAdapter(attractionDetailAdaptor);
    }

    @Override
    protected void onPause() {
        super.onPause();
        /* Saving user's rating */
        try {
            JSONHelper jsonHelper = new JSONHelper();
            addRatingToJSONObject(this.session.getUser().getAttractionRating());
            jsonHelper.writeJSONObjectToFile(
                    this, session.getUser().getName() + ".json", this.jsonRatingObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AttractionsActivity.class);
        intent.putExtra(getString(R.string.session_intent_extra), session);
        startActivity(intent);
        finish();
    }

    public void back(View view) {
        this.onBackPressed();
    }

    public void logout(View view) {
        this.session.logout(this);
        Intent intent = new Intent(this, com.project.tourismapp.LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private float jsonObjectToUserRating(JSONObject jsonObject, int attractionId) {
        if (jsonObject.has(getString(R.string.user_ratings_json_key))) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.user_ratings_json_key));
                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject ratingObject = jsonArray.getJSONObject(i);
                    if (attractionId == ratingObject.getInt(getString(R.string.attraction_id_json_key))) {
                        return ratingObject.getInt(getString(R.string.rating_json_key));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private void addRatingToJSONObject(AttractionRating attractionRating) throws JSONException {
        boolean ratingExist = false;
        JSONArray jsonArray = new JSONArray();
        if (this.jsonRatingObject.has(getString(R.string.user_ratings_json_key))) {
            jsonArray = this.jsonRatingObject.getJSONArray(getString(R.string.user_ratings_json_key));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ratingObject = jsonArray.getJSONObject(i);
                if (ratingObject.getInt(getString(R.string.attraction_id_json_key)) == attractionRating.getAttractionId()) {
                    ratingObject.put(getString(R.string.rating_json_key), attractionRating.getRating());
                    ratingExist = true;
                    break;
                }
            }
        }

        if(!ratingExist && attractionRating.getRating() > 0) {
            JSONObject userJSONObject = new JSONObject();
            userJSONObject.put(getString(R.string.attraction_id_json_key), attractionRating.getAttractionId());
            userJSONObject.put(getString(R.string.rating_json_key), attractionRating.getRating());
            jsonArray.put(userJSONObject);
            this.jsonRatingObject.put(getString(R.string.user_ratings_json_key), jsonArray);
        }
    }
}