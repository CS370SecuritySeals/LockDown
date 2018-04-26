package com.sp18.ssu370.baseprojectapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PhoneActivity extends AppCompatActivity {
    private Button exitLockDownButton;
    private TelephonyManager mTelephonyManager;
    private OnSwipeTouchListener onTouchListener;

    public void setOnTouchListener(OnSwipeTouchListener onTouchListener) {
        onTouchListener = onTouchListener;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(PhoneActivity.this, ExitLockdownActivity.class));
    }

//    public void onHomePressed() {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

    public void onHomeLongPressed() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onTouchListener.onTouch(null, event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            Toast.makeText(this, "You pressed the home button!", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Button mDialButton = (Button) findViewById(R.id.btn_dial);
        final EditText mPhoneNoEt = (EditText) findViewById(R.id.et_phone_no);

        mDialButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                String phoneNo = mPhoneNoEt.getText().toString();
                if (!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(PhoneActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setOnTouchListener(new OnSwipeTouchListener(PhoneActivity.this) {
            public void onSwipeUp() {
                Toast.makeText(PhoneActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(PhoneActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(PhoneActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeDown() {
                Toast.makeText(PhoneActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

        exitLockDownButton = (Button) findViewById(R.id.exit_lockdown_button);

        exitLockDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhoneActivity.this, ExitLockdownActivity.class));
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, ExitLockdownActivity.class);

        }

}


