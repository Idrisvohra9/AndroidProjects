package com.example.datepicker;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btnPickDateTime;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPickDateTime = findViewById(R.id.btnPickDateTime);
        calendar = Calendar.getInstance();

        btnPickDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
    }

    private void showDateTimePicker() {
        // Get current date and time
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Date Picker
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Update the calendar with the selected date
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                        // Time Picker
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                MainActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                        // Update the calendar with the selected time
                                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                        calendar.set(Calendar.MINUTE, selectedMinute);

                                        // Display the selected date and time
                                        String dateTime = DateFormat.getDateTimeInstance().format(calendar.getTime());
                                        Toast.makeText(MainActivity.this, "Selected Date and Time: " + dateTime, Toast.LENGTH_LONG).show();
                                    }
                                },
                                hour,
                                minute,
                                true
                        );

                        timePickerDialog.show();
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }
}
