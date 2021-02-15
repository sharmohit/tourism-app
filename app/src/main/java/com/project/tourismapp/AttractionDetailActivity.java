package com.project.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.tourismapp.DataClasses.Session;

public class AttractionDetailActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);

        Intent intent = getIntent();
        if (intent != null) {
            this.session = (Session) intent.getSerializableExtra(getString(R.string.session_intent_extra));
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}