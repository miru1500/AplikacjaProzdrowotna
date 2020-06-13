package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.aplikacjaprozdrowotna.R;

public class ActivityCigAchievments extends AppCompatActivity {

    ImageView ivNoSmoked10, ivNoSmoked20, ivNoSmoked50, ivNoSmoked100;
    ImageView ivSavedMoney10, ivSavedMoney50, ivSavedMoney100, ivSavedMoney500;
    ImageView ivHealth1, ivHealth2, ivHealth3, ivHealth4;

    private SharedPreferences spCig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cig_achievments);

        ///utworzenie zmiennej z kluczem CigStartInfo, pod którym mają być zapisywane dane
        spCig = getSharedPreferences("CigStartInfo", MODE_PRIVATE);

        ivNoSmoked10 = findViewById(R.id.ivNoSmoked10);
        ivNoSmoked20 = findViewById(R.id.ivNoSmoked20);
        ivNoSmoked50 = findViewById(R.id.ivNoSmoked50);
        ivNoSmoked100 = findViewById(R.id.ivNoSmoked100);
        ivSavedMoney10 = findViewById(R.id.ivSavedMoney10);
        ivSavedMoney50 = findViewById(R.id.ivSavedMoney50);
        ivSavedMoney100 = findViewById(R.id.ivSavedMoney100);
        ivSavedMoney500 = findViewById(R.id.ivSavedMoney500);
        ivHealth1 = findViewById(R.id.ivHealth1);
        ivHealth2 = findViewById(R.id.ivHealth2);
        ivHealth3 = findViewById(R.id.ivHealth3);
        ivHealth4 = findViewById(R.id.ivHealth4);

        noSmokedAchievment();
        savedMoneyAchievment();
        healthAchievment();

    }

    ///koloruje buźki jeśli osiągnięcie niepalenia zostało osiągnięte
    private void noSmokedAchievment() {
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Cigs10AchievmentSeconds", 0)) {
            ivNoSmoked10.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorBlue));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Cigs20AchievmentSeconds", 0)) {
            ivNoSmoked20.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorBlue));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Cigs50AchievmentSeconds", 0)) {
            ivNoSmoked50.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorBlue));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Cigs100AchievmentSeconds", 0)) {
            ivNoSmoked100.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorBlue));
        }
    }

    ///koloruje buźki jeśli osiągnięcie oszczędzania zostało osiągnięte
    private void savedMoneyAchievment() {
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Money10AchievmentSeconds", 0)) {
            ivSavedMoney10.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorPink));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Money50AchievmentSeconds", 0)) {
            ivSavedMoney50.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorPink));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Money100AchievmentSeconds", 0)) {
            ivSavedMoney100.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorPink));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Money500AchievmentSeconds", 0)) {
            ivSavedMoney500.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorPink));
        }
    }

    private void healthAchievment() {
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Health20Mins", 0)) {
            ivHealth1.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorCyan));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Health8Hours", 0)) {
            ivHealth2.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorCyan));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Health24Hours", 0)) {
            ivHealth3.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorCyan));
        }
        if((System.currentTimeMillis() / 1000) >= spCig.getLong("Health48Hours", 0)) {
            ivHealth4.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.colorCyan));
        }
    }

    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityCigAchievments.this, ActivityCigMain.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
