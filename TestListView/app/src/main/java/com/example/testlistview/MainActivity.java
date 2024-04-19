package com.example.testlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listData;
    String nameArr[] = {"Ahmedabad", "Surat", "Baroda", "Rajkot"};
    String numberArr[] = {"9012012", "121231", "5342322", "33121212"};
    int imgArr[] = {R.mipmap.i1, R.mipmap.i1, R.mipmap.i3, R.mipmap.i4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listData = findViewById(R.id.listData);
        CustomAdapter adapter = new CustomAdapter(MainActivity.this, imgArr, nameArr, numberArr);
        listData.setAdapter(adapter);
    }
}