package com.sp18.ssu370.baseprojectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.text.TextUtils;
import android.net.Uri;





import com.sp18.ssu370.baseprojectapp.ui.activities.MainActivity;

public class PhoneActivity extends AppCompatActivity {
    private Button exitLockDownButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Button mDialButton = (Button) findViewById(R.id.btn_dial);
        final EditText mPhoneNoEt = (EditText) findViewById(R.id.et_phone_no);

        mDialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = mPhoneNoEt.getText().toString();
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(PhoneActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
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
}
