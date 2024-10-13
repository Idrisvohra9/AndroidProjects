package com.example.texttospeechdemo;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private Button btn_speak;
    private TextToSpeech tts;
    private EditText et_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this,this);
        btn_speak = findViewById(R.id.btn_speak);
        et_text = findViewById(R.id.et_text);
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Speaking", Toast.LENGTH_SHORT).show();
                speak(et_text.getText().toString());
            }
        });
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.ENGLISH);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","This language is not supported");
            }
        }
    }
    private void speak(String text){
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
    }
}











