package com.example.blogsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private DatabaseHelper dbHelp;
    private Button btnSubmit, btnToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSubmit = findViewById(R.id.submit);
        btnToSignup = findViewById(R.id.to_signup);
        dbHelp = new DatabaseHelper(this);
        SharedPreferences preferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        if (preferences.contains("username")) {
            boolean isLoggedIn = preferences.getString("username", "") != "" && preferences.getInt("u_id", 0) != 0;
            if (isLoggedIn) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }

        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (dbHelp.checkUser(username, password)) {
                    // User authenticated, save session or redirect to dashboard
//                    We also need the user id for future database interactions such as adding a blog:
                    int userId = dbHelp.getUserId(username, password);
                    // Example: Save username to shared preferences for session management
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.putInt("u_id", userId);
                    editor.apply();
                    dbHelp.closeDatabase();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
