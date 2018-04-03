package com.sp18.ssu370.baseprojectapp.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sp18.ssu370.baseprojectapp.ChangePasswordScreen;
import com.sp18.ssu370.baseprojectapp.R;

public class MainActivity extends AppCompatActivity {
    private Button changePCButton;
    private TextView PCButtonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changePCButton = (Button) findViewById(R.id.change_pc_button);

        changePCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChangePasswordScreen.class));
            }
        });

    }
}
