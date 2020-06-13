package com.example.aplikacjaprozdrowotna;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aplikacjaprozdrowotna.R;

public class MainActivity extends AppCompatActivity {

    Button btnCig;
    Button btnKilograms;
    Button btnWater;

    private SharedPreferences spCig;
    private SharedPreferences spWater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///pobranie danych z klucza CigStartInfo i WaterStartInfo i przypisanie ich do zmiennej spCig i spWater
        spCig = getSharedPreferences("CigStartInfo", MODE_PRIVATE);
        spWater = getSharedPreferences("WaterStartInfo", MODE_PRIVATE);

        ///ustawienie wartości IsPopup na false dzięki czemu w aktywności z papierosami pojawi się motywujący tekst
        spCig.edit().putBoolean("IsPopup", false).commit();

        btnCig = findViewById(R.id.btnCig);
        btnKilograms = findViewById(R.id.btnKilograms);
        btnWater = findViewById(R.id.btnWater);

        btnCig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spCig.getBoolean("IsDone", false) != true) {
                    Intent intent = new Intent(MainActivity.this, ActivityCigStartInfo.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ActivityCigMain.class);
                    startActivity(intent);
                }
            }
        });

        btnKilograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityKilograms.class);
                startActivity(intent);
            }
        });

        btnWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spWater.getBoolean("IsDone", false) != true) {
                    Intent intent = new Intent(MainActivity.this, ActivityWaterStartInfo.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ActivityWaterMain.class);
                    startActivity(intent);
                }
            }
        });
    }
}
