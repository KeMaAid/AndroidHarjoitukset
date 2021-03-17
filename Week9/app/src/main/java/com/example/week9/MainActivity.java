package com.example.week9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayoutMovies;
    private Spinner spinner;
    private Button buttonDate;
    private Button buttonOpenTime;
    private Button buttonCloseTime;
    private Calendar calendar;
    private Context context;
    private EditText editTextSearchword;

    private int positionTheatre;

    private LocalDate localDate;
    private LocalTime openTime;
    private LocalTime closeTime;
    private CinemaController cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = this;

        cc = CinemaController.getInstance();

        localDate = LocalDate.now();
        System.out.println("\n\n\n"+localDate.toString()+"\n\n\n");
        openTime = null;
        closeTime = null;

        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        linearLayoutMovies = (LinearLayout) findViewById(R.id.linearLayoutMovies);
        linearLayoutMovies.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT
        ));

        spinner = (Spinner) findViewById((R.id.spinner));
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cc.cinemasStringArray()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionTheatre = position;
                loadMovies();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO
            }
        });

        editTextSearchword = (EditText) findViewById(R.id.editTextName);
        editTextSearchword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadMovies();
            }
        });

        buttonDate = (Button) findViewById(R.id.buttonDate);
        buttonDate.setText(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        buttonOpenTime = (Button) findViewById(R.id.buttonOpenTime);
        buttonCloseTime = (Button) findViewById(R.id.buttonCloseTime);

    }

    public void loadMovies(){
        TextView moviesView = new TextView(this);
        if(this.positionTheatre ==0){
            Map<String, ArrayList<Movie>> movies = cc.findMovies(this.editTextSearchword.getText().toString(), this.localDate, this.openTime, this.closeTime);
            if (movies.size() == 0) {
                moviesView.append("No movies to show");
            } else {
                for (String key : movies.keySet()) {
                    moviesView.append(key+"\n");

                    for (Movie movie : movies.get(key)) {
                        String cinemaName = cc.getCinemaName(movie.getLocationID());
                        if(cinemaName != null) {
                            moviesView.append("\t" + cinemaName +" "+ movie.getStartTime()+ "\n");
                        }
                    }
                }
            }
        } else {
            Movie[] movies = cc.getMovies(cc.getCinemaID(this.positionTheatre), this.editTextSearchword.getText().toString(), this.localDate, this.openTime, this.closeTime);
            if (movies.length == 0) {
                moviesView.append("No movies to show");
            } else {
                for (Movie movie : movies) {
                    moviesView.append(movie + "\n");
                }
            }
        }

        linearLayoutMovies.removeAllViews();
        linearLayoutMovies.addView(moviesView);
    }
    public void handleClickDate(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                localDate = LocalDate.of(year, month+1, dayOfMonth);
                buttonDate.setText(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
                loadMovies();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void handleClickOpenTime(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                openTime =LocalTime.of(hourOfDay, minute);
                buttonOpenTime.setText(openTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        loadMovies();
    }
    public void handleClickCloseTime(View view){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                closeTime =LocalTime.of(hourOfDay, minute);
                buttonCloseTime.setText(closeTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        loadMovies();
    }

}

