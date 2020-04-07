package com.hungnguyen2809.apptracnghiem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hungnguyen2809.apptracnghiem.Class.Question;
import com.hungnguyen2809.apptracnghiem.R;

public class EditQuestionActivity extends AppCompatActivity {
    EditText edtContentQuestion, edtAnswerA, edtAnswerB, edtAnswerC, edtAnswerD, edtAnswerResultQuestion;
    Button btAccept, btExit;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        Mapping();
        GetData();
        btExit.setOnClickListener(new View.OnClickListener() {
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
                String answerResult = edtAnswerResultQuestion.getText().toString().trim().toUpperCase();
                if (content.isEmpty() || answerA.isEmpty() || answerB.isEmpty() || answerC.isEmpty() || answerD.isEmpty() || answerResult.isEmpty()){
                    Toast.makeText(EditQuestionActivity.this, "Bạn chưa điền đầy đủ hết thông tin !", Toast.LENGTH_SHORT).show();
                }
                else if (checkAnswerResult(answerResult) == false){
                    Toast.makeText(EditQuestionActivity.this, "Đáp án đưa vào chỉ có thể là: A, B, C, D", Toast.LENGTH_SHORT).show();
                }
                else {
                    Question question = new Question(id, content, answerA, answerB, answerC, answerD, answerResult);
                    Intent intent = new Intent();
                    intent.putExtra("data", question);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void Mapping(){
        edtContentQuestion = (EditText) findViewById(R.id.editTextContentQuestionEdit);
        edtAnswerA = (EditText) findViewById(R.id.editTextAnswerAEdit);
        edtAnswerB = (EditText) findViewById(R.id.editTextAnswerBEdit);
        edtAnswerC = (EditText) findViewById(R.id.editTextAnswerCEdit);
        edtAnswerD = (EditText) findViewById(R.id.editTextAnswerDEdit);
        edtAnswerResultQuestion = (EditText) findViewById(R.id.editTextResultQuestionEdit);
        btAccept = (Button) findViewById(R.id.buttonAcceptEditQuestionEdit);
        btExit = (Button) findViewById(R.id.buttonCancelEditQuestionEdit);
    }

    private void GetData(){
        Intent intent = getIntent();
        Question question = (Question) intent.getSerializableExtra("data");
        id = question.getId();
        edtContentQuestion.setText(question.getContentQuestion());
        edtAnswerA.setText(question.getContentA());
        edtAnswerB.setText(question.getContentB());
        edtAnswerC.setText(question.getContentC());
        edtAnswerD.setText(question.getContentD());
        edtAnswerResultQuestion.setText(question.getResultQuestion());
    }

    private boolean checkAnswerResult(String str){
        return (str.matches("^[A-D]*$"));
    }
}
