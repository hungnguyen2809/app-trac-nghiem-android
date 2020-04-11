package com.hungnguyen2809.apptracnghiem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hungnguyen2809.apptracnghiem.Class.Question;
import com.hungnguyen2809.apptracnghiem.R;

public class AddQuestionActivity extends AppCompatActivity {
    EditText edtContentQuestion, edtAnswerA, edtAnswerB, edtAnswerC, edtAnswerD, edtAnswerResult;
    Button btAccept, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        Mapping();

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtContentQuestion.getText().toString().trim();
                String answerA = edtAnswerA.getText().toString().trim();
                String answerB = edtAnswerB.getText().toString().trim();
                String answerC = edtAnswerC.getText().toString().trim();
                String answerD = edtAnswerD.getText().toString().trim();
                String answerResult = edtAnswerResult.getText().toString().trim().toUpperCase();
                if (content.isEmpty() || answerA.isEmpty() || answerB.isEmpty() || answerC.isEmpty() || answerD.isEmpty() || answerResult.isEmpty()) {
                    Toast.makeText(AddQuestionActivity.this, "Bạn chưa điền đầy đủ hết thông tin !", Toast.LENGTH_SHORT).show();
                } else if (!checkAnswerResult(answerResult)) {
                    Toast.makeText(AddQuestionActivity.this, "Đáp án đưa vào chỉ có thể là: A, B, C, D", Toast.LENGTH_SHORT).show();
                } else if (content.length() > 255) {
                    Toast.makeText(AddQuestionActivity.this, "Nội dung câu hỏi hiện tại quá dài!\nVui lòng giảm bớt nội dung lại", Toast.LENGTH_SHORT).show();
                } else if (answerA.length() > 50 || answerB.length() > 50 || answerC.length() > 50 || answerD.length() > 50) {
                    Toast.makeText(AddQuestionActivity.this, "Nội dung các câu trả lời quá dài vui lòng giảm bớt lại !", Toast.LENGTH_SHORT).show();
                } else if (answerResult.length() > 1) {
                    Toast.makeText(AddQuestionActivity.this, "Đáp án trả lời chỉ được duy nhất một chữ cái A hoặc B hoặc C hoặc D ", Toast.LENGTH_SHORT).show();
                } else {
                    Question question = new Question(content, answerA, answerB, answerC, answerD, answerResult);
                    Intent intent = new Intent();
                    intent.putExtra("data", question);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void Mapping() {
        edtContentQuestion = (EditText) findViewById(R.id.editTextContentQuestion);
        edtAnswerA = (EditText) findViewById(R.id.editTextAnswerA);
        edtAnswerB = (EditText) findViewById(R.id.editTextAnswerB);
        edtAnswerC = (EditText) findViewById(R.id.editTextAnswerC);
        edtAnswerD = (EditText) findViewById(R.id.editTextAnswerD);
        edtAnswerResult = (EditText) findViewById(R.id.editTextResultQuestion);
        btAccept = (Button) findViewById(R.id.buttonAcceptAddQuestion);
        btCancel = (Button) findViewById(R.id.buttonCancelAddQuestion);
    }

    private boolean checkAnswerResult(String str) {
        return (str.matches("^[A-D]*$"));
    }
}
