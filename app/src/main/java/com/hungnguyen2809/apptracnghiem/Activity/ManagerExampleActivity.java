package com.hungnguyen2809.apptracnghiem.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hungnguyen2809.apptracnghiem.Adapter.AdapterQuestion;
import com.hungnguyen2809.apptracnghiem.Class.Question;
import com.hungnguyen2809.apptracnghiem.Class.StringURL;
import com.hungnguyen2809.apptracnghiem.MainActivity;
import com.hungnguyen2809.apptracnghiem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManagerExampleActivity extends AppCompatActivity {
    ListView listQuestion;
    public AdapterQuestion adapterQuestion;
    public ArrayList<Question> arrayListQuestion;
    final int REQUEST_CODE_ADD_QUESTION = 222;
    final int REQUEST_CODE_EDIT_QUESTION = 333;
    int location = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_example);
        Mapping();
        arrayListQuestion = new ArrayList<>();
        adapterQuestion = new AdapterQuestion(this, R.layout.line_layout_custom_question, arrayListQuestion);
        listQuestion.setAdapter(adapterQuestion);

        UpdateData();
        registerForContextMenu(listQuestion);

        listQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ManagerExampleActivity.this, InformationQuestionActivity.class);
                intent.putExtra("data", arrayListQuestion.get(position));
                startActivity(intent);
            }
        });

        listQuestion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                location = position;
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_edit_delete_question, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit_question:
                Intent intent = new Intent(ManagerExampleActivity.this, EditQuestionActivity.class);
                intent.putExtra("data", arrayListQuestion.get(location));
                startActivityForResult(intent, REQUEST_CODE_EDIT_QUESTION);
                break;
            case R.id.menu_delete_question:
                DeleteQuestion();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_ADD_QUESTION && resultCode == RESULT_OK && data != null){
            Question question = (Question) data.getSerializableExtra("data");
            MainActivity.database.InsertQuestion(question);
            UpdateData();
            Toast.makeText(ManagerExampleActivity.this, "Thêm thành công !", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == REQUEST_CODE_EDIT_QUESTION && resultCode == RESULT_OK && data != null){
            Question question = (Question) data.getSerializableExtra("data");
            MainActivity.database.UpdateQuestion(question);
            UpdateData();
            Toast.makeText(this, "Sửa thành công !", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_question, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                Intent newQuestion = new Intent(ManagerExampleActivity.this, AddQuestionActivity.class);
                startActivityForResult(newQuestion, REQUEST_CODE_ADD_QUESTION);
                break;
            case R.id.menu_add_server:
                AddAllQuestionOnServer(StringURL.urlGetAllQuestionFromServer);
                break;
            case R.id.menu_delete_all_question:
                DeleteAllQuestion();
                break;
            case R.id.menu_set_time:
                SetTimeExam();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SetTimeExam(){
        final Dialog dialogSetTine = new Dialog(this);
        dialogSetTine.setContentView(R.layout.layout_set_time_exam);
        dialogSetTine.setCanceledOnTouchOutside(false);

        final EditText edtSetTime = (EditText) dialogSetTine.findViewById(R.id.editTextSetTimeExam);
        Button btAccept = (Button) dialogSetTine.findViewById(R.id.buttonAcceptTime);
        Button btCancel = (Button) dialogSetTine.findViewById(R.id.buttonCancelTime);
        edtSetTime.setText(MainActivity.timeExam + "");

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStringOnlyDigits(edtSetTime.getText().toString().trim())){
                    Toast.makeText(ManagerExampleActivity.this, "Thời gian không được để trống ! Và nó phải là một số nguyên !", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity.timeExam = Integer.parseInt(edtSetTime.getText().toString().trim());
                    Toast.makeText(ManagerExampleActivity.this, "Bạn đã cài thời gian thi là: " + edtSetTime.getText().toString(), Toast.LENGTH_SHORT).show();
                    dialogSetTine.cancel();
                }
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSetTine.cancel();
            }
        });
        dialogSetTine.show();
    }

    private  boolean isStringOnlyDigits(String str){
        return ((str != null) && (!str.equals("")) && (str.matches("[0-9]+")));
    }

    private void DeleteAllQuestion() {
        AlertDialog.Builder delete = new AlertDialog.Builder(this);
        delete.setTitle("Cảnh báo !");
        delete.setMessage("Bạn có chắc muốn xóa tất cả các câu hỏi hiện tại không ?");
        delete.setPositiveButton("Xóa tất cả", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.database.DeleteAllQuestion();
                UpdateData();
            }
        });

        delete.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        delete.show();
    }

    private void AddAllQuestionOnServer(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            for (int i=0; i<data.length(); i++){
                                JSONObject question = data.getJSONObject(i);
                                String content = question.getString("Content");
                                String a = question.getString("AnswerA");
                                String b = question.getString("AnswerB");
                                String c = question.getString("AnswerC");
                                String d = question.getString("AnswerD");
                                String res = question.getString("AnswerRes");
                                Question qs = new Question(content, a, b, c, d, res);
                                MainActivity.database.InsertQuestion(qs);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        UpdateData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManagerExampleActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void DeleteQuestion(){
        AlertDialog.Builder delete = new AlertDialog.Builder(this);
        delete.setTitle("Thông báo");
        delete.setMessage("Bạn có chắc muốn xóa câu hỏi này không ?");
        delete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.database.DeleteQuestion(arrayListQuestion.get(location).getId());
                UpdateData();
                Toast.makeText(ManagerExampleActivity.this, "Xóa thành công !", Toast.LENGTH_SHORT).show();
            }
        });
        delete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        delete.show();
    }

    private void Mapping(){
        listQuestion = (ListView) findViewById(R.id.listViewDanhSachCauHoi);
    }

    public void UpdateData() {
        arrayListQuestion.clear();
        for (Question question : MainActivity.database.GetAllDataQuestion()){
            arrayListQuestion.add(question);
        }
        adapterQuestion.notifyDataSetChanged();
    }

}
