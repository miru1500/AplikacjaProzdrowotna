package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikacjaprozdrowotna.R;

public class ActivityAddWater extends AppCompatActivity {

    Button btnAcceptWater;
    EditText etAddWater;
    int waterProgress;

    private SharedPreferences spWater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water);

        ///pobranie danych z klucza WaterStartInfo i przypisanie ich do zmiennej spWater
        spWater = getSharedPreferences("WaterStartInfo", MODE_PRIVATE);

        btnAcceptWater = findViewById(R.id.btnAcceptWater);
        etAddWater = findViewById(R.id.etAddWater);

        btnAcceptWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAddWater.getText().toString().isEmpty()) {
                    Toast.makeText(ActivityAddWater.this, "Wpisz ilość wypitej wody", Toast.LENGTH_SHORT).show();
                }
                else {
                    waterProgress = spWater.getInt("WaterProgress", 0) + Integer.valueOf(etAddWater.getText().toString());
                    spWater.edit().putInt("WaterProgress", waterProgress).commit();
                    Intent intent = new Intent(ActivityAddWater.this, ActivityWaterMain.class);
                    startActivity(intent);
                }
            }
        });
    }

    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityAddWater.this, ActivityWaterMain.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
