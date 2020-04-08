package com.hungnguyen2809.apptracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hungnguyen2809.apptracnghiem.Activity.ChooseOptionActivity;
import com.hungnguyen2809.apptracnghiem.Activity.ResultExamActivity;
import com.hungnguyen2809.apptracnghiem.Class.Account;
import com.hungnguyen2809.apptracnghiem.Class.Server;
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
        Server.mySocket.on("'server-send-all-student", UpdateStudent);
    }

    private Emitter.Listener UpdateQuestion = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray dataQuestion = new JSONArray(args[0]);
                        for(int i=0; i<dataQuestion.length(); i++){
                            JSONObject question = dataQuestion.getJSONObject(i);
                            /**
                             *
                             *
                             * */
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener UpdateStudent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray dataStudent = new JSONArray(args[0]);
                        for (int i=0; i<dataStudent.length(); i++){
                            JSONObject student = dataStudent.getJSONObject(i);
                            /**
                             *
                             *
                             * */
                        }
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
