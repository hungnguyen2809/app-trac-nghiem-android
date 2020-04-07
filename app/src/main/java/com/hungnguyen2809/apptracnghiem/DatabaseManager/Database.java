package com.hungnguyen2809.apptracnghiem.DatabaseManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.accessibility.AccessibilityRecord;

import androidx.annotation.Nullable;

import com.hungnguyen2809.apptracnghiem.Class.Account;
import com.hungnguyen2809.apptracnghiem.Class.Question;
import com.hungnguyen2809.apptracnghiem.Class.Student;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static String tblNameQuestion = "Question";
    public static String tblNameStudent = "Student";
    public static String tblAdmin = "ADMIN";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void SQLNonResultData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor SQLGetResultData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void CreateTableQuestion(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tblNameQuestion + " ( " +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " ContentQuestion NVARCHAR(250), " +
                " AnswerA NVARCHAR(10), " +
                " AnswerB NVARCHAR(10), " +
                " AnswerC NVARCHAR(10), " +
                " AnswerD NVARCHAR(10), " +
                " AnswerResult NVARCHAR(10) )";
        SQLNonResultData(sql);
    }

    public void CreateTableStudent(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tblNameStudent + " ( " +
                " MSV NVARCHAR(50) PRIMARY KEY, " +
                " Name NVARCHAR(250), " +
                " Lop NVARCHAR(50), " +
                " Count INTEGER )";
        SQLNonResultData(sql);
    }

    public void CreateTableAdmin(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tblAdmin + " ( " +
                " User NVARCHAR(50) PRIMARY KEY, " +
                " Pass NVARCHAR(50) )";
        SQLNonResultData(sql);
    }

    public ArrayList<Account> GetUserAdmin(){
        String sql = "SELECT * FROM " + tblAdmin;
        ArrayList<Account> list = new ArrayList<>();
        Cursor cursor = SQLGetResultData(sql);
        while (cursor.moveToNext()){
            String user = cursor.getString(0);
            String pass = cursor.getString(1);
            list.add(new Account(user, pass));
        }
        return list;
    }

    public ArrayList<Account> GetUserStudent(){
        String sql = "SELECT * FROM " + tblNameStudent;
        Cursor cursor = SQLGetResultData(sql);
        ArrayList<Account> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String msv = cursor.getString(0).trim();
            String lop = cursor.getString(2).trim();
            String pass = (msv + lop).toUpperCase();
            list.add(new Account(msv, pass));
        }
        return list;
    }

    public void InsertStudent(Student student){
        String sql = "INSERT INTO " +tblNameStudent+ " VALUES ( '"+student.getMsv()+"', '"+student.getName()+"', '"+student.getLop()+"' , "+student.getCountAnswer()+" )";
        SQLNonResultData(sql);
    }

    public void UpdatePointStudent(int point, String msv){
        String sql = "UPDATE " + tblNameStudent + " SET Count = "+ point +" WHERE MSV = '"+ msv +"' ";
        SQLNonResultData(sql);
    }

    public void DeleteAllStudent(){
        String sql = "DELETE FROM " + tblNameStudent;
        SQLNonResultData(sql);
    }


    public ArrayList<Student> GetAllDataStudent(){
        ArrayList<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM " +  tblNameStudent;
        Cursor cursor = SQLGetResultData(sql);
        while (cursor.moveToNext()){
            String msv = cursor.getString(0);
            String name = cursor.getString(1);
            String lop = cursor.getString(2);
            int point = cursor.getInt(3);
            list.add(new Student(msv, name, lop, point));
        }
        return list;
    }

    public void InsertQuestion(Question question){
        String sql = "INSERT INTO " + tblNameQuestion + " VALUES (null, '"+ question.getContentQuestion() +"', '"+ question.getContentA() +"', " +
                " '"+ question.getContentB() +"', '"+ question.getContentC() +"', '"+ question.getContentD() +"', '"+ question.getResultQuestion() +"'   )";
        SQLNonResultData(sql);
    }

    public void DeleteQuestion(int id){
        String sql = "DELETE FROM " + tblNameQuestion + " WHERE ID = " + id;
        SQLNonResultData(sql);
    }

    public void DeleteAllQuestion(){
        String sql = "DELETE FROM " + tblNameQuestion ;
        SQLNonResultData(sql);
    }

    public void UpdateQuestion(Question question){
        String sql = "UPDATE " + tblNameQuestion + " SET ContentQuestion = '"+ question.getContentQuestion() +"', AnswerA = '"+ question.getContentA() +"'," +
                " AnswerB = '"+ question.getContentB() +"', AnswerC = '"+ question.getContentC() +"', AnswerD = '"+ question.getContentD() +"', " +
                " AnswerResult = '"+ question.getResultQuestion() +"' WHERE ID = "+ question.getId() +" ";
        SQLNonResultData(sql);
    }

    public ArrayList<Question> GetAllDataQuestion(){
        ArrayList<Question> listQuestion = new ArrayList<>();
        String sql = "SELECT * FROM " + tblNameQuestion;
        Cursor cursor = SQLGetResultData(sql);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String content = cursor.getString(1);
            String answerA = cursor.getString(2);
            String answerB = cursor.getString(3);
            String answerC = cursor.getString(4);
            String answerD = cursor.getString(5);
            String answerResult = cursor.getString(6);
            listQuestion.add(new Question(id, content, answerA, answerB, answerC, answerD, answerResult));
        }
        return  listQuestion;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
