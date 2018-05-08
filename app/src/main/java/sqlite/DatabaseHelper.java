// K
package sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import sqlite.model.Passcodes;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version (may not be necessary)
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "passcodes_db";

    private SQLiteDatabase mDefaultWritableDatabase = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // CREATING TABLE WITH INITIAL QUESTIONS
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create passcodes table
        try {
                db.execSQL(Passcodes.CREATE_TABLE);
        }
        catch (Exception e) {
            System.out.println("Database Creation Error: " + e);
        }
        try {
            addRows(db);
        }
        catch (Exception e) {
            System.out.println("Addrows in OnCreate Error: " + e);
        }
    }

    private void addRows(SQLiteDatabase db){
        // populate it with initial 6 security questions
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();
        ContentValues values5 = new ContentValues();
        ContentValues values6 = new ContentValues();

        // passcode & answer should be empty
        // id auto-increments & doesn't need to be handled
        values1.put(Passcodes.COLUMN_QUESTION, "What was your first car?");
        values1.put(Passcodes.COLUMN_IS_SELECTED, false);
        values1.put(Passcodes.COLUMN_PASSCODE, "****");
        values2.put(Passcodes.COLUMN_QUESTION, "Where do you vacation?");
        values2.put(Passcodes.COLUMN_IS_SELECTED, false);
        values2.put(Passcodes.COLUMN_PASSCODE, "0");
        values3.put(Passcodes.COLUMN_QUESTION, "What is your favorite food?");
        values3.put(Passcodes.COLUMN_IS_SELECTED, false);
        values4.put(Passcodes.COLUMN_QUESTION, "What team do you root for?");
        values4.put(Passcodes.COLUMN_IS_SELECTED, false);
        values5.put(Passcodes.COLUMN_QUESTION, "What is your spirit animal?");
        values5.put(Passcodes.COLUMN_IS_SELECTED, false);
        values6.put(Passcodes.COLUMN_QUESTION, "What is your favorite show?");
        values6.put(Passcodes.COLUMN_IS_SELECTED, false);

        // insert rows
        try{
            Log.d("Addrows", "Added row:" + db.insert(Passcodes.TABLE_NAME, null, values1));
            db.insert(Passcodes.TABLE_NAME, null, values2);
            db.insert(Passcodes.TABLE_NAME, null, values3);
            db.insert(Passcodes.TABLE_NAME, null, values4);
            db.insert(Passcodes.TABLE_NAME, null, values5);
            db.insert(Passcodes.TABLE_NAME, null, values6);
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    // UPGRADING DATABASE (may not be necessary)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        this.mDefaultWritableDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS " + Passcodes.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase db;
        if(mDefaultWritableDatabase != null){
            db = mDefaultWritableDatabase;
        } else {
            db = super.getWritableDatabase();
        }
        return db;
    }

    // RETURNS PASSCODE STORED IN PASSCODE COLUMN ROW 1
    public String getPasscode() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Passcodes.TABLE_NAME,
                new String[]{Passcodes.COLUMN_PASSCODE},
                Passcodes.COLUMN_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // store value of passcode
        String temp = cursor.getString(cursor.getColumnIndex(Passcodes.COLUMN_PASSCODE));

        // close the db connection
        cursor.close();

        return temp;
    }

    // RETURNS PASSCODE STORED IN PASSCODE COLUMN ROW 1
    public String getQuestionNumber() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Passcodes.TABLE_NAME,
                new String[]{Passcodes.COLUMN_PASSCODE},
                Passcodes.COLUMN_ID + "=?",
                new String[]{String.valueOf(2)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // store value of passcode
        String temp = cursor.getString(cursor.getColumnIndex(Passcodes.COLUMN_PASSCODE));

        // close the db connection
        cursor.close();

        return temp;
    }

    // RETURNS PASSCODE STORED IN PASSCODE COLUMN ROW 1
    public String getQuestion(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Passcodes.TABLE_NAME,
                new String[]{Passcodes.COLUMN_QUESTION},
                Passcodes.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // store value of passcode
        String temp = cursor.getString(cursor.getColumnIndex(Passcodes.COLUMN_QUESTION));

        // close the db connection
        cursor.close();

        return temp;
    }

    // RETURNS PASSCODE STORED IN PASSCODE COLUMN ROW 1
    public String getAnswer(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Passcodes.TABLE_NAME,
                new String[]{Passcodes.COLUMN_ANSWER},
                Passcodes.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // store value of passcode
        String temp = cursor.getString(cursor.getColumnIndex(Passcodes.COLUMN_ANSWER));

        // close the db connection
        cursor.close();

        return temp;
    }

    // passcode setter
    public int change_passcode(SQLiteDatabase temp, String entry){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Passcodes.COLUMN_PASSCODE, entry);

        // updating row
        return db.update(Passcodes.TABLE_NAME, values, Passcodes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(1)});
    }

    // passcode setter
    public int setQuestionNumber(String entry){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Passcodes.COLUMN_PASSCODE, entry);

        // updating row
        return db.update(Passcodes.TABLE_NAME, values, Passcodes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(2)});
    }

    // RETURNS PASSCODE STORED IN PASSCODE COLUMN ROW 1
    public Boolean getIsSelected(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Passcodes.TABLE_NAME,
                new String[]{Passcodes.COLUMN_IS_SELECTED},
                Passcodes.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Boolean temp = (cursor.getString(cursor.getColumnIndex(Passcodes.COLUMN_IS_SELECTED)).equals("1"));
        //System.out.println("FROM getIsSelected " + temp);
        // close the db connection
        cursor.close();

        return temp;
    }

    // passcode setter
    public int change_selected(int num, boolean entry){
        //System.out.println("going INTo change selected... " + entry);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Passcodes.COLUMN_IS_SELECTED, entry);

        // updating row
        return db.update(Passcodes.TABLE_NAME, values, Passcodes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(num)});
    }

    // question setter
    public int change_question(int questionId, String newQuestion){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Passcodes.COLUMN_QUESTION, newQuestion);

        // updating row
        return db.update(Passcodes.TABLE_NAME, values, Passcodes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(questionId)});
    }

    // answer setter
    public int change_answer(int questionId, String newAnswer){
        // get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Passcodes.COLUMN_ANSWER, newAnswer);

        // updating row
        return db.update(Passcodes.TABLE_NAME, values, Passcodes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(questionId)});
    }

    public List<Passcodes> getAll() {
        List<Passcodes> passcodes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Passcodes.TABLE_NAME + " ORDER BY " +
                Passcodes.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Passcodes passcode = new Passcodes();
                passcode.setId(cursor.getInt(cursor.getColumnIndex(Passcodes.COLUMN_ID)));
                passcode.setAnswer(cursor.getString(cursor.getColumnIndex(Passcodes.COLUMN_ANSWER)));
                passcode.setQuestion(cursor.getString(cursor.getColumnIndex(Passcodes.COLUMN_QUESTION)));

                passcodes.add(passcode);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // close db connection
        db.close();

        // return notes list
        return passcodes;
    }
}