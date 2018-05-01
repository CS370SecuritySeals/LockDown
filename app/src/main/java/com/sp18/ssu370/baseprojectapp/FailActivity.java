package com.sp18.ssu370.baseprojectapp;


import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class FailActivity extends AppCompatActivity {
    private Window window;
    private WindowManager wmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        setContentView(R.layout.fail_state);

        Context context = getApplicationContext();
        int windowType;
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
        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        ViewGroup mTopView = (ViewGroup) getLayoutInflater().inflate(R.layout.fail_state, null);
        getWindow().setAttributes(params);
        //Set up your views here
         //View testView = mTopView.findViewById(R.id.fai);
        wm.addView(mTopView, params);
    }

    //@Override
    //public void onBackPressed() {
    //    //startActivity(new Intent(FailActivity.this, FailActivity.class));
    //}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            Log.i("TAG", "Press Home");
            System.exit(0);
            startActivity(new Intent(FailActivity.this, FailActivity.class));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Intent nextFlow = new Intent(this, AppActionActivity.class);
            //Intent nextFlow = new Intent(this, AppActionDropDownActivity.class);
            //startActivity(nextFlow);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        startActivity(new Intent(FailActivity.this, FailActivity.class));
    }
}
