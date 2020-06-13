package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aplikacjaprozdrowotna.R;

public class ActivityKilograms extends AppCompatActivity {

    Button btnBMI;
    Button btnFit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kilograms);

        btnBMI = findViewById(R.id.btnBMI);
        btnFit = findViewById(R.id.btnFit);

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityKilograms.this, ActivityBMI.class);
                startActivity(intent);
            }
        });

        btnFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityKilograms.this, ActivityFit.class);
                startActivity(intent);
            }
        });
    }
    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityKilograms.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
