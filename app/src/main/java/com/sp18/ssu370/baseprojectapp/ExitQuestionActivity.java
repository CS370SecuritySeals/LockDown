package com.sp18.ssu370.baseprojectapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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

public class ExitQuestionActivity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mEmailView;
    private TextView question;
    private DatabaseHelper db;
    private Button exitLockdownButton;
    public int counter;
    private int questionNumber;
    private WindowManager wm;
    private ViewGroup viewG;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        viewG = (ViewGroup) getLayoutInflater().inflate(R.layout.exit_question, null);
        getWindow().setAttributes(params);
        //Set up your views here
        wm.addView(viewG, params);

        // Set up the login form.
        mEmailView = viewG.findViewById(R.id.exit_answer_value);
        db = new DatabaseHelper(this);
        db.getReadableDatabase();
        counter = 0;

        questionNumber = Integer.valueOf(db.getQuestionNumber());
        question = viewG.findViewById(R.id.exit_question);
        question.setText(db.getQuestion(questionNumber));

        exitLockdownButton = viewG.findViewById(R.id.answerSubmit);
        exitLockdownButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View mEmailView) {
                ProcessInput();
            }
        });
    }

    private void ProcessInput() {
        System.out.println(counter);
        if (mEmailView.getText().toString().length() == 0) {
        } else if (doesAnswerMatch()) {
            counter = 0;
            changeActivity();
        } else if (counter == 3) {
            counter = 0;
            failDialog();
        } counter++;
        hideSoftKeyboard();
    }

    private boolean doesAnswerMatch() {
        String answer = mEmailView.getText().toString();
        String current = db.getAnswer(questionNumber);
        if (answer.length() == 0) {
        } else
            if (answer.equals(current))
                return true;
        Toast.makeText(this, "Incorrect answer...", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void failDialog(){
        startActivity(new Intent(ExitQuestionActivity.this, FailActivity.class));
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
        startActivity(new Intent(ExitQuestionActivity.this, MainActivity.class));
        Toast.makeText(context, "Loading homescreen...", Toast.LENGTH_SHORT).show();
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