package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjaprozdrowotna.R;

import java.text.DecimalFormat;

public class ActivityBMI extends AppCompatActivity {

    EditText etAge;
    EditText etHeight;
    EditText etWeight;
    TextView tvBMI;
    TextView tvIdealWeight;
    Button btnCalc;
    TextView tvBMIInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        tvBMI = findViewById(R.id.tvBMI);
        tvIdealWeight = findViewById(R.id.tvIdealWeight);
        btnCalc = findViewById(R.id.btnCalc);
        tvBMIInfo = findViewById(R.id.tvBMIInfo);
        final String[] BMIInfoArr = {"Ludzie nie są aż tak chudzi xD", "Jesteś wygłodzony!", "Masz znaczną niedowagę!", "Masz niedowagę",
                "Masz doskonałe BMI", "Masz nadwagę", "Cierpisz na otyłość klasy I",
                "Cierpisz na otyłość klasy II!", "Cierpisz na otyłość klasy III!", "Chyba kłamiesz xD"};
        final DecimalFormat df = new DecimalFormat("0.0");

        ///ukrycie info o BMI, odkryje się po wciśnięciu przycisku obliczania
        tvBMIInfo.setVisibility(View.GONE);

        ///przycisk do obliczania BMI
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAge.getText().toString().isEmpty() || etHeight.getText().toString().isEmpty() ||
                etWeight.getText().toString().isEmpty()) {
                    Toast.makeText(ActivityBMI.this, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //obliczanie BMI
                    int age = Integer.parseInt(etAge.getText().toString().trim());
                    float height = Float.parseFloat(etHeight.getText().toString().trim());
                    float weight = Float.parseFloat(etWeight.getText().toString().trim());
                    float heightMeter = height / 100;
                    float BMI = weight / (heightMeter * heightMeter);
                    String strBMI = df.format(BMI) + "";
                    tvBMI.setText(strBMI);

                    //obliczanie wagi dla idealnego BMI = 22,4
                    float idealWeight = (float) (22.4 * heightMeter * heightMeter);
                    String strIdealWeight = df.format(idealWeight) + "";
                    tvIdealWeight.setText(strIdealWeight);

                    //wyświetlenie info o BMI i jego stan
                    tvBMIInfo.setVisibility(View.VISIBLE);
                    if(BMI < 5) {
                        tvBMIInfo.setText(BMIInfoArr[0]);
                    }
                    else if(BMI >= 5 && BMI < 16) {
                        tvBMIInfo.setText(BMIInfoArr[1]);
                    }
                    else if(BMI >= 16 && BMI < 17) {
                        tvBMIInfo.setText(BMIInfoArr[2]);
                    }
                    else if(BMI >= 17 && BMI < 18.5) {
                        tvBMIInfo.setText(BMIInfoArr[3]);
                    }
                    else if(BMI >= 18.5 && BMI < 25) {
                        tvBMIInfo.setText(BMIInfoArr[4]);
                    }
                    else if(BMI >= 25 && BMI < 30) {
                        tvBMIInfo.setText(BMIInfoArr[5]);
                    }
                    else if(BMI >= 30 && BMI < 35) {
                        tvBMIInfo.setText(BMIInfoArr[6]);
                    }
                    else if(BMI >= 35 && BMI < 40) {
                        tvBMIInfo.setText(BMIInfoArr[7]);
                    }
                    else if(BMI >= 40 && BMI < 120) {
                        tvBMIInfo.setText(BMIInfoArr[8]);
                    }
                    else {
                        tvBMIInfo.setText(BMIInfoArr[9]);
                    }
                }
            }
        });


    }
    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityBMI.this, ActivityKilograms.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
