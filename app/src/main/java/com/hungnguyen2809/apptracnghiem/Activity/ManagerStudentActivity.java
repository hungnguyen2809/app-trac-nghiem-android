package com.hungnguyen2809.apptracnghiem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hungnguyen2809.apptracnghiem.Adapter.AdapterStudent;
import com.hungnguyen2809.apptracnghiem.Class.StringURL;
import com.hungnguyen2809.apptracnghiem.Class.Student;
import com.hungnguyen2809.apptracnghiem.MainActivity;
import com.hungnguyen2809.apptracnghiem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerStudentActivity extends AppCompatActivity {
    ListView listViewStudent;
    TextView txtLop;
    ArrayList<Student> dsStudent;
    AdapterStudent adapterStudent;
    ArrayList<String> listClassName;
    ArrayList<Student> dsStudentWhereClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_student);
        Mapping();
        listClassName = new ArrayList<>();
        dsStudent = new ArrayList<>();
        adapterStudent = new AdapterStudent(this, R.layout.line_layout_custom_student, dsStudent);
        listViewStudent.setAdapter(adapterStudent);

        dsStudentWhereClass = new ArrayList<>();

        AddData();
        ReadAllClassFromServer(StringURL.urlGetAllClass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manager_student, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add_all_student:
                GetAllStudentFromServer();
                break;
            case R.id.menu_delete_all_student:
                DeleteAllStudent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetAllStudentFromClass(String url, final String lop, final int position){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            for (int i=0; i<data.length(); i++){
                                int countAnswer = 0;
                                JSONObject object = data.getJSONObject(i);
                                String msv = object.getString("MSV");
                                String name = object.getString("Name");
                                String lop = object.getString("Lop");
                                String count = object.getString("CountAnswer");
                                if (!count.equals("null")){
                                    countAnswer = Integer.parseInt(count);
                                }
                                Student st = new Student(msv, name, lop, countAnswer);
                                dsStudentWhereClass.add(st);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AddStudentInDatabase(listClassName.get(position));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManagerStudentActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("lopHS", lop);
                return  data;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetAllStudentFromServer(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.show_all_class_server);
        ListView listClass = (ListView) dialog.findViewById(R.id.listViewClass);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, listClassName);
        listClass.setAdapter(adapter);

        listClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetAllStudentFromClass(StringURL.urlGetAllStudentWhereClass, listClassName.get(position), position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void AddStudentInDatabase(String lop){
        if (MainActivity.database.GetAllDataStudent().size() > 0){
            Toast.makeText(this, "Bạn cần xóa dữ liệu của lớp trước !", Toast.LENGTH_SHORT).show();
        }
        else {
            for (Student student : dsStudentWhereClass){
                MainActivity.database.InsertStudent(student);
            }
            txtLop.setText("Danh sách lớp: " + lop);
            UpdateData();
        }
    }


    private void ReadAllClassFromServer(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest requestAllClass = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<String> listName = new ArrayList<>();
                        for (int i=0; i<response.length(); i++){
                            try {
                                listName.add(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (String vl : listName){
                            if (checkIsExistsNameClass(vl)){
                                listClassName.add(vl);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManagerStudentActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", error.toString());
                    }
                });
        requestQueue.add(requestAllClass);
    }

    private boolean checkIsExistsNameClass(String input){
        for (String values : listClassName){
            if (input.equals(values))
                return false;
        }
        return true;
    }

    private void DeleteAllStudent(){
        AlertDialog.Builder delete = new AlertDialog.Builder(this);
        delete.setTitle("Cảnh báo !");
        delete.setMessage("Bạn có chắc muốn xóa toàn bộ học sinh ở trong lớp này không ?");
        delete.setPositiveButton("Xóa tất cả", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.database.DeleteAllStudent();
                txtLop.setText("Danh sách lớp đang trống !");
                Toast.makeText(ManagerStudentActivity.this, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        delete.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        delete.show();
    }

    private void UpdateData() {
        dsStudent.clear();
        for (Student st : MainActivity.database.GetAllDataStudent()){
            dsStudent.add(st);
        }
        adapterStudent.notifyDataSetChanged();
    }

    private void AddData(){
        if (MainActivity.database.GetAllDataStudent().size() == 0){
            txtLop.setText("Danh sách trống !");
            UpdateData();
        }
        else {
            String lop = MainActivity.database.GetAllDataStudent().get(0).getLop();
            txtLop.setText("Danh sách lớp: " + lop);
            UpdateData();
        }
    }

    private void Mapping(){
        listViewStudent = (ListView) findViewById(R.id.listViewStudent);
        txtLop = (TextView) findViewById(R.id.textViewLop);
    }

}
