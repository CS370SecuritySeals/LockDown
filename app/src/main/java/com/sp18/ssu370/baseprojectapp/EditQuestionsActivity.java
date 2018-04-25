package com.sp18.ssu370.baseprojectapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import sqlite.DatabaseHelper;

public class EditQuestionsActivity extends AppCompatActivity {
    private Button returnHomeButton;
    private TextView Q1, Q2, Q3, Q4, Q5, Q6;
    private TextView A1, A2, A3, A4, A5, A6;
    private Button E1, E2, E3, E4, E5, E6;
    private DatabaseHelper db;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_questions);
        EditText Q = (EditText)findViewById(R.id.edit_question);
        EditText A = (EditText)findViewById(R.id.edit_answer);

        db = new DatabaseHelper(this);
        db.getReadableDatabase();

        Q1 = (TextView) findViewById(R.id.Q1);
        A1 = (TextView) findViewById(R.id.A1);
        A1.setText(" " + db.getAnswer(1));
        Q1.setText(" " + db.getQuestion(1));

        Q2 = (TextView) findViewById(R.id.Q2);
        A2 = (TextView) findViewById(R.id.A2);
        Q2.setText(" " + db.getQuestion(2));
        A2.setText(" " + db.getAnswer(2));

        Q3 = (TextView) findViewById(R.id.Q3);
        A3 = (TextView) findViewById(R.id.A3);
        Q3.setText(" " + db.getQuestion(3));
        A3.setText(" " + db.getAnswer(3));

        Q4 = (TextView) findViewById(R.id.Q4);
        A4 = (TextView) findViewById(R.id.A4);
        Q4.setText(" " + db.getQuestion(4));
        A4.setText(" " + db.getAnswer(4));

        Q5 = (TextView) findViewById(R.id.Q5);
        A5 = (TextView) findViewById(R.id.A5);
        Q5.setText(" " + db.getQuestion(5));
        A5.setText(" " + db.getAnswer(5));

        Q6 = (TextView) findViewById(R.id.Q6);
        A6 = (TextView) findViewById(R.id.A6);
        Q6.setText(" " + db.getQuestion(6));
        A6.setText(" " + db.getAnswer(6));

        E1 = (Button) findViewById(R.id.edit1);
        E1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(1);
            }
        });

        E2 = (Button) findViewById(R.id.edit2);
        E2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Dialog(2);
            }
        });

        E3 = (Button) findViewById(R.id.edit3);
        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(3);
            }
        });

        E4 = (Button) findViewById(R.id.edit4);
        E4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(4);
            }
        });

        E5 = (Button) findViewById(R.id.edit5);
        E5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(5);
            }
        });

        E6 = (Button) findViewById(R.id.edit6);
        E6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(6);
            }
        });

        returnHomeButton = (Button) findViewById(R.id.return_home);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditQuestionsActivity.this, ChangePasswordScreen.class));
            }
        });
    }

    public void Dialog(final int num){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.question_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.edit_question);
        final EditText Q = (EditText)promptsView.findViewById(R.id.edit_question);
        final EditText A = (EditText)promptsView.findViewById(R.id.edit_answer);
        Q.setText(db.getQuestion(num));
        A.setText(db.getAnswer(num));
        CheckBox use = (CheckBox) promptsView.findViewById(R.id.use);

        use.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                System.out.println(Q.getText().toString());
                                System.out.println(A.getText().toString());
                                if(!Q.getText().toString().equals("") && !A.getText().toString().equals("")) {
                                    db.change_question(num, Q.getText().toString());
                                    db.change_answer(num, A.getText().toString());
                                    startActivity(new Intent(EditQuestionsActivity.this, EditQuestionsActivity.class));
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
