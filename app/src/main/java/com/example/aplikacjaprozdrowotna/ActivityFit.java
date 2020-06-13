package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aplikacjaprozdrowotna.R;

public class ActivityFit extends AppCompatActivity implements View.OnClickListener {

    ProgressBar pbFitDay1, pbFitDay2, pbFitDay3, pbFitDay5, pbFitDay6, pbFitDay7, pbFitDay9,
            pbFitDay10;
    TextView tvFitDay1, tvFitDay2, tvFitDay3, tvFitDay5, tvFitDay6, tvFitDay7, tvFitDay9,
            tvFitDay10;
    Button btnFitDay1, btnFitDay2, btnFitDay3, btnFitDay4, btnFitDay5, btnFitDay6, btnFitDay7,
            btnFitDay8, btnFitDay9, btnFitDay10;

    static int progressDay1, progressDay2, progressDay3, progressDay5, progressDay6, progressDay7,
            progressDay9, progressDay10;
    static String progressDay1Str, progressDay2Str, progressDay3Str, progressDay5Str,
            progressDay6Str, progressDay7Str, progressDay9Str, progressDay10Str;

    SharedPreferences spDay1, spDay2, spDay3, spDay5, spDay6, spDay7, spDay9, spDay10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit);

        btnFitDay4 = findViewById(R.id.btnFitDay4);
        btnFitDay8 = findViewById(R.id.btnFitDay8);


        btnFitDay4.setOnClickListener(this);
        btnFitDay8.setOnClickListener(this);

        ///dodajemy po kolei zmienne z liczbą większą o 1
        fitDay(pbFitDay1, R.id.pbFitDay1, tvFitDay1, R.id.tvFitDay1, btnFitDay1, R.id.btnFitDay1,
                spDay1, "FitDay1", "FitDay1Progress", progressDay1,
                progressDay1Str);
        fitDay(pbFitDay2, R.id.pbFitDay2, tvFitDay2, R.id.tvFitDay2, btnFitDay2, R.id.btnFitDay2,
                spDay2, "FitDay2", "FitDay2Progress", progressDay2,
                progressDay2Str);
        fitDay(pbFitDay3, R.id.pbFitDay3, tvFitDay3, R.id.tvFitDay3, btnFitDay3, R.id.btnFitDay3,
                spDay3, "FitDay3", "FitDay3Progress", progressDay3,
                progressDay3Str);
        fitDay(pbFitDay5, R.id.pbFitDay5, tvFitDay5, R.id.tvFitDay5, btnFitDay5, R.id.btnFitDay5,
                spDay5, "FitDay5", "FitDay5Progress", progressDay5,
                progressDay5Str);
        fitDay(pbFitDay6, R.id.pbFitDay6, tvFitDay6, R.id.tvFitDay6, btnFitDay6, R.id.btnFitDay6,
                spDay6, "FitDay6", "FitDay6Progress", progressDay6,
                progressDay6Str);
        fitDay(pbFitDay6, R.id.pbFitDay6, tvFitDay6, R.id.tvFitDay6, btnFitDay6, R.id.btnFitDay6,
                spDay6, "FitDay6", "FitDay6Progress", progressDay6,
                progressDay6Str);
        fitDay(pbFitDay7, R.id.pbFitDay7, tvFitDay7, R.id.tvFitDay7, btnFitDay7, R.id.btnFitDay7,
                spDay7, "FitDay7", "FitDay7Progress", progressDay7,
                progressDay7Str);
        fitDay(pbFitDay9, R.id.pbFitDay9, tvFitDay9, R.id.tvFitDay9, btnFitDay9, R.id.btnFitDay9,
                spDay9, "FitDay9", "FitDay9Progress", progressDay9,
                progressDay9Str);
        fitDay(pbFitDay10, R.id.pbFitDay10, tvFitDay10, R.id.tvFitDay10, btnFitDay10, R.id.btnFitDay10,
                spDay10, "FitDay10", "FitDay10Progress", progressDay10,
                progressDay10Str);
    }

    private void fitDay(ProgressBar pbFitDay, int pbFitDayID, TextView tvFitDay, int tvFitDayID,
                        Button btnFitDay, int btnFitDayID, SharedPreferences spDay, String FitDay,
                        String FitDayProgress, int progressDay, String progressDayStr) {
        pbFitDay = findViewById(pbFitDayID);
        tvFitDay = findViewById(tvFitDayID);
        btnFitDay = findViewById(btnFitDayID);

        spDay = getSharedPreferences(FitDay, MODE_PRIVATE);

        progressDay = spDay.getInt(FitDayProgress, 0);
        progressDayStr = progressDay + "%";

        pbFitDay.setProgress(progressDay);
        tvFitDay.setText(progressDayStr);

        btnFitDay.setOnClickListener(this);
    }

    ///przechodzenie do aktywności z ćwiczeniami
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnFitDay1) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDay1.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay2) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDay2.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay3) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDay3.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay4) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDayFree.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay5) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDay5.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay6) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDay6.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay7) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDay7.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay8) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDayFree.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay9) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDay9.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnFitDay10) {
            Intent intent = new Intent(ActivityFit.this, ActivityFitDay10.class);
            startActivity(intent);
        }
    }

    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityFit.this, ActivityKilograms.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
