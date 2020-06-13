package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjaprozdrowotna.R;

public class ActivityWaterMain extends AppCompatActivity {

    TextView tvWaterTop;
    TextView tvProgressWater;
    TextView tvCriticalLevel;
    Button btnAddWater;
    Button btnSet;
    Button btnDayReset;
    ProgressBar pbWater;
    ScrollView svBackground;

    private SharedPreferences spWater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_main);

        ///pobranie danych z klucza WaterStartInfo i przypisanie ich do zmiennej spWater
        spWater = getSharedPreferences("WaterStartInfo", MODE_PRIVATE);

        tvWaterTop = findViewById(R.id.tvWaterTop);
        tvProgressWater = findViewById(R.id.tvProgressWater);
        tvCriticalLevel = findViewById(R.id.tvCriticalLevel);
        btnAddWater = findViewById(R.id.btnAddWater);
        btnSet = findViewById(R.id.btnSet);
        btnDayReset = findViewById(R.id.btnDayReset);
        pbWater = findViewById(R.id.pbWater);
        svBackground = findViewById(R.id.svBackground);

        tvWaterTop.setText(spWater.getString("WaterTop", "Tekst"));

        ///sprawdza czy ilość wypitej wody jest mniejsza niż czterokrotność wymaganej wody jeśli tak wyświetla ostrzeżenie
        if(spWater.getInt("WaterProgress", 0) < (spWater.getInt("Kilograms", 0)) * 150) {
            tvProgressWater.setText(spWater.getInt("WaterProgress", 0) + "/" + spWater.getInt("WaterNeed", 666) + "ml");
            int waterProgress = (spWater.getInt("WaterProgress", 0) * 100) / spWater.getInt("WaterNeed", 666);
            pbWater.setProgress(waterProgress);
            tvCriticalLevel.setVisibility(View.GONE);
            tvProgressWater.setVisibility(View.VISIBLE);
            svBackground.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        else {
            pbWater.setProgress(100);
            tvWaterTop.setText("Nie pij już wody :(");
            btnAddWater.setText("Nie dodawaj już wody ");
            tvCriticalLevel.setVisibility(View.VISIBLE);
            tvProgressWater.setVisibility(View.GONE);
            btnAddWater.setText("Nie dodawaj wody!");
            tvWaterTop.setText("Nie pij już wody :(");
            svBackground.setBackgroundColor(Color.parseColor("#ff0000"));
        }

        btnAddWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityWaterMain.this, ActivityAddWater.class);
                startActivity(intent);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityWaterMain.this, "Przytrzymaj by zmienić ustawienia profilu", Toast.LENGTH_SHORT).show();
            }
        });

        btnSet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                spWater.edit().putBoolean("IsDone", false).commit();
                spWater.edit().putInt("WaterProgress", 0).commit();
                Intent intent = new Intent(ActivityWaterMain.this, ActivityWaterStartInfo.class);
                startActivity(intent);
                return false;
            }
        });

        btnDayReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spWater.edit().putInt("WaterProgress", 0).commit();
                tvProgressWater.setVisibility(View.VISIBLE);
                tvCriticalLevel.setVisibility(View.GONE);
                tvProgressWater.setText(spWater.getInt("WaterProgress",0)+"/"+spWater.getInt("WaterNeed", 666)+"ml");
                pbWater.setProgress(0);
                btnAddWater.setText("Dodaj wodę");
                tvWaterTop.setText(spWater.getString("WaterTop", "Text"));
                svBackground.setBackgroundColor(Color.parseColor("#ffffff"));
                Toast.makeText(ActivityWaterMain.this, "Zresetowano", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityWaterMain.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
