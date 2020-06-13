package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aplikacjaprozdrowotna.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActivityCigStartInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button btnQuitDate, btnQuitTime, btnDone;
    TextView tvQuitDate, tvQuitTime;
    EditText etCigsSmoked, etCigsInPack, etPackCost;

    private SharedPreferences spCig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cig_start_info);

        ///utworzenie zmiennej z kluczem CigStart, pod którym mają być zapisywane dane
        spCig = ActivityCigStartInfo.this.getSharedPreferences("CigStartInfo", MODE_PRIVATE);

        btnQuitDate = findViewById(R.id.btnQuitDate);
        btnQuitTime = findViewById(R.id.btnQuitTime);
        btnDone = findViewById(R.id.btnDone);
        tvQuitDate = findViewById(R.id.tvQuitDate);
        tvQuitTime = findViewById(R.id.tvQuitTime);
        etCigsSmoked = findViewById(R.id.etCigsSmoked);
        etCigsInPack = findViewById(R.id.etCigsInPack);
        etPackCost = findViewById(R.id.etPackCost);

        ///ustawienie wartości IsPopup na false dzięki czemu w aktywności z papierosami pojawi się motywujący tekst
        spCig.edit().putBoolean("IsPopup", false).commit();

        btnQuitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnQuitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCigsSmoked.getText().toString().isEmpty() ||
                        etCigsInPack.getText().toString().isEmpty() ||
                        etPackCost.getText().toString().isEmpty() ||
                        spCig.getBoolean("DateChosen", false) != true ||
                        spCig.getBoolean("TimeChosen", false) != true){
                    Toast.makeText(ActivityCigStartInfo.this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                }
                else {
                    int cigsSmoked = Integer.parseInt(etCigsSmoked.getText().toString().trim());
                    spCig.edit().putInt("CigsSmoked", cigsSmoked).commit();
                    int cigsInPack = Integer.parseInt(etCigsInPack.getText().toString().trim());
                    spCig.edit().putInt("CigsInPack", cigsInPack).commit();
                    float packCost = Float.parseFloat(etPackCost.getText().toString().trim());
                    spCig.edit().putFloat("PackCost", packCost).commit();
                    spCig.edit().putBoolean("IsDone", true).commit();

                    //ustawienie flag na wartość false
                    spCig.edit().putBoolean("IsTimeAchievmentCounted", false).commit();
                    spCig.edit().putBoolean("IsCounted", false).commit();

                    notificationStart("Gratulacje!", "Podjąłeś decyzję, żeby rzucić palenie!");

                    Intent intent = new Intent(ActivityCigStartInfo.this, ActivityCigMain.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void notificationStart(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ActivityCigStartInfo.this)
                .setSmallIcon(R.drawable.happy)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);
        Intent intent = new Intent(ActivityCigStartInfo.this, ActivityCigStartInfo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        PendingIntent pendingIntent = PendingIntent.getActivity(ActivityCigStartInfo.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

            ///instrukcje warunkowe, żeby przed jednocyfrowymi dniami i miesiącami było wstawiane 0
            String yearStr = Integer.toString(year);
            String monthStr;
            String dayStr;
            if (month < 9)
                monthStr = "0" + (month + 1);
            else
                monthStr = Integer.toString(month + 1);
            if (day < 10)
                dayStr = "0" + day;
            else
                dayStr = Integer.toString(day);

            String quitDateStr = dayStr + "." + monthStr + "." + yearStr;
            tvQuitDate.setText(quitDateStr);

            ///parsowanie i konwersja daty na milisekundy
            Date quitDate = sdf.parse(quitDateStr);
            long quitDateMillis =  TimeUnit.MILLISECONDS.convert(quitDate.getTime(), TimeUnit.MILLISECONDS);

            ///umieszczenie wartości millis w kluczu QuitDateMillis
            spCig.edit().putLong("QuitDateMillis", quitDateMillis).commit();

            ///data została wybrana, wartość TextView w kluczu DateChosen ustawiamy na true
            spCig.edit().putBoolean("DateChosen", true).commit();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        ///instrukcje warunkowe żeby przed godzinami i minutami mniejszymi od 10 było wstawiane 0
        String hourStr;
        String minuteStr;
        if(hour < 10)
            hourStr = "0" + hour;
        else
            hourStr = Integer.toString(hour);
        if(minute < 10)
            minuteStr = "0" + minute;
        else
            minuteStr = Integer.toString(minute);

        String quitTimeStr = hourStr  + ":" + minuteStr;
        tvQuitTime.setText(quitTimeStr);

        ///przeliczanie wybranej godziny na milisekundy
        long millis = hour * 60 * 60 * 1000 + minute * 60 * 1000;

        ///godzina została wybrana, wartość TextView w kluczu TimeChosen ustawiamy na true
        spCig.edit().putBoolean("TimeChosen", true).commit();

        ///umieszczenie wartości millis w kluczu QuitTimeMillis
        spCig.edit().putLong("QuitTimeMillis", millis).commit();
    }

    ///powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityCigStartInfo.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}

