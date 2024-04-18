package com.example.meowgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        ImageView fullScreenImageView = findViewById(R.id.fullScreenImageView);

        // Get the image resource from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("imageResource")) {
            int imageResource = intent.getIntExtra("imageResource", 0);
            fullScreenImageView.setImageResource(imageResource);
        }
    }
}