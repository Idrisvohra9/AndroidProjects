package com.example.blogsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateBlogActivity extends AppCompatActivity {
    EditText etBlogTitle, etBlogBody;
    Button btnSubmit;
    private DatabaseHelper dbHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blog);
        dbHelp = new DatabaseHelper(this);
        btnSubmit = findViewById(R.id.create_blog);
        etBlogTitle = findViewById(R.id.title_blog);
        etBlogBody = findViewById(R.id.body_blog);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etBlogTitle.getText().toString();
                String body = etBlogBody.getText().toString();
                SharedPreferences preferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
                int userId = preferences.getInt("u_id", 0);
//                On Success:
                if (dbHelp.addBlog(title, body, userId) != -1) {
                    Toast.makeText(CreateBlogActivity.this, "Blog Created!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateBlogActivity.this, "An error occurred...", Toast.LENGTH_SHORT).show();
                }
                dbHelp.closeDatabase();
                Intent intent = new Intent(CreateBlogActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}