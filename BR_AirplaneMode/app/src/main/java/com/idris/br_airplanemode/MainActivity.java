// Package declaration for the app
package com.idris.br_airplanemode;

// Import necessary Android and AndroidX classes

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Main activity class that extends AppCompatActivity
public class MainActivity extends AppCompatActivity {

    // Declare a private instance of AirplaneModeReceiver
    private AirplaneModeReceiver airplaneModeReceiver;

    // Override the onCreate method to set up the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the superclass onCreate method
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main);
        // Set up window insets listener for the main view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Get the system bars insets
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply padding to the view based on system bar insets
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Return the original insets
            return insets;
        });
        // Find the root view of the activity
        View rootview = findViewById(android.R.id.content);
        // Initialize the AirplaneModeReceiver with the root view
        airplaneModeReceiver = new AirplaneModeReceiver(rootview);
    }

    // Override the onResume method to register the receiver
    @Override
    protected void onResume() {
        // Call the superclass onResume method
        super.onResume();
        // Create an IntentFilter for airplane mode changes
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        // Register the airplaneModeReceiver with the intent filter
        registerReceiver(airplaneModeReceiver, intentFilter);
    }

    // Override the onDestroy method to unregister the receiver
    @Override
    protected void onDestroy() {
        // Call the superclass onDestroy method
        super.onDestroy();
        // Unregister the airplaneModeReceiver
        unregisterReceiver(airplaneModeReceiver);
    }
}