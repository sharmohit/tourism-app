package com.project.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.tourismapp.DataClasses.Session;

public class AttractionsActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        Intent intent = getIntent();
        if (intent != null) {
            this.session = (Session) intent.getSerializableExtra(getString(R.string.session_intent_extra));
            Toast.makeText(this, getString(R.string.welcome_msg) + " " +
                    this.session.getUser().getName(), Toast.LENGTH_SHORT).show();
        }
    }

    public void logout(View view) {
        this.session.logout(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void attractionDetail(View view) {
        Intent intent = new Intent(this, AttractionDetailActivity.class);
        intent.putExtra(getString(R.string.session_intent_extra), this.session);
        startActivity(intent);
        finish();
    }
}