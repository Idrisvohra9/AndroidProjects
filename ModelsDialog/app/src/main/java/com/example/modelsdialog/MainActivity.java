package com.example.modelsdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button showModelBtn;
    BasicDialog basicDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showModelBtn = findViewById(R.id.button);
        showModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicDialog = new BasicDialog("Hello", "Demo Msg");
                basicDialog.show(getSupportFragmentManager(), "basic_dialog");
            }
        });
    }
}