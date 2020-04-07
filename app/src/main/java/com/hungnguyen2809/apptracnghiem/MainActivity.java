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
import com.hungnguyen2809.apptracnghiem.DatabaseManager.Database;

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


}
