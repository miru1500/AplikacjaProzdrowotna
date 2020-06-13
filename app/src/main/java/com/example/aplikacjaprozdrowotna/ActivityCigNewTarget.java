package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.aplikacjaprozdrowotna.R;

import java.util.HashSet;

public class ActivityCigNewTarget extends AppCompatActivity {

    int TargetId;

    EditText etTargets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_target);

        etTargets = findViewById(R.id.etTargets);

        Intent intent = getIntent();
        TargetId = intent.getIntExtra("TargetId", -1);

        if(TargetId != -1){
            etTargets.setText(ActivityCigTarget.targets.get(TargetId));
        }
        else{
            ActivityCigTarget.targets.add("");
            TargetId = ActivityCigTarget.targets.size()-1;
            ActivityCigTarget.arrayAdapter.notifyDataSetChanged();
        }

        etTargets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ActivityCigTarget.targets.set(TargetId, String.valueOf(s));
                ActivityCigTarget.arrayAdapter.notifyDataSetChanged();

                SharedPreferences spTargets = getApplicationContext()
                        .getSharedPreferences("Targets", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(ActivityCigTarget.targets);

                spTargets.edit().putStringSet("target", set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
