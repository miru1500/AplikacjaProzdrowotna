package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplikacjaprozdrowotna.R;

public class ActivityFitDay10 extends AppCompatActivity implements View.OnClickListener {

    Button btnFitDay10Ex1;
    Button btnFitDay10Ex2;
    Button btnFitDay10Ex3;
    Button btnFitDay10Ex4;
    Button btnFitDay10Ex5;
    Button btnFitDay10Ex6;
    Button btnFitDay10Ex7;

    Button btnBack;
    Button btnReset;

    private static int[] isClick;
    private static int exFinished;

    ///zmienna do zapisywania stanu kluczowych zmiennych
    private SharedPreferences sp;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_day10);

        btnFitDay10Ex1 = findViewById(R.id.btnFitDay10Ex1);
        btnFitDay10Ex2 = findViewById(R.id.btnFitDay10Ex2);
        btnFitDay10Ex3 = findViewById(R.id.btnFitDay10Ex3);
        btnFitDay10Ex4 = findViewById(R.id.btnFitDay10Ex4);
        btnFitDay10Ex5 = findViewById(R.id.btnFitDay10Ex5);
        btnFitDay10Ex6 = findViewById(R.id.btnFitDay10Ex6);
        btnFitDay10Ex7 = findViewById(R.id.btnFitDay10Ex7);

        btnBack = findViewById(R.id.btnBack);
        btnReset = findViewById(R.id.btnReset);

        isClick = new int[7];

        ///zapisujemy zmienne pod kluczem "FitDay10"
        sp = ActivityFitDay10.this.getSharedPreferences("FitDay10", MODE_PRIVATE);

        ///pobiera stan zapisanych elementów tablicy isClick i zmiennej exFinished
        isClick[0] = sp.getInt("FitDay10Ex1", 0);
        isClick[1] = sp.getInt("FitDay10Ex2", 0);
        isClick[2] = sp.getInt("FitDay10Ex3", 0);
        isClick[3] = sp.getInt("FitDay10Ex4", 0);
        isClick[4] = sp.getInt("FitDay10Ex5", 0);
        isClick[5] = sp.getInt("FitDay10Ex6", 0);
        isClick[6] = sp.getInt("FitDay10Ex7", 0);
        exFinished = sp.getInt("FitDay10ExFinished", 0);

        ///zapamiętywanie koloru po odkliknięciu ćwiczenia
        setBtnColor();

        btnFitDay10Ex1.setOnClickListener(this);
        btnFitDay10Ex2.setOnClickListener(this);
        btnFitDay10Ex3.setOnClickListener(this);
        btnFitDay10Ex4.setOnClickListener(this);
        btnFitDay10Ex5.setOnClickListener(this);
        btnFitDay10Ex6.setOnClickListener(this);
        btnFitDay10Ex7.setOnClickListener(this);

        btnBack.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        ///odklikiwanie skończonych ćwiczeń i odhaczanie ich w metodzie exClick
        if(v.getId() == R.id.btnFitDay10Ex1) {
            exClick(1);
            btnFitDay10Ex1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }
        if(v.getId() == R.id.btnFitDay10Ex2) {
            exClick(2);
            btnFitDay10Ex2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }
        if(v.getId() == R.id.btnFitDay10Ex3) {
            exClick(3);
            btnFitDay10Ex3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }
        if(v.getId() == R.id.btnFitDay10Ex4) {
            exClick(4);
            btnFitDay10Ex4.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }
        if(v.getId() == R.id.btnFitDay10Ex5) {
            exClick(5);
            btnFitDay10Ex5.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }
        if(v.getId() == R.id.btnFitDay10Ex6) {
            exClick(6);
            btnFitDay10Ex6.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }
        if(v.getId() == R.id.btnFitDay10Ex7) {
            exClick(7);
            btnFitDay10Ex7.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }

        if(v.getId() == R.id.btnBack) {
            Intent intent = new Intent(ActivityFitDay10.this, ActivityFit.class);
            startActivity(intent);
            super.onBackPressed();
        }
        ///resetowanie zmiennych do zera
        if(v.getId() == R.id.btnReset) {
            for(int i = 0; i < isClick.length; i++) {
                isClick[i] = 0;
                sp.edit().putInt("FitDay10Ex" + (i+1), isClick[i]).commit();
            }
            sp.edit().putInt("FitDay10Progress", 0).commit();
            exFinished = 0;
            sp.edit().putInt("FitDay10ExFinished", exFinished).commit();
            btnFitDay10Ex1.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            btnFitDay10Ex2.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            btnFitDay10Ex3.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            btnFitDay10Ex4.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            btnFitDay10Ex5.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            btnFitDay10Ex6.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            btnFitDay10Ex7.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            Toast.makeText(ActivityFitDay10.this, "Zresetowano", Toast.LENGTH_SHORT).show();
        }
    }

    ///metoda odhaczająca ćwiczenia po kliknięciu przycisku
    private void exClick(int exNumber) {
        if(isClick[exNumber - 1] == 0) {
            exFinished = exFinished + 1;
            //tu zapisuje stan zmiennej exFinished do klucza ExFinishedDay1
            sp.edit().putInt("FitDay10ExFinished", exFinished).commit();
            isClick[exNumber - 1] = 1;
        }
        sp.edit().putInt("FitDay10Ex" + exNumber , isClick[exNumber - 1]).commit();
        if(exFinished < isClick.length) {
            sp.edit().putInt("FitDay10Progress", (exFinished * (100/isClick.length))).commit();
        }
        else if(exFinished >= isClick.length){
            sp.edit().putInt("FitDay10Progress", 100).commit();
        }
    }

    private void setBtnColor() {
        if(isClick[0] == 1)
            btnFitDay10Ex1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        if(isClick[1] == 1)
            btnFitDay10Ex2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        if(isClick[2] == 1)
            btnFitDay10Ex3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        if(isClick[3] == 1)
            btnFitDay10Ex4.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        if(isClick[4] == 1)
            btnFitDay10Ex5.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        if(isClick[5] == 1)
            btnFitDay10Ex6.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        if(isClick[6] == 1)
            btnFitDay10Ex7.setBackgroundColor(getResources().getColor(R.color.colorGreen));
    }

    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityFitDay10.this, ActivityFit.class);
        startActivity(intent);
        super.onBackPressed();
    }
}