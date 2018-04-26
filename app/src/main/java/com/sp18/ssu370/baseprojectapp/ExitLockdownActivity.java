package com.sp18.ssu370.baseprojectapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sp18.ssu370.baseprojectapp.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import sqlite.DatabaseHelper;

import static android.Manifest.permission.READ_CONTACTS;

public class ExitLockdownActivity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private DatabaseHelper db;
    private Button exitLockdownButton;
    public int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_lockdown);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.code);
        db = new DatabaseHelper(this);
        db.getReadableDatabase();
        counter = 0;

        exitLockdownButton = (Button) findViewById(R.id.passCodeSubmit);
        exitLockdownButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View mEmailView) {
                Process();
            }
        });
    }

    private void Process(){
        System.out.println(counter);
        if(mEmailView.getText().toString().length() != 4) {
        } else if (passcodeMatch()) {
            counter = 0;
            changeActivity();
        } else if (counter == 3) {
            counter++;
            for (int i = 1; i < 7; i++) {
                if (db.getIsSelected(i))
                    Dialog(i);
            }
        } else if (counter == 4) {
            System.out.println("You have failed to meet security credentials. Restart phone to exit app.");
        } else
                counter++;
    }

    private void Dialog(int num){
        System.out.println("Selected question will need pop up: " + num);
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

    private void changeActivity(){
        startActivity(new Intent(ExitLockdownActivity.this, MainActivity.class));
    }
}

