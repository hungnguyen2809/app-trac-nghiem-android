package com.hungnguyen2809.apptracnghiem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hungnguyen2809.apptracnghiem.Class.Question;
import com.hungnguyen2809.apptracnghiem.Class.StringURL;
import com.hungnguyen2809.apptracnghiem.Class.Student;
import com.hungnguyen2809.apptracnghiem.DatabaseManager.Database;
import com.hungnguyen2809.apptracnghiem.Interface.ResultExam;
import com.hungnguyen2809.apptracnghiem.MainActivity;
import com.hungnguyen2809.apptracnghiem.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExampleActivity extends AppCompatActivity{
    RadioButton radioA, radioB, radioC, radioD;
    Button btBack, btNext, btSubmit;
    TextView txtIndexQuestion, txtContentQuestion, txtTime;
    RadioGroup radioGroupAnswer;
    int location = -1;
    ArrayList<Question> listQuestion;
    String[] listAnswer;
    Boolean[] arrMeetQuestion;
    String classLogin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Mapping();
        listQuestion = new ArrayList<>();
        AddData();
        CountTimeExam();

        listAnswer = new String[listQuestion.size()];
        Arrays.fill(listAnswer, null);

        arrMeetQuestion = new Boolean[listQuestion.size()];
        Arrays.fill(arrMeetQuestion, false);
        SetQuestion(listQuestion.get(location));

        disableButtonBackNext();
        radioAnswerNotCheck();
        GetInformationOfStudent();

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++location;
                SetQuestion(listQuestion.get(location));
                radioAnswerNotCheck();
                responsiveAnswer();
                disableButtonBackNext();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --location;
                SetQuestion(listQuestion.get(location));
                radioAnswerNotCheck();
                responsiveAnswer();
                disableButtonBackNext();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkWorkExam()){
                    String mes = "Bạn có chắc muốn nộp bài ?";
                    DialogSubmit(mes);
                }
                else {
                    String mes = "Bạn chưa hoàn thành tất cả các câu hỏi bạn có chắc muốn nộp ?";
                    DialogSubmit(mes);
                }
            }
        });

        radioA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAnswer[location] = "A";
            }
        });
        radioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAnswer[location] = "B";
            }
        });
        radioC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAnswer[location] = "C";
            }
        });
        radioD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAnswer[location] = "D";
            }
        });
    }

    private void DialogSubmit(String mes){
        AlertDialog.Builder dialogAccept = new AlertDialog.Builder(ExampleActivity.this);
        dialogAccept.setTitle("Thông báo !");
        dialogAccept.setMessage(mes);
        dialogAccept.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EndActivityExam();
            }
        });
        dialogAccept.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogAccept.show();
    }

    private void EndActivityExam(){
        Intent intent = new Intent(ExampleActivity.this, ResultExamActivity.class);
        intent.putExtra("res", ResultExam());
        intent.putExtra("countQuestion", listQuestion.size());
        startActivity(intent);
        if (!MainActivity.MarkLogin.equals("admin")){
            MainActivity.database.UpdatePointStudent(ResultExam(), MainActivity.MarkLogin);
            UpdatePointOnServer(StringURL.urlUpdatePointForStudent, MainActivity.MarkLogin, classLogin, ResultExam());
        }
        ExampleActivity.this.finish();
    }

    private boolean checkWorkExam(){
        for (int i = 0; i < listAnswer.length; i++){
            if (listAnswer[i] == null)
                return false;
        }
        return true;
    }

    private int ResultExam(){
        int count = 0;
        for (int index = 0; index < listQuestion.size(); index++){
            if (listAnswer[index].trim().equals(listQuestion.get(index).getResultQuestion().trim())){
                count++;
            }
        }
        return count;
    }

    private void radioAnswerNotCheck(){
        if (!arrMeetQuestion[location]){
            radioA.setChecked(false);
            radioB.setChecked(false);
            radioC.setChecked(false);
            radioD.setChecked(false);
            if (radioA.isChecked() || radioB.isChecked() || radioC.isChecked() || radioD.isChecked()){
                arrMeetQuestion[location] = true;
            }
        }
    }

    private void responsiveAnswer(){
        if (listAnswer[location] != null){
            if (listAnswer[location].equals("A")){
                radioA.setChecked(true);
            }
            if (listAnswer[location].equals("B")){
                radioB.setChecked(true);
            }
            if (listAnswer[location].equals("C")){
                radioC.setChecked(true);
            }
            if (listAnswer[location].equals("D")){
                radioD.setChecked(true);
            }
        }
    }

    private void disableButtonBackNext(){
        if (location == 0){
            btBack.setEnabled(false);
        }
        else{
            btBack.setEnabled(true);
        }
        if (location >= listQuestion.size() - 1 || listQuestion.size() == 1){
            btNext.setEnabled(false);
        }
        else {
            btNext.setEnabled(true);
        }
    }

    private void Mapping() {
        radioA = (RadioButton) findViewById(R.id.radioButtonA);
        radioB = (RadioButton) findViewById(R.id.radioButtonB);
        radioC = (RadioButton) findViewById(R.id.radioButtonC);
        radioD = (RadioButton) findViewById(R.id.radioButtonD);
        btBack = (Button) findViewById(R.id.buttonExamBack);
        btNext = (Button) findViewById(R.id.buttonExamNext);
        btSubmit = (Button) findViewById(R.id.buttonExamSubmit);
        txtIndexQuestion = (TextView) findViewById(R.id.textViewCauHoi);
        txtContentQuestion = (TextView) findViewById(R.id.textViewNoiDungCauHoi);
        radioGroupAnswer = (RadioGroup) findViewById(R.id.radioGroupAnswer);
        txtTime = (TextView) findViewById(R.id.textViewTime);

        location = 0;
    }

    private void SetQuestion(Question question){
        txtIndexQuestion.setText("Câu số " + (location + 1));
        txtContentQuestion.setText(question.getContentQuestion());
        radioA.setText(question.getContentA());
        radioB.setText(question.getContentB());
        radioC.setText(question.getContentC());
        radioD.setText(question.getContentD());
    }

    private void AddData(){
        listQuestion.clear();
        for (Question question : MainActivity.database.GetAllDataQuestion()){
            listQuestion.add(question);
        }
    }

    private void UpdatePointOnServer(String url, final String msv, final String lop, final int point){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("complete")){
                            Toast.makeText(ExampleActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ExampleActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ExampleActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("MSV", msv);
                data.put("Lop", lop);
                data.put("Point", point + "");
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetInformationOfStudent(){
        String sql = "SELECT Lop FROM " + Database.tblNameStudent + " WHERE MSV = '"+MainActivity.MarkLogin+"' ";
        Cursor cursor = MainActivity.database.SQLGetResultData(sql);
        while (cursor.moveToNext()){
            classLogin = cursor.getString(0);
        }
    }

    private void CountTimeExam(){
        CountDownTimer time = new CountDownTimer(MainActivity.timeExam * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long timeNow = millisUntilFinished / 1000;
                txtTime.setText(timeNow / 60 + " : " + (timeNow % 60));
            }
            @Override
            public void onFinish() {
                Toast.makeText(ExampleActivity.this, "Bạn đã hết thời gian làm bài thi !", Toast.LENGTH_SHORT).show();
                EndActivityExam();
            }
        };
        time.start();
    }
}