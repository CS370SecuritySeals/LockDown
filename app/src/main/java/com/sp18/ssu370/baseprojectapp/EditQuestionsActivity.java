package com.sp18.ssu370.baseprojectapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sp18.ssu370.baseprojectapp.ui.activities.MainActivity;

import sqlite.DatabaseHelper;

public class EditQuestionsActivity extends AppCompatActivity {
    private Button returnHomeButton;
    private TextView Q1, Q2, Q3, Q4, Q5, Q6;
    private TextView A1, A2, A3, A4, A5, A6;
    private TextView L1, L2, L3, L4, L5;
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

        L1 = (TextView) findViewById(R.id.L1);
        L2 = (TextView) findViewById(R.id.L2);
        L3 = (TextView) findViewById(R.id.L3);
        L4 = (TextView) findViewById(R.id.L4);
        L5 = (TextView) findViewById(R.id.L5);

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
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.question_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.edit_question);
                final EditText Q = (EditText)promptsView.findViewById(R.id.edit_question);
                final EditText A = (EditText)promptsView.findViewById(R.id.edit_answer);
                Q.setText(db.getQuestion(1));
                A.setText(db.getAnswer(1));

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        System.out.println(Q.getText().toString());
                                        System.out.println(A.getText().toString());
                                        if(!Q.getText().toString().equals("") && !A.getText().toString().equals("")) {
                                            db.change_question(1, Q.getText().toString());
                                            db.change_answer(1, A.getText().toString());
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
        });

        E2 = (Button) findViewById(R.id.edit2);
        E2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.question_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.edit_question);
                final EditText Q = (EditText)promptsView.findViewById(R.id.edit_question);
                final EditText A = (EditText)promptsView.findViewById(R.id.edit_answer);
                Q.setText(db.getQuestion(2));
                A.setText(db.getAnswer(2));

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        System.out.println(Q.getText().toString());
                                        System.out.println(A.getText().toString());
                                        if(!Q.getText().toString().equals("") && !A.getText().toString().equals("")) {
                                            db.change_question(2, Q.getText().toString());
                                            db.change_answer(2, A.getText().toString());
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
        });

        E3 = (Button) findViewById(R.id.edit3);
        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.question_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.edit_question);
                final EditText Q = (EditText)promptsView.findViewById(R.id.edit_question);
                final EditText A = (EditText)promptsView.findViewById(R.id.edit_answer);
                Q.setText(db.getQuestion(3));
                A.setText(db.getAnswer(3));

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        System.out.println(Q.getText().toString());
                                        System.out.println(A.getText().toString());
                                        if(!Q.getText().toString().equals("") && !A.getText().toString().equals("")) {
                                            db.change_question(3, Q.getText().toString());
                                            db.change_answer(3, A.getText().toString());
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
        });

        E4 = (Button) findViewById(R.id.edit4);
        E4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.question_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.edit_question);
                final EditText Q = (EditText)promptsView.findViewById(R.id.edit_question);
                final EditText A = (EditText)promptsView.findViewById(R.id.edit_answer);
                Q.setText(db.getQuestion(4));
                A.setText(db.getAnswer(4));

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                System.out.println(Q.getText().toString());
                                System.out.println(A.getText().toString());
                                if(!Q.getText().toString().equals("") && !A.getText().toString().equals("")) {
                                    db.change_question(4, Q.getText().toString());
                                    db.change_answer(4, A.getText().toString());
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
        });

        E5 = (Button) findViewById(R.id.edit5);
        E5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.question_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.edit_question);
                final EditText Q = (EditText)promptsView.findViewById(R.id.edit_question);
                final EditText A = (EditText)promptsView.findViewById(R.id.edit_answer);
                Q.setText(db.getQuestion(5));
                A.setText(db.getAnswer(5));

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                System.out.println(Q.getText().toString());
                                System.out.println(A.getText().toString());
                                if(!Q.getText().toString().equals("") && !A.getText().toString().equals("")) {
                                    db.change_question(5, Q.getText().toString());
                                    db.change_answer(5, A.getText().toString());
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
        });

        E6 = (Button) findViewById(R.id.edit6);
        E6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.question_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.edit_question);
                final EditText Q = (EditText)promptsView.findViewById(R.id.edit_question);
                final EditText A = (EditText)promptsView.findViewById(R.id.edit_answer);
                Q.setText(db.getQuestion(6));
                A.setText(db.getAnswer(6));

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        System.out.println(Q.getText().toString());
                                        System.out.println(A.getText().toString());
                                        if(!Q.getText().toString().equals("") && !A.getText().toString().equals("")) {
                                            db.change_question(6, Q.getText().toString());
                                            db.change_answer(6, A.getText().toString());
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
        });

        returnHomeButton = (Button) findViewById(R.id.return_home);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditQuestionsActivity.this, ChangePasswordScreen.class));
            }
        });
    }
}
