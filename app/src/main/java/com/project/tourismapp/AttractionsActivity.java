package com.project.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.tourismapp.Adaptors.AttractionAdaptor;
import com.project.tourismapp.DataClasses.Attraction;
import com.project.tourismapp.DataClasses.Session;
import com.project.tourismapp.Helpers.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AttractionsActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        Intent intent = getIntent();
        if (intent != null) {
            this.session = (Session) intent.getSerializableExtra(getString(R.string.session_intent_extra));
            if (intent.getBooleanExtra(getString(R.string.welcome_msg), false)) {
                Toast.makeText(this, getString(R.string.welcome_msg) + " " +
                        this.session.getUser().getName(), Toast.LENGTH_SHORT).show();
            }
        }

        ListView lvAttractions = findViewById(R.id.lvAttractions);
        Attraction attraction[] = createAttractions();
        AttractionAdaptor attractionAdaptor = new AttractionAdaptor(this, attraction);
        lvAttractions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startAttractionDetailsActivity(attraction[position]);
            }
        });
        lvAttractions.setAdapter(attractionAdaptor);
    }

    public void logout(View view) {
        this.session.logout(this);
        Intent intent = new Intent(this, com.project.tourismapp.LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startAttractionDetailsActivity(Attraction attraction) {
        Intent intent = new Intent(this, AttractionDetailActivity.class);
        intent.putExtra(getString(R.string.session_intent_extra), this.session);
        intent.putExtra(getString(R.string.attraction_intent_extra), attraction);
        startActivity(intent);
        finish();
    }

    private Attraction[] createAttractions() {
        JSONHelper jsonHelper = new JSONHelper();
        try {
            JSONArray jsonArray = jsonHelper.convertToJSONObject(
                    jsonHelper.assetsFileReader(this, "attractions.json")).
                    getJSONArray(getString(R.string.attractions_json_key));
            Attraction attractions[] = new Attraction[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject attractionJsonObject = jsonArray.getJSONObject(i);
                Attraction attraction = new Attraction();
                attraction.setId(attractionJsonObject.getInt(getString(R.string.attraction_id_key)));
                attraction.setName(attractionJsonObject.getString(getString(R.string.attraction_name_key)));
                attraction.setDescription(attractionJsonObject.getString(getString(R.string.attraction_description_key)));
                attraction.setAddress(attractionJsonObject.getString(getString(R.string.attraction_address_key)));
                attraction.setPrice(attractionJsonObject.getDouble(getString(R.string.attraction_price_key)));
                attraction.setPhone(attractionJsonObject.getString(getString(R.string.attraction_phone_key)));
                attraction.setWebsite(attractionJsonObject.getString(getString(R.string.attraction_website_key)));
                attractions[i] = attraction;
            }

            return attractions;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}