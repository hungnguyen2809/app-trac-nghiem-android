package com.hungnguyen2809.apptracnghiem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hungnguyen2809.apptracnghiem.Interface.ResultExam;
import com.hungnguyen2809.apptracnghiem.MainActivity;
import com.hungnguyen2809.apptracnghiem.R;

public class ResultExamActivity extends AppCompatActivity {
    Button btReturnHome;
    TextView txtResult;
    public static boolean isCheckedExam = false;
    public static String CheckedLast = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_exam);
        btReturnHome = (Button) findViewById(R.id.buttonReturnHome);
        txtResult = (TextView) findViewById(R.id.textViewResultExam);

        Intent intent = getIntent();
        int result = intent.getIntExtra("res", 0);
        int countQuestion = intent.getIntExtra("countQuestion", 0);
        txtResult.setText("Bạn đã trả lời đúng tất cả:\n" + result + " / " + countQuestion);

        btReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultExamActivity.this, ChooseOptionActivity.class);
                startActivity(intent);
                isCheckedExam = true;
                CheckedLast = MainActivity.MarkLogin;
                ResultExamActivity.this.finish();
            }
        });
    }
}
