package com.project.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.project.tourismapp.Adaptors.AttractionDetailAdaptor;
import com.project.tourismapp.DataClasses.Attraction;
import com.project.tourismapp.DataClasses.Session;

public class AttractionDetailActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);

        Intent intent = getIntent();
        this.session = (Session) intent.getSerializableExtra(getString(R.string.session_intent_extra));
        Attraction attraction = (Attraction) intent.getSerializableExtra(getString(R.string.session_intent_extra));

        ListView lvAttractionDetails = findViewById(R.id.lvAttractionDetails);
        AttractionDetailAdaptor attractionDetailAdaptor = new AttractionDetailAdaptor(this, new Attraction[]{attraction});
        lvAttractionDetails.setAdapter(attractionDetailAdaptor);
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
}