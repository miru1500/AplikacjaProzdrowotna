package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjaprozdrowotna.R;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.Random;

public class ActivityCigMain extends AppCompatActivity {

    TextView tvDaysWithoutCigs;
    TextView tvNoSmokeCigs;
    TextView tvSavedMoney;
    Button btnReset;
    Button btnAchievments;
    Button btnTargets;
    long cigs10AchievmentSeconds, cigs20AchievmentSeconds, cigs50AchievmentSeconds, cigs100AchievmentSeconds, x;
    long money10AchievmentSeconds, money50AchievmentSeconds, money100AchievmentSeconds, money500AchievmentSeconds;
    long health20Mins, health8Hours, health24Hours, health48Hours;

    String[] motivates;

    static int secondsPassed;

    private static SharedPreferences spCig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cig_main);

        ///pobranie danych z klucza CigStartInfo i przypisanie ich do zmiennej sp
        spCig = getSharedPreferences("CigStartInfo", MODE_PRIVATE);

        tvDaysWithoutCigs = findViewById(R.id.tvDaysWithoutCigs);
        tvNoSmokeCigs = findViewById(R.id.tvNoSmokeCigs);
        tvSavedMoney = findViewById(R.id.tvSavedMoney);
        btnReset = findViewById(R.id.btnReset);
        btnAchievments = findViewById(R.id.btnAchievments);
        btnTargets = findViewById(R.id.btnTargets);

        secondsPassed = spCig.getInt("SecondsPassed", 0);

        ///tablica motywacyjnych tekstów oraz metoda wywołująca wyskakujące okno z motywującym tekstem
        motivates = new String[]{"Nie obawiaj się porzucić czegoś dobrego dla czegoś wspaniałego.",
                "Punktem wyjścia dla wszystkich osiągnięć jest pragnienie.",
                "Wszelki postęp dzieje się poza strefą komfortu.",
                "Jeśli potrafisz o czymś marzyć, to potrafisz także tego dokonać.",
                "Wszystkie nasze marzenia mogą się stać rzeczywistością - jeśli mamy odwagę je realizować."};
        motivateText();

        daysWithoutCigs();
        noSmokeCigs();
        savedMoney();

        ///obliczanie ile milisekund jest potrzebnych żeby otrzymać osiągnięcie
        if(spCig.getBoolean("IsTimeAchievmentCounted", false) == false) {
            long currentTime = System.currentTimeMillis();

            //obliczanie czasu potrzebnego dla osiągnięcia niepalenia papierosów
            cigs10AchievmentSeconds = (((10 * 86400) / spCig.getInt("CigsSmoked", 1)) - secondsPassed() + (currentTime/1000));
            spCig.edit().putLong("Cigs10AchievmentSeconds", cigs10AchievmentSeconds).commit();
            cigs20AchievmentSeconds = (((20 * 86400) / spCig.getInt("CigsSmoked", 1)) - secondsPassed() + (currentTime/1000));
            spCig.edit().putLong("Cigs20AchievmentSeconds", cigs20AchievmentSeconds).commit();
            cigs50AchievmentSeconds = (((50 * 86400) / spCig.getInt("CigsSmoked", 1)) - secondsPassed() + (currentTime/1000));
            spCig.edit().putLong("Cigs50AchievmentSeconds", cigs50AchievmentSeconds).commit();
            cigs100AchievmentSeconds = (((100 * 86400) / spCig.getInt("CigsSmoked", 1)) - secondsPassed() + (currentTime/1000));
            spCig.edit().putLong("Cigs100AchievmentSeconds", cigs100AchievmentSeconds).commit();

            //obliczanie czasu potrzebnego dla osiągnięcia zaoszczędzonych pieniędzy
            float proporcja = (((float) spCig.getInt("CigsSmoked", 1) / (float) spCig.getInt("CigsInPack", 1)) * spCig.getFloat("PackCost", 1));
            money10AchievmentSeconds = (long)((10 * 86400) / proporcja) - secondsPassed() + (currentTime / 1000);
            spCig.edit().putLong("Money10AchievmentSeconds", money10AchievmentSeconds).commit();
            money50AchievmentSeconds = (long)((50 * 86400) / proporcja) - secondsPassed() + (currentTime / 1000);
            spCig.edit().putLong("Money50AchievmentSeconds", money50AchievmentSeconds).commit();
            money100AchievmentSeconds = (long)((100 * 86400) / proporcja) - secondsPassed() + (currentTime / 1000);
            spCig.edit().putLong("Money100AchievmentSeconds", money100AchievmentSeconds).commit();
            money500AchievmentSeconds = (long)((500 * 86400) / proporcja) - secondsPassed() + (currentTime / 1000);
            spCig.edit().putLong("Money500AchievmentSeconds", money500AchievmentSeconds).commit();

            ///obliczanie czasu potrzebnego dla osiągnięcia odzyskania zdrowia
            long quitMoment = (spCig.getLong("QuitDateMillis", 0) + spCig.getLong("QuitTimeMillis", 0)) / 1000;
            health20Mins = quitMoment + 60 * 20;
            spCig.edit().putLong("Health20Mins", health20Mins).commit();
            health8Hours = quitMoment + 60 * 60 * 8;
            spCig.edit().putLong("Health8Hours", health8Hours).commit();
            health24Hours = quitMoment + 60 * 60 * 24;
            spCig.edit().putLong("Health24Hours", health24Hours).commit();
            health48Hours = quitMoment + 60 * 60 * 48;
            spCig.edit().putLong("Health48Hours", health48Hours).commit();
            spCig.edit().putBoolean("IsTimeAchievmentCounted", true).commit();
        }

        if(spCig.getBoolean("IsCounted", false) == false) {
            count10Cigs();
            count20Cigs();
            count50Cigs();
            count100Cigs();
            count10Money();
            count50Money();
            count100Money();
            count500Money();
            countHealth20Mins();
            countHealth8Hours();
            countHealth24Hours();
            countHealth48Hours();
            spCig.edit().putBoolean("IsCounted", true).commit();
        }


        //----------------------------------
        ///przechodzenie do innych aktywności
        btnAchievments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCigMain.this, ActivityCigAchievments.class);
                startActivity(intent);
            }
        });

        btnTargets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCigMain.this, ActivityCigTarget.class);
                startActivityForResult(intent, 1);
            }
        });

        ///koniec przechodzenia do innych aktywności
        //-----------------------------------------

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityCigMain.this, "Przytrzymaj by zresetować", Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                spCig.edit().putBoolean("IsDone", false).commit();
                Intent intent = new Intent(ActivityCigMain.this, ActivityCigStartInfo.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private void motivateText() {
        if(spCig.getBoolean("IsPopup", false) == false) {
            final int random = new Random().nextInt(5);
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCigMain.this);

            builder.setCancelable(false);
            builder.setTitle("Dasz radę!");
            builder.setMessage(motivates[random]);

            builder.setNeutralButton("Oki doki :)", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            spCig.edit().putBoolean("IsPopup", true).commit();
            builder.show();
        }
    }

    ///uruchamia powiadomienie kiedy określony czas zostanie osiągnięty
    private void count10Cigs() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcast10Cigs.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Cigs10AchievmentSeconds", 0) * 1000, pendingIntent);
    }
    private void count20Cigs() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcast20Cigs.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Cigs20AchievmentSeconds", 0) * 1000, pendingIntent);
    }
    private void count50Cigs() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcast50Cigs.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Cigs50AchievmentSeconds", 0) * 1000, pendingIntent);
    }
    private void count100Cigs() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcast100Cigs.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Cigs100AchievmentSeconds", 0) * 1000, pendingIntent);
    }
    private void count10Money() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcast10Money.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Money10AchievmentSeconds", 0) * 1000, pendingIntent);
    }
    private void count50Money() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcast50Money.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Money50AchievmentSeconds", 0) * 1000, pendingIntent);
    }
    private void count100Money() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcast100Money.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Money100AchievmentSeconds", 0) * 1000, pendingIntent);
    }
    private void count500Money() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcast500Money.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Money500AchievmentSeconds", 0) * 1000, pendingIntent);
    }
    private void countHealth20Mins() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcastHealth20Mins.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Health20Mins", 0) * 1000, pendingIntent);
    }
    private void countHealth8Hours() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcastHealth8Hours.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Health8Hours", 0) * 1000, pendingIntent);
    }
    private void countHealth24Hours() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcastHealth24Hours.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Health24Hours", 0) * 1000, pendingIntent);
    }
    private void countHealth48Hours() {
        Intent intent = new Intent(ActivityCigMain.this, AchievmentBroadcastHealth48Hours.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ActivityCigMain.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, spCig.getLong("Health48Hours", 0) * 1000, pendingIntent);
    }

    ///wypisuje ile dni, godzin, minut i sekund minęło od dnia rzucenia palenia
    private void daysWithoutCigs() {
        long secondsPassed = secondsPassed();
        int seconds = (int) (secondsPassed) % 60 ;
        int minutes = (int) ((secondsPassed / 60) % 60);
        int hours   = (int) ((secondsPassed / (60*60)) % 24);
        int days = (int) ((secondsPassed / (60*60*24)));
        tvDaysWithoutCigs.setText(days+ " dni, "+hours+" godzin, "+minutes+" minut, "+seconds+" sekund");
    }

    ///wypisuje ile papierosów nie zostało spalonych od dnia rzucenia palenia
    private void noSmokeCigs() {
        final DecimalFormat df = new DecimalFormat("0.00");
        long secondsPassed = secondsPassed();
        int cigsSmoked = spCig.getInt("CigsSmoked", 0);
        float noSmokeCigs = (float) (cigsSmoked * (int)secondsPassed)/86400;
        String noSmokeCigsStr = df.format(noSmokeCigs);
        tvNoSmokeCigs.setText(noSmokeCigsStr);
    }

    private void savedMoney() {
        final DecimalFormat df = new DecimalFormat("0.00");
        long secondsPassed = secondsPassed();
        float cigsSmoked =(float) spCig.getInt("CigsSmoked", 0);
        float cigsInPack =(float) spCig.getInt("CigsInPack", 0);
        float packCost = spCig.getFloat("PackCost", 0);
        float savedMoney = ((cigsSmoked/cigsInPack) * packCost * (float)secondsPassed)/86400;
        String savedMoneyStr = df.format(savedMoney) + " zł";
        tvSavedMoney.setText(savedMoneyStr);
    }

    ///zwraca liczbę sekund która minęła od dnia rzucenia palenia
    private int secondsPassed() {
        int secondsPassed = (int)((System.currentTimeMillis() - (spCig.getLong("QuitDateMillis", 0) + spCig.getLong("QuitTimeMillis", 0))) / 1000);
        return secondsPassed;
    }

    ///zwraca liczbę milisekund która minęła od dnia rzucenia palenia
    private long millisecondsPassed() {
        long millisecondsPassed = (System.currentTimeMillis() - (spCig.getLong("QuitDateMillis", 0) + spCig.getLong("QuitTimeMillis", 0)));
        return millisecondsPassed;
    }

    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityCigMain.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}

