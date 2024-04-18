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

public class SignupActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private DatabaseHelper dbHelp;
    private Button btnSubmit, btnToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etUsername = findViewById(R.id.etUname);
        etPassword = findViewById(R.id.etPass);
        btnSubmit = findViewById(R.id.signup);
        btnToSignup = findViewById(R.id.to_login);
        dbHelp = new DatabaseHelper(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (dbHelp.checkUser(username, password)) {
                    Toast.makeText(SignupActivity.this, "The user already exists login to continue..", Toast.LENGTH_SHORT).show();
                } else {
                    long result = dbHelp.addUser(username, password);
                    if (result == -1){
                        Toast.makeText(SignupActivity.this, "Error during sign up.", Toast.LENGTH_SHORT).show();
                    }
//                    We also need the user id for future database interactions such as adding a blog:
                    int userId = dbHelp.getUserId(username, password);
                    Toast.makeText(SignupActivity.this, "Sign up successful.", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.putInt("u_id", userId);

                    editor.apply();

                    Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}