package com.sp18.ssu370.baseprojectapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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


// Reset a number password only
public class UpdatePasscodeActivity extends AppCompatActivity {

    // Id to identity READ_CONTACTS permission request.
    private static final int REQUEST_READ_CONTACTS = 0;

    // A dummy authentication store containing known user names and passwords.
    // remove after connecting to a real authentication system.
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    // Keep track of the login task to ensure we can cancel it if requested.
    //private UpdatePasscodeActivity.UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mProgressView;
    private TextView Passcode;
    private DatabaseHelper db;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.code);
        Passcode = (TextView) findViewById(R.id.PasscodeDisplayUpdate);
        db = new DatabaseHelper(this);
        db.getWritableDatabase();
        //populateAutoComplete();

        resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View mEmailView) {
                if(resetPasscode()) {
                    System.out.println(db.getPasscode());
                    //final TextView P = (TextView) findViewById(R.id.PasscodeDisplay);
                    String temp = db.getPasscode();
                    Passcode.setText(temp);
                    changeActivity();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }

    private boolean resetPasscode(){
        String password = mEmailView.getText().toString();
        if(password.length() == 4) {
            int rowsAffected = db.change_passcode(db.getWritableDatabase(), password);
            Log.d("UPDATE_PASSCODE", "resetPasscode: " + rowsAffected + " row changed");
            return true;
        }
        return false;
    }

    private void changeActivity(){
        startActivity(new Intent(UpdatePasscodeActivity.this, MainActivity.class));
    }
}

