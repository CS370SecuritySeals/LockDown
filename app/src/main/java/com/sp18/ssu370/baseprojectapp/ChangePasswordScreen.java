package com.sp18.ssu370.baseprojectapp;
import com.sp18.ssu370.baseprojectapp.ui.activities.MainActivity;
import sqlite.DatabaseHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.sp18.ssu370.baseprojectapp.R;

public class ChangePasswordScreen extends AppCompatActivity {
    private Button returnHomeButton;
    private Button tempSettingsButton;
    private Button changePCButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);

        changePCButton = (Button) findViewById(R.id.change_pc_button);
        changePCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordScreen.this, UpdatePasscodeActivity.class));
            }
        });

        returnHomeButton = (Button) findViewById(R.id.return_home);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordScreen.this, MainActivity.class));
            }
        });


        tempSettingsButton = (Button) findViewById(R.id.settings);
        tempSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordScreen.this, QuestionActivity.class));
            }
        });
    }
}
