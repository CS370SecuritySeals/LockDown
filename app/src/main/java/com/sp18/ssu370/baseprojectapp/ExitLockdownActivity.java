package com.sp18.ssu370.baseprojectapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sp18.ssu370.baseprojectapp.ui.activities.MainActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import sqlite.DatabaseHelper;

public class ExitLockdownActivity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private DatabaseHelper db;
    private Button exitLockdownButton;
    public int counter;
    private WindowManager wm;
    private ViewGroup viewG;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_exit_lockdown);

        final Context context = getApplicationContext();
        final int windowType;
        if (Build.VERSION.SDK_INT>=26)
            windowType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        else
            windowType = WindowManager.LayoutParams.TYPE_TOAST;
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                windowType,
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
                PixelFormat.TRANSLUCENT
        );
        wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        viewG = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_exit_lockdown, null);
        getWindow().setAttributes(params);
        //Set up your views here
        wm.addView(viewG, params);

        // Set up the login form.
        mEmailView = viewG.findViewById(R.id.code);
        db = new DatabaseHelper(this);
        db.getReadableDatabase();
        counter = 0;

        exitLockdownButton = viewG.findViewById(R.id.passCodeSubmit);
        exitLockdownButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View mEmailView) {
                ProcessInput();
            }
        });
    }

    private void ProcessInput() {
        if (mEmailView.getText().toString().length() != 4) {
        } else if (doesPasscodeMatch()) {
            counter = 0;
            getQuestionIfAny();
        } else if (counter == 3) {
            counter = 0;
            failDialog();
        } counter++;
        hideSoftKeyboard();
    }

    private void getQuestionIfAny(){
        // check for no selected questions state
        // reset counter & exit if none selectedboolean gotQuestion = false;
        boolean gotQuestion = false;
        for (int i = 1; i < 7; i++)
            if (db.getIsSelected(i))
                gotQuestion = true;
        if (!gotQuestion) {
            counter = 0;
            changeActivity();
        }
        else {
            // choose a random question to answer
            // reset counter & enter answer question dialog
            gotQuestion = false;
            int n = 1;
            while (!gotQuestion) {
                Random rand = new Random();
                n = rand.nextInt(6) + 1;
                if (db.getIsSelected(n))
                    gotQuestion = true;
            }
            counter = 0;
            db.setQuestionNumber(Integer.toString(n));
            questionActivity();
        }
    }

    private boolean doesPasscodeMatch() {
        String password = mEmailView.getText().toString();
        String current = String.format("%04d", Integer.parseInt(db.getPasscode()));
        if (password.length() != 4) {
        } else
            if (password.equals(current))
                return true;
        Toast.makeText(this, "Incorrect passcode...", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void failDialog(){
        startActivity(new Intent(ExitLockdownActivity.this, FailActivity.class));
        Toast.makeText(context, "Locking device...", Toast.LENGTH_SHORT).show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 4 seconds
                wm.removeView(viewG);
            }
        }, 4000);
    }

    private void changeActivity(){
        startActivity(new Intent(ExitLockdownActivity.this, MainActivity.class));
        Toast.makeText(context, "Loading homescreen...", Toast.LENGTH_SHORT).show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 4 seconds
                wm.removeView(viewG);
            }
        }, 4000);
    }

    private void questionActivity(){
        startActivity(new Intent(ExitLockdownActivity.this, ExitQuestionActivity.class));
        Toast.makeText(context, "Loading security question...", Toast.LENGTH_SHORT).show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 4 seconds
                wm.removeView(viewG);
            }
        }, 4000);
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() { }
}