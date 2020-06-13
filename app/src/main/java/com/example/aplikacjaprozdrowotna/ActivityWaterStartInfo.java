package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aplikacjaprozdrowotna.R;

public class ActivityWaterStartInfo extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText etWaterKg;
    Button btnDone;

    private SharedPreferences spWater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_start_info);

        ///pobranie danych z klucza WaterStartInfo i przypisanie ich do zmiennej sp
        spWater = getSharedPreferences("WaterStartInfo", MODE_PRIVATE);

        radioGroup = findViewById(R.id.radioGroup);
        etWaterKg = findViewById(R.id.etWaterKg);
        btnDone = findViewById(R.id.btnDone);
        radioButton = findViewById(R.id.rbMan);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etWaterKg.getText().toString().isEmpty()) {
                    Toast.makeText(ActivityWaterStartInfo.this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (radioButton.getText().equals("Mężczyzna")) {
                        int waterNeed = (Integer.valueOf(etWaterKg.getText().toString())) * 33;
                        spWater.edit().putInt("WaterNeed", waterNeed).commit();
                        spWater.edit().putString("WaterTop", "Tyle wody wypiłeś :)").commit();
                    }
                    if (radioButton.getText().equals("Kobieta")) {
                        int waterNeed = (Integer.valueOf(etWaterKg.getText().toString())) * 30;
                        spWater.edit().putInt("WaterNeed", waterNeed).commit();
                        spWater.edit().putString("WaterTop", "Tyle wody wypiłaś :)").commit();
                    }
                    spWater.edit().putInt("Kilograms", Integer.valueOf(etWaterKg.getText().toString())).commit();
                    spWater.edit().putBoolean("IsDone", true).commit();
                    Intent intent = new Intent(ActivityWaterStartInfo.this, ActivityWaterMain.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
    }

    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityWaterStartInfo.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
