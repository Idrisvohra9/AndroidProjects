// Package declaration for the application
package com.example.esf;

// Import statements for required Android and Java classes
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// Import statements for AndroidX libraries
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import statements for Java concurrent utilities
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Main activity class definition, extending AppCompatActivity
public class MainActivity extends AppCompatActivity {

    // Declare UI elements as class variables
    private Button btn_async_task;
    private EditText et_time;
    private TextView tv_final_result;
    // Declare ExecutorService for background tasks
    private ExecutorService excecutorSerivce;
    // Declare Handler for main thread operations
    private Handler mainHandler;
    // Declare ProgressDialog to show loading state
    private ProgressDialog progressDialog;

    // Override onCreate method to initialize the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call superclass onCreate method
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main);
        // Set up window insets listener for proper layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements by finding them in the layout
        btn_async_task = findViewById(R.id.btn_run);
        et_time = findViewById(R.id.it_time);
        tv_final_result = findViewById(R.id.tv_result);

        // Create a single-threaded ExecutorService
        excecutorSerivce = Executors.newSingleThreadExecutor();
        // Create a Handler associated with the main looper
        mainHandler = new Handler(Looper.getMainLooper());

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Processing");
        progressDialog.setCancelable(false);

        // Set click listener for the async task button
        btn_async_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the time input from the EditText
                String timeText = et_time.getText().toString();
                // Check if the input is not empty
                if(!timeText.isEmpty()){
                    // Parse the input to an integer
                    int timeInSeconds = Integer.parseInt(timeText);
                    // Show the progress dialog
                    progressDialog.show();
                    // Submit the background task to the ExecutorService
                    excecutorSerivce.submit(new BackgroundTask(timeInSeconds));
                }
                else{
                    // Display an error message if the input is empty
                    tv_final_result.setText("Please enter a valid number");
                }
            }
        });
    }

    // Inner class for background task
    private class BackgroundTask implements Runnable{
        // Store the time in seconds
        private final int timeInSeconds;

        // Constructor to initialize the time
        BackgroundTask(int timeInSeconds){
            this.timeInSeconds = timeInSeconds;
        }

        // Run method to be executed in background
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            try{
                // Simulate work by sleeping for the specified time
                Thread.sleep((timeInSeconds * 1000L));
            } catch(InterruptedException e){
                // Print stack trace if interrupted
                e.printStackTrace();
            }
            // Post a runnable to the main thread to update UI
            mainHandler.post(()->{
                // Dismiss the progress dialog if it's showing
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                // Update the result TextView
                tv_final_result.setText(String.format("Task completed in %d seconds.", timeInSeconds));
            });
        }
    }
}