package com.example.aplikacjaprozdrowotna;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplikacjaprozdrowotna.R;

import java.util.ArrayList;
import java.util.HashSet;

public class ActivityCigTarget extends AppCompatActivity {

    static ArrayList<String> targets = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    Button btnNewTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        final ListView lvTargets = (ListView) findViewById(R.id.lvTargets);
        btnNewTarget = findViewById(R.id.btnNewTarget);

        btnNewTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityCigNewTarget.class);
                startActivity(intent);
            }
        });

        SharedPreferences spTargets = getApplicationContext()
                .getSharedPreferences("Targets", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) spTargets.getStringSet("target", null);

        if(set == null){
            targets.add("Przykładowy cel - adidaski");

        }
        else{
            targets = new ArrayList(set);
        }
        arrayAdapter =
                new ArrayAdapter(this,android.R.layout.simple_list_item_1, targets);

        lvTargets.setAdapter(arrayAdapter);

        lvTargets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ActivityCigNewTarget.class);
                intent.putExtra("TargetId", position);
                startActivity(intent);

            }
        });

        lvTargets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(ActivityCigTarget.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Odhacz cel")
                        .setMessage("Czy napewno chcesz odhaczyć cel?")
                        .setPositiveButton("Tak :)", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        targets.remove(position);
                                        arrayAdapter.notifyDataSetChanged();

                                        SharedPreferences sharedPreferences = getApplicationContext()
                                                .getSharedPreferences("Targets", Context.MODE_PRIVATE);

                                        HashSet<String> set = new HashSet<>(ActivityCigTarget.targets);

                                        sharedPreferences.edit().putStringSet("target", set).apply();

                                        Toast.makeText(getApplicationContext(),"Odhaczono cel", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).setNegativeButton("Nie :(", null)
                        .show();
                return true;
            }

        });
    }

    //powrót przyciskiem "back"
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityCigTarget.this, ActivityCigMain.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
