package com.example.formapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {
    Button btnSubmit, btnReset, btnRedirect;
    EditText edName;
    TextView txtName;
    CheckBox chMusic,chDance,chCricket;
    RadioButton rbMale,rbFemale;
    RadioGroup rgGender;
    AutoCompleteTextView autoCity;
    Spinner spCity;
    String city[]={"Ahmedabad","Baroda","Surat","Valsad","Rajkot","Ambaji","Veraval","Amreli","Bhuj","Bardoli"};
    String SelectedGender = "Male";
    String hobbies="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSubmit=findViewById(R.id.submit);
        btnReset=findViewById(R.id.reset);
        btnRedirect=findViewById(R.id.redirect);
        edName=findViewById(R.id.edName);
        autoCity=findViewById(R.id.autoCity);
        spCity=findViewById(R.id.spCity);
        txtName=findViewById(R.id.txtName);
        chMusic=findViewById(R.id.chMusic);
        chDance=findViewById(R.id.chDance);
        chCricket=findViewById(R.id.chCricket);
        rgGender=findViewById(R.id.rgGender);
        rbFemale=findViewById(R.id.rbFemale);

        rbMale=findViewById(R.id.rbMale);
        autoCity.setThreshold(1);

        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,city);
        autoCity.setAdapter(adapter);



        spCity.setAdapter(adapter);
        spCity.setSelection(3);
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "City :"+city[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rbMale){
                    Toast.makeText(MainActivity.this, "Male selected..", Toast.LENGTH_SHORT).show();
                    SelectedGender = "Male";
                }

                else{
                    Toast.makeText(MainActivity.this, "Female selected..", Toast.LENGTH_SHORT).show();
                    SelectedGender = "Female";
                }
            }
        });
        chMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(MainActivity.this, "Music checked..", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Music unchecked..", Toast.LENGTH_SHORT).show();
                }

            }
        });
        chDance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(MainActivity.this, "Dance checked..", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Dance unchecked..", Toast.LENGTH_SHORT).show();
                }

            }
        });
        chCricket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(MainActivity.this, "Cricket checked..", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Cricket unchecked..", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edName.getText().toString();

                if(chCricket.isChecked())
                    hobbies+="Cricket.";
                if(chMusic.isChecked())
                    hobbies+="Music.";
                if(chDance.isChecked())
                    hobbies+="Dance.";


                txtName.setText(MessageFormat.format("Welcome {0},\nYour Hobbies: {1},\nYour Gender: {2}", name, hobbies, SelectedGender));
                Log.i("Name","Your Name is :"+name);
                Toast.makeText(MainActivity.this, "Your Name is :"+name, Toast.LENGTH_SHORT).show();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edName.clearComposingText();
                autoCity.clearComposingText();
            }
        });
        btnRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edName.getText().toString();
//                Using Intents to navigate to a different activity (screens in the app) with the data/context from the form
                Intent sendDataIntent = new Intent(MainActivity.this, MainActivity2.class);
                sendDataIntent.putExtra("name", name);
                sendDataIntent.putExtra("city", autoCity.getText().toString());
                sendDataIntent.putExtra("hobbies", hobbies);
                sendDataIntent.putExtra("gender", SelectedGender);
                startActivity(sendDataIntent);
            }
        });
    }
}