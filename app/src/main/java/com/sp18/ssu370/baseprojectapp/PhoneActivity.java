package com.sp18.ssu370.baseprojectapp;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneActivity extends AppCompatActivity {
    private Button exitLockDownButton;
    private TelephonyManager mTelephonyManager;
    private OnSwipeTouchListener onTouchListener;
    private WindowManager wm;
    private ViewGroup viewG;

    public void setOnTouchListener(OnSwipeTouchListener onTouchListener) {
        onTouchListener = onTouchListener;
    }

    public void onHomeLongPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_phone);

        final Context context = getApplicationContext();
        final int windowType;
        if (Build.VERSION.SDK_INT>=26) windowType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        else windowType = WindowManager.LayoutParams.TYPE_TOAST;
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
        viewG = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_phone, null);
        getWindow().setAttributes(params);
        //Set up your views here
        wm.addView(viewG, params);

        Button mDialButton = viewG.findViewById(R.id.btn_dial);
        final EditText mPhoneNoEt = viewG.findViewById(R.id.et_phone_no);

        mDialButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                String phoneNo = mPhoneNoEt.getText().toString();
                if (!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    hideSoftKeyboard();
                } else {
                    Toast.makeText(PhoneActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exitLockDownButton = viewG.findViewById(R.id.exit_lockdown_button);

        exitLockDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(viewG);
                startActivity(new Intent(PhoneActivity.this, ExitLockdownActivity.class));
            }

        });
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

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

//   setOnTouchListener(new OnSwipeTouchListener(PhoneActivity.this) {
//            public void onSwipeUp() {
//                Toast.makeText(PhoneActivity.this, "top", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeRight() {
//                Toast.makeText(PhoneActivity.this, "right", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeLeft() {
//                Toast.makeText(PhoneActivity.this, "left", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeDown() {
//                Toast.makeText(PhoneActivity.this, "bottom", Toast.LENGTH_SHORT).show();
//            }
//        });

//   // @Override
//   // protected void onStop() {
//        super.onStop();
//        wm.removeView(viewG);
//        Intent intent = new Intent(this, ExitLockdownActivity.class);
//        }

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //onTouchListener.onTouch(null, event);
//        return super.onTouchEvent(event);
//    }

}
