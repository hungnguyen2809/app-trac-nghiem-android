package com.hungnguyen2809.apptracnghiem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hungnguyen2809.apptracnghiem.Class.Question;
import com.hungnguyen2809.apptracnghiem.R;

public class InformationQuestionActivity extends AppCompatActivity {
    TextView txtContentQuestion, txtAnswerA, txtAnswerB, txtAnswerC, txtAnswerD,txtAnswerResultQuestion;
    Button btExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_question);
        Mapping();

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Question question = (Question) intent.getSerializableExtra("data");
        txtContentQuestion.setText(question.getContentQuestion());
        txtAnswerA.setText(question.getContentA());
        txtAnswerB.setText(question.getContentB());
        txtAnswerC.setText(question.getContentC());
        txtAnswerD.setText(question.getContentD());
        txtAnswerResultQuestion.setText(question.getResultQuestion());

    }


    private void Mapping(){
        txtContentQuestion = (TextView) findViewById(R.id.textViewContentQuestionInfor);
        txtAnswerA = (TextView) findViewById(R.id.textViewAnswerAInfor);
        txtAnswerB = (TextView) findViewById(R.id.textViewAnswerBInfor);
        txtAnswerC = (TextView) findViewById(R.id.textViewAnswerCInfor);
        txtAnswerD = (TextView) findViewById(R.id.textViewAnswerDInfor);
        txtAnswerResultQuestion = (TextView) findViewById(R.id.textViewAnswerResultQuestionInfor);
        btExit = (Button) findViewById(R.id.buttonExitInfor);
    }
}
