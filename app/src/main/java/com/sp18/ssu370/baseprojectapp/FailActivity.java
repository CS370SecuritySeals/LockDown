package com.sp18.ssu370.baseprojectapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fail_state);

    }

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(FailActivity.this, FailActivity.class));
    }
}
