package com.example.blogsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    TextView tvTargetName;
    Button btnLogOut;
    FloatingActionButton btnAddBlog;
    DatabaseHelper dbHelp;
    BlogAdapter blogAdapter;
    RecyclerView recyclerView;
    List<Blog> blogs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SharedPreferences preferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        boolean isLoggedIn = !preferences.getString("username", "").isEmpty() && preferences.getInt("u_id", 0) != 0;
        if (!isLoggedIn) {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        dbHelp = new DatabaseHelper(this);
        blogs = new ArrayList<>();
        blogAdapter = new BlogAdapter(blogs, this);
        recyclerView = findViewById(R.id.blogsHolder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(blogAdapter);
        String username = " " + preferences.getString("username", "");
        tvTargetName = findViewById(R.id.targetName);
        tvTargetName.setText(username);
        btnAddBlog = findViewById(R.id.newBlog);
        btnLogOut = findViewById(R.id.logOut);
        loadBlogs();
//        Go to New Blog:
        // Set click listener for FloatingActionButton (New Blog)
        btnAddBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CreateBlogActivity.class));
            }
        });


//        Log Out:
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadBlogs() {
        // Fetch all blogs from database
        blogs.clear();
        blogs.addAll(dbHelp.fetchAllBlogs());
//        for (Blog blog : blogs) {
//            Log.d("DashboardActivity", "Blog ID: " + blog.getId());
//            Log.d("DashboardActivity", "Title: " + blog.getTitle());
//            Log.d("DashboardActivity", "Body: " + blog.getBody());
//            Log.d("DashboardActivity", "Created By User ID: " + blog.getByUser());
//            Log.d("DashboardActivity", "Created At: " + blog.getCreatedAt());
//            Log.d("DashboardActivity", "Updated At: " + blog.getUpdatedAt());
//            Log.d("DashboardActivity", "----------------------");
//        }
        blogAdapter.notifyDataSetChanged();
    }

    private void logoutUser() {
        // Remove user session
        SharedPreferences preferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("username");
        editor.remove("u_id");
        editor.apply();

        // Redirect to login screen
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}