package com.idris.jokesapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ProgressBar progressBar;
    private TextView punchline, setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        punchline = findViewById(R.id.punchline);
        setup = findViewById(R.id.setup);
        progressBar.setVisibility(View.GONE);
        button.setOnClickListener(v -> fetchJoke());
    }

    private void fetchJoke() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Joke> call = apiService.getRandomJoke();
        call.enqueue(new Callback<Joke>() {

            @Override
            public void onFailure(Call<Joke> call, Throwable throwable) {

            }

            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if (response.isSuccessful()) {
                    Joke joke = response.body();
                    if (joke != null) {
                        punchline.setText(joke.getPunchline());
                        setup.setText(joke.getSetup());
                    }
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch joke", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}