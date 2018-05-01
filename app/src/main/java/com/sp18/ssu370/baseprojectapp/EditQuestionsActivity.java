package com.sp18.ssu370.baseprojectapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
    private TextView Q;
    private TextView A;
    private Button E1, E2, E3, E4, E5, E6;
    private DatabaseHelper db;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_questions);

        db = new DatabaseHelper(this);
        db.getReadableDatabase();

        Q = findViewById(R.id.Q1);
        A = findViewById(R.id.A1);
        if(db.getAnswer(1) == null)
            A.setText(" < >");
        else
            A.setText(" " + db.getAnswer(1));
        Q.setText(" " + db.getQuestion(1));

        Q = findViewById(R.id.Q2);
        A = findViewById(R.id.A2);
        Q.setText(" " + db.getQuestion(2));
        if(db.getAnswer(2) == null)
            A.setText(" < >");
        else
            A.setText(" " + db.getAnswer(2));

        Q = findViewById(R.id.Q3);
        A = findViewById(R.id.A3);
        Q.setText(" " + db.getQuestion(3));
        if(db.getAnswer(3) == null)
            A.setText(" < >");
        else
            A.setText(" " + db.getAnswer(3));

        Q = findViewById(R.id.Q4);
        A = findViewById(R.id.A4);
        Q.setText(" " + db.getQuestion(4));
        if(db.getAnswer(4) == null)
            A.setText(" < >");
        else
            A.setText(" " + db.getAnswer(4));

        Q = findViewById(R.id.Q5);
        A = findViewById(R.id.A5);
        Q.setText(" " + db.getQuestion(5));
        if(db.getAnswer(5) == null)
            A.setText(" < >");
        else
            A.setText(" " + db.getAnswer(5));

        Q = findViewById(R.id.Q6);
        A = findViewById(R.id.A6);
        Q.setText(" " + db.getQuestion(6));
        if(db.getAnswer(6) == null)
            A.setText(" < >");
        else
            A.setText(" " + db.getAnswer(6));

        E1 = findViewById(R.id.edit1);
        E1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(1);
                boolean temp = db.getIsSelected(1);
            }
        });

        E2 = findViewById(R.id.edit2);
        E2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Dialog(2);
                boolean temp = db.getIsSelected(1);
            }
        });

        E3 = findViewById(R.id.edit3);
        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(3);
            }
        });

        E4 = findViewById(R.id.edit4);
        E4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(4);
            }
        });

        E5 = findViewById(R.id.edit5);
        E5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(5);
            }
        });

        E6 = findViewById(R.id.edit6);
        E6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(6);
            }
        });

        returnHomeButton = findViewById(R.id.return_home);
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
        final EditText Q = promptsView.findViewById(R.id.edit_question);
        final EditText A = promptsView.findViewById(R.id.edit_answer);
        Q.setText(db.getQuestion(num));
        A.setText(db.getAnswer(num));
        final CheckBox use = promptsView.findViewById(R.id.use);

        use.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    use.setChecked(true);
                } else {
                    use.setChecked(false);
                }
            }
        });
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                System.out.println("Before everything " + db.getIsSelected(num));
                                if(!Q.getText().toString().equals("") && !A.getText().toString().equals("")) {
                                    db.change_question(num, Q.getText().toString());
                                    db.change_answer(num, A.getText().toString());
                                    if(use.isChecked())
                                        db.change_selected(num, true);
                                    else
                                        db.change_selected(num, false);
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

    @Override
    public void onBackPressed() {
        //startActivity(new Intent(FailActivity.this, FailActivity.class));
    }
}
