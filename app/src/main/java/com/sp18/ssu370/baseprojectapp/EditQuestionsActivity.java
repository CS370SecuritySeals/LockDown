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

        Q = (TextView) findViewById(R.id.Q1);
        A = (TextView) findViewById(R.id.A1);
        A.setText(" " + db.getAnswer(1));
        Q.setText(" " + db.getQuestion(1));

        Q = (TextView) findViewById(R.id.Q2);
        A = (TextView) findViewById(R.id.A2);
        Q.setText(" " + db.getQuestion(2));
        A.setText(" " + db.getAnswer(2));

        Q = (TextView) findViewById(R.id.Q3);
        A = (TextView) findViewById(R.id.A3);
        Q.setText(" " + db.getQuestion(3));
        A.setText(" " + db.getAnswer(3));

        Q = (TextView) findViewById(R.id.Q4);
        A = (TextView) findViewById(R.id.A4);
        Q.setText(" " + db.getQuestion(4));
        A.setText(" " + db.getAnswer(4));

        Q = (TextView) findViewById(R.id.Q5);
        A = (TextView) findViewById(R.id.A5);
        Q.setText(" " + db.getQuestion(5));
        A.setText(" " + db.getAnswer(5));

        Q = (TextView) findViewById(R.id.Q6);
        A = (TextView) findViewById(R.id.A6);
        Q.setText(" " + db.getQuestion(6));
        A.setText(" " + db.getAnswer(6));

        E1 = (Button) findViewById(R.id.edit1);
        E1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog(1);
                boolean temp = db.getIsSelected(1);
            }
        });

        E2 = (Button) findViewById(R.id.edit2);
        E2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Dialog(2);
                boolean temp = db.getIsSelected(1);
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
        final CheckBox use = (CheckBox) promptsView.findViewById(R.id.use);

        use.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    use.setChecked(true);
                    //db.change_selected(num, 1);
                    //int rowsAffected = db.change_selected(num, true);
                    //Log.d("IS_SELECTED", "change made: " + rowsAffected + " row changed");
                } else {
                    use.setChecked(false);
                    //db.change_selected(num, 0);
                    //int rowsAffected = db.change_selected(num, false);
                    //Log.d("IS_SELECTED", "change made: " + rowsAffected + " row changed");
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
}
