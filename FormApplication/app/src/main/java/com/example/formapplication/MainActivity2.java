package com.example.formapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView placeName = findViewById(R.id.txtData);
        TextView placeHobby = findViewById(R.id.showHobby);
        TextView placeGender = findViewById(R.id.gender);
        TextView placeCity = findViewById(R.id.city);
        Intent formData = getIntent();

        String UserName = formData.getStringExtra("name");
        String Hobbies = formData.getStringExtra("hobbies");
        String Gender = formData.getStringExtra("gender");
        String City = formData.getStringExtra("city");
        placeName.append(UserName);
        placeHobby.append(Hobbies);
        placeGender.append(Gender);
        placeCity.append(City);
    }
}