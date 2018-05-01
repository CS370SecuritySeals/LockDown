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

import com.sp18.ssu370.baseprojectapp.ui.activities.MainActivity;

import java.util.Random;

import sqlite.DatabaseHelper;

public class ExitLockdownActivity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private DatabaseHelper db;
    private Button exitLockdownButton;
    public int counter;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exit_lockdown);

        // Set up the login form.
        mEmailView = findViewById(R.id.code);
        db = new DatabaseHelper(this);
        db.getReadableDatabase();
        counter = 0;

        exitLockdownButton = findViewById(R.id.passCodeSubmit);
        exitLockdownButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View mEmailView) {
                Process();
            }
        });
    }

    private void Process() {
        System.out.println(counter);
        if (mEmailView.getText().toString().length() != 4) {
        } else if (passcodeMatch()) {
            counter = 0;
            getQuestion();
        } else if (counter == 3) {
            counter = 0;
            failDialog();
        } counter++;
        hideSoftKeyboard();
    }

    private void getQuestion(){
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
            Dialog(n);
        }
    }

    private boolean passcodeMatch() {
        String password = mEmailView.getText().toString();
        String current = String.format("%04d", Integer.parseInt(db.getPasscode()));
        if (password.length() != 4) {
        } else
            if (password.equals(current))
                return true;
        return false;
    }

    private void failDialog(){
        startActivity(new Intent(ExitLockdownActivity.this, FailActivity.class));
    }

    private void changeActivity(){
        startActivity(new Intent(ExitLockdownActivity.this, MainActivity.class));
    }

    public void Dialog(final int num) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.answer_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final TextView Q = promptsView.findViewById(R.id.exit_question);
        final EditText A = promptsView.findViewById(R.id.exit_answer);
        Q.setText(db.getQuestion(num));
        A.setText("");

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println(A.getText().toString());
                                System.out.println(db.getAnswer(num));
                                if (A.getText().toString().equals(db.getAnswer(num)))
                                    changeActivity();
                                else {
                                    failDialog();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
            //Intent nextFlow = new Intent(this, AppActionActivity.class);
            //Intent nextFlow = new Intent(this, AppActionDropDownActivity.class);
            //startActivity(nextFlow);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(FailActivity.this, FailActivity.class));
    }
}