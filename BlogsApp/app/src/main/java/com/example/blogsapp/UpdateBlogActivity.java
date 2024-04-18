package com.example.blogsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateBlogActivity extends AppCompatActivity {
    private EditText etBlogTitle, etBlogBody;
    private Button btnSubmit;
    private int blogId;
    private DatabaseHelper dbHelp;
    private TextView tvAddId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        This came as it is:
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_blog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        Assigning to their widgets
        dbHelp = new DatabaseHelper(this);
        btnSubmit = findViewById(R.id.update_blog);
        etBlogTitle = findViewById(R.id.title_blog_update);
        etBlogBody = findViewById(R.id.body_blog_update);
        tvAddId = findViewById(R.id.addBlogId);
//        Get the id from the intent:
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("blogId")) {
            blogId = intent.getIntExtra("blogId", 0);
            tvAddId.append(blogId + "");
//          Get the blog with the id and set the text to the widgets:
            Blog blog = dbHelp.getBlogById(blogId);
            etBlogTitle.setText(blog.getTitle());
            etBlogBody.setText(blog.getBody());
//          Set the event listener to the submit button:
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = etBlogTitle.getText().toString();
                    String body = etBlogBody.getText().toString();
                    if (dbHelp.updateBlog(blogId, title, body)) {
                        Toast.makeText(UpdateBlogActivity.this, "Blog Updated!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateBlogActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(UpdateBlogActivity.this, "An error occurred...", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });
        }
    }
}