package com.project.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tourismapp.DataClasses.Session;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.session = new Session();
        this.session.loadUsersJSONFile(this, getString(R.string.users_json_filename));
        if (this.session.restoreSession(this)) {
            this.startAttractionsActivity(this.session);
        }
    }

    public void login(View view) {
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        CheckBox cbRememberMe = findViewById(R.id.cbRememberMe);

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (validateInput(email, password)) {
            if (this.session.login(this, email, password, cbRememberMe.isChecked())) {
                this.startAttractionsActivity(this.session);
            } else {
                Toast.makeText(this, R.string.invalid_user_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInput(String email, String password) {
        boolean isValid = false;
        String errMsg = "";

        if (email.isEmpty()) {
            errMsg = getString(R.string.email_empty_error);
        } else if (!isValidEmailId(email)) {
            errMsg = getString(R.string.email_invalid_error);
        } else if (password.isEmpty()) {
            errMsg = getString(R.string.password_empty_error);
        } else {
            isValid = true;
        }

        if (!isValid) {
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private void startAttractionsActivity(Session session) {
        Intent intent = new Intent(this, AttractionsActivity.class);
        intent.putExtra(getString(R.string.session_intent_extra), session);
        intent.putExtra(getString(R.string.welcome_msg), true);
        startActivity(intent);
        finish();
    }
}