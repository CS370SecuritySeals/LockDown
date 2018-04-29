package com.sp18.ssu370.baseprojectapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.sp18.ssu370.baseprojectapp.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sqlite.DatabaseHelper;

import static android.Manifest.permission.READ_CONTACTS;

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

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(FailActivity.this, FailActivity.class));
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
}