package com.hungnguyen2809.apptracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hungnguyen2809.apptracnghiem.Activity.ChooseOptionActivity;
import com.hungnguyen2809.apptracnghiem.Activity.ManagerExampleActivity;
import com.hungnguyen2809.apptracnghiem.Activity.ManagerStudentActivity;
import com.hungnguyen2809.apptracnghiem.Activity.ResultExamActivity;
import com.hungnguyen2809.apptracnghiem.Class.Account;
import com.hungnguyen2809.apptracnghiem.Class.Question;
import com.hungnguyen2809.apptracnghiem.Class.Server;
import com.hungnguyen2809.apptracnghiem.Class.Student;
import com.hungnguyen2809.apptracnghiem.DatabaseManager.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btLogin, btCancel;
    public static Database database;
    public static String MarkLogin = "";
    public static String CheckedFirst = "";
    public static String demo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Server.mySocket.connect();

        Mapping();
        database = new Database(this, "DBQuestion", null, 1);
        database.CreateTableQuestion();
        database.CreateTableStudent();
        database.CreateTableAdmin();
        AddUserAdmin();
        ResultExamActivity.isCheckedExam = false;
        UpdateOnServer();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginAdmin() || LoginUserStudent()){
                    Intent login = new Intent(MainActivity.this, ChooseOptionActivity.class);
                    startActivity(login);
                    MarkLogin = edtUsername.getText().toString().trim();
                    CheckedFirst = edtPassword.getText().toString().trim();
                }
                else {
                    Toast.makeText(MainActivity.this, "Thông tin đăng nhập không đúng !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUsername.setText("");
                edtPassword.setText("");
            }
        });
    }

    private void UpdateOnServer(){
        Server.mySocket.on("server-send-all-question", UpdateQuestion);
        Server.mySocket.on("server-send-all-student", UpdateStudent);
        Server.mySocket.on("server-accept-delete-question", DeleteAllQuestion);
        Server.mySocket.on("server-accept-delete-student", DeleteAllStudent);
    }

    Emitter.Listener DeleteAllStudent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject query = (JSONObject) args[0];
                    try {
                        String content = query.getString("content");
                        if (content.trim().equals("delete")){
                            database.DeleteAllStudent();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    Emitter.Listener DeleteAllQuestion = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject query = (JSONObject) args[0];
                    try {
                        String content = query.getString("content");
                        if (content.trim().equals("delete")){
                            database.DeleteAllQuestion();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private final Emitter.Listener UpdateQuestion = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        JSONObject data = object.getJSONObject("data-question");
                    } catch (JSONException e) {
                            e.printStackTrace();
                    }
                    /*try {
                        JSONObject question = data.getJSONObject("data-question");
                        String content = question.getString("Content");
                        String a = question.getString("AnswerA");
                        String b = question.getString("AnswerB");
                        String c = question.getString("AnswerC");
                        String d = question.getString("AnswerD");
                        String res = question.getString("AnswerResult");
                        Question qs = new Question(content, a, b, c, d, res);
                        //database.InsertQuestion(qs);
                        demo = question.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            });
        }
    };

    private final Emitter.Listener UpdateStudent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONObject student = data.getJSONObject("data-student");
                        String msv = student.getString("MSV");
                        String name = student.getString("Name");
                        String lop = student.getString("Lop");
                        int point = student.getInt("Point");
                        Student st = new Student(msv, name, lop, point);
                        //database.InsertStudent(st);
                        Toast.makeText(MainActivity.this, student.toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private boolean LoginAdmin(){
        String user = edtUsername.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        for (Account ac : database.GetUserAdmin()){
            if (user.equals(ac.getUser()) && pass.equals(ac.getPass())){
                return true;
            }
        }
        return false;
    }

    private boolean LoginUserStudent(){
        String user = edtUsername.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        for (Account student : database.GetUserStudent()){
            if (user.equals(student.getUser()) && pass.equals(student.getPass())){
                return  true;
            }
        }
        return  false;
    }

    private void Mapping(){
        edtUsername = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btLogin = (Button) findViewById(R.id.buttonLogin);
        btCancel = (Button) findViewById(R.id.buttonCancel);
    }

    private void AddUserAdmin(){
        if (database.GetUserAdmin().size() == 0){
            String sql = "INSERT INTO " + Database.tblAdmin + " VALUES ( 'admin', 'admin' )";
            database.SQLNonResultData(sql);
        }
    }

    @Override
    protected void onResume() {
        edtUsername.setText("");
        edtPassword.setText("");
        ResultExamActivity.isCheckedExam = false;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Server.mySocket.close();
        super.onDestroy();
    }

}
