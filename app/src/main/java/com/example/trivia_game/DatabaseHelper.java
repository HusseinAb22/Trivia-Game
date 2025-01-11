package com.example.trivia_game;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName="Trivia.db";
    private static final int DATABASE_VERSION = 2;


    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS allusers (" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT)");


        db.execSQL("CREATE TABLE IF NOT EXISTS allquestion (" +
                "question TEXT PRIMARY KEY, " +
                "answers TEXT, " +
                "correctanswer TEXT)");


        db.execSQL("CREATE TABLE IF NOT EXISTS Result (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "score TEXT, " +
                "result TEXT,"+
                "date TEXT,"+
                "time TEXT)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop all tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS allusers");
        db.execSQL("DROP TABLE IF EXISTS allquestion");
        db.execSQL("DROP TABLE IF EXISTS Result");
        onCreate(db);
    }
    public Boolean insertData(String email,String password){
        SQLiteDatabase Mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long result=Mydb.insert("allusers",null,contentValues);
        if (result==-1){
            return false;
        }else {return true;}
    }
    public void insertQuestion(String Q,String[] answers,String correctanswer){
        SQLiteDatabase Mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("question",Q);
        contentValues.put("answers", TextUtils.join(",",answers));
        contentValues.put("correctanswer",correctanswer);
        Mydb.insert("allquestion",null,contentValues);
    }
    public void insertResults(String score,String[] result){
        SQLiteDatabase Mydb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("score",score);
        contentValues.put("result",TextUtils.join(",",result));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-indexed
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        String currentDate = dayOfMonth + "-" + month + "-" +year;

        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String currentTime = String.format("%02d:%02d:%02d", hour, minute, second);

        contentValues.put("date", currentDate);
        contentValues.put("time",currentTime);
        Mydb.insert("Result",null,contentValues);
    }
    public List<String[]> getAllResults() {
        SQLiteDatabase Mydb = this.getReadableDatabase();
        List<String[]> resultsList = new ArrayList<>();
        Cursor cursor = Mydb.rawQuery("SELECT * FROM Result", null);

        if (cursor.moveToFirst()) {
            do {
                // Extract each column and store it in an array
                String[] row = new String[]{
                        cursor.getString(cursor.getColumnIndexOrThrow("score")),
                        cursor.getString(cursor.getColumnIndexOrThrow("result"))
                };
                resultsList.add(row);
            } while (cursor.moveToNext());
        }

        cursor.close(); // Close the cursor
        return resultsList; // Return the list of results
    }
    public String getLastResultDateTime() {
        SQLiteDatabase Mydb = this.getReadableDatabase();
        String query = "SELECT date, time FROM Result ORDER BY id DESC LIMIT 1";
        Cursor cursor = Mydb.rawQuery(query, null);

        String dateTime = "No results found"; // Default value if no results

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Check if the required columns are available
                int dateColumnIndex = cursor.getColumnIndex("date");
                int timeColumnIndex = cursor.getColumnIndex("time");

                if (dateColumnIndex != -1 && timeColumnIndex != -1) {
                    // Get the date and time of the most recent result
                    String date = cursor.getString(dateColumnIndex);
                    String time = cursor.getString(timeColumnIndex);

                    // Combine date and time into a single string
                    dateTime = date + " " + time;
                } else {
                    dateTime = "Error: Missing date or time column!";
                }
            } else {
                dateTime = "No results found";
            }

            cursor.close(); // Close the cursor
        }

        return dateTime; // Return the formatted date and time
    }


    public Boolean checkEmail(String email){
        SQLiteDatabase Mydb=this.getWritableDatabase();
        Cursor cursor=Mydb.rawQuery("Select * from allusers where email = ?",new String[]{email});
        if(cursor.getCount()>0){
            return true;
        }else {return false;}
    }
    public Boolean checkEmailpassword(String email,String password){
        SQLiteDatabase Mydb=this.getWritableDatabase();
        Cursor cursor=Mydb.rawQuery("Select * from allusers where email = ? and password = ?",new String[]{email, password});
        if(cursor.getCount()>0){
            return true;
        }else {return false;}
    }
}
