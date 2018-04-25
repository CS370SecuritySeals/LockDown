package com.sp18.ssu370.baseprojectapp.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.sp18.ssu370.baseprojectapp.ChangePasswordScreen;
import com.sp18.ssu370.baseprojectapp.EditQuestionsActivity;
import com.sp18.ssu370.baseprojectapp.MapsActivity;
import com.sp18.ssu370.baseprojectapp.PhoneActivity;
import com.sp18.ssu370.baseprojectapp.R;

import sqlite.DatabaseHelper;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sp18.ssu370.baseprojectapp.OnSwipeTouchListener;

public class MainActivity extends AppCompatActivity {
    private static OnSwipeTouchListener onTouchListener;
    private Button changePCButton;
    private Button enterMapsButton;
    private Button enterLockDownButton;
    private TextView code;
    private DatabaseHelper db;
    final Context context = this;

    public static void setOnTouchListener(OnSwipeTouchListener onTouchListener) {
        MainActivity.onTouchListener = onTouchListener;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onTouchListener.onTouch(null, event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        db.getReadableDatabase();

        code = (TextView) findViewById(R.id.PasscodeDisplay);
        if(db.getPasscode().equals("****"))
            code.setText(db.getPasscode());
        else
            code.setText(String.format("%04d", Integer.parseInt(db.getPasscode())));

        changePCButton = (Button) findViewById(R.id.change_pc_button);
        changePCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChangePasswordScreen.class));
            }
        });

        enterMapsButton = (Button) findViewById(R.id.enter_maps_button);

        enterMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.getPasscode().equals("****"))
                    startActivity(new Intent(MainActivity.this, MapsActivity.class));
                else{
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.no_passcode_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(promptsView);
                    final TextView alert = (TextView) promptsView.findViewById(R.id.no_passcode);

                    // set question_dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        enterLockDownButton = (Button) findViewById(R.id.enterLockDown);

        enterLockDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.getPasscode().equals("****"))
                    startActivity(new Intent(MainActivity.this, PhoneActivity.class));
                else {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.no_passcode_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(promptsView);
                    final TextView alert = (TextView) promptsView.findViewById(R.id.no_passcode);

                    // set question_dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
