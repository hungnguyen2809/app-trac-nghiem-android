package com.hungnguyen2809.apptracnghiem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.hungnguyen2809.apptracnghiem.Class.Question;

import com.hungnguyen2809.apptracnghiem.DatabaseManager.Database;
import com.hungnguyen2809.apptracnghiem.Interface.ResultExam;
import com.hungnguyen2809.apptracnghiem.MainActivity;
import com.hungnguyen2809.apptracnghiem.R;

import java.util.ArrayList;

public class ChooseOptionActivity extends AppCompatActivity {
    Button btStartExample, btManagerStudent, btManagerExample;
    ArrayList<Question> listQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);
        Mapping();

        listQuestion = new ArrayList<>();

        AddQuestion();
        SecureInformation();

        btStartExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartExam();
            }
        });

        btManagerStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent managerStudent = new Intent(ChooseOptionActivity.this, ManagerStudentActivity.class);
                startActivity(managerStudent);
                overridePendingTransition(R.anim.anim_manager_student_out, R.anim.anim_manager_student_in);
            }
        });

        btManagerExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent managerExam = new Intent(ChooseOptionActivity.this, ManagerExampleActivity.class);
                startActivity(managerExam);
                overridePendingTransition(R.anim.anim_manager_question_in, R.anim.anim_manager_question_out);
            }
        });
    }

    private void StartExam() {
        if (listQuestion.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChooseOptionActivity.this);
            builder.setTitle("Thông báo!");
            builder.setMessage("Bạn chưa thể bắt đầu thi vì hiện tại đang chưa có câu hỏi nào đang lưu trữ !\nHãy liên hệ với giáo viên để giải quyết !");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        } else if (ResultExamActivity.isCheckedExam || CheckedLogin()) {
            Toast.makeText(ChooseOptionActivity.this, "Bạn đã thi rồi không được phép thi lại !", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder startExam = new AlertDialog.Builder(ChooseOptionActivity.this);
            startExam.setTitle("Thông báo !");
            startExam.setMessage("Bạn đã sẵn sàng bắt đầu thi !");
            startExam.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent startExam = new Intent(ChooseOptionActivity.this, ExampleActivity.class);
                    startActivity(startExam);
                }
            });
            startExam.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            startExam.show();
        }
    }

    private void AddQuestion() {
            listQuestion = MainActivity.database.GetAllDataQuestion();
    }

    private void SecureInformation() {
        if (MainActivity.MarkLogin.equals("admin")) {
            btManagerStudent.setVisibility(View.VISIBLE);
            btManagerExample.setVisibility(View.VISIBLE);
        } else {
            btManagerStudent.setVisibility(View.GONE);
            btManagerExample.setVisibility(View.INVISIBLE);
        }
    }

    private boolean CheckedLogin() {
        if (MainActivity.CheckedFirst.equals(ResultExamActivity.CheckedLast)) {
            return true;
        } else return false;
    }

    @Override
    protected void onResume() {
        AddQuestion();
        super.onResume();
    }

    private void Mapping() {
        btStartExample = (Button) findViewById(R.id.buttonStartExam);
        btManagerExample = (Button) findViewById(R.id.buttonDeThi);
        btManagerStudent = (Button) findViewById(R.id.buttonQLHocSinh);
    }

}
