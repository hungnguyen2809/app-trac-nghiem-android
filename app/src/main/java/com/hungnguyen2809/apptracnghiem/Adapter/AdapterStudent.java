package com.hungnguyen2809.apptracnghiem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hungnguyen2809.apptracnghiem.Class.Student;
import com.hungnguyen2809.apptracnghiem.R;

import java.util.ArrayList;

public class AdapterStudent extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Student> dsStudent;

    public AdapterStudent(Context context, int layout, ArrayList<Student> dsStudent) {
        this.context = context;
        this.layout = layout;
        this.dsStudent = dsStudent;
    }

    @Override
    public int getCount() {
        return dsStudent.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtMSV, txtName, txtCountAnswer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.txtMSV = (TextView) convertView.findViewById(R.id.textViewMSV);
            holder.txtName = (TextView) convertView.findViewById(R.id.textViewHoTen);
            holder.txtCountAnswer = (TextView) convertView.findViewById(R.id.textViewKetQua);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Student student = dsStudent.get(position);
        holder.txtMSV.setText("MSV: " + student.getMsv());
        holder.txtName.setText("Họ tên: " + student.getName());
        holder.txtCountAnswer.setText("Số câu trả lời đúng: " + student.getCountAnswer());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_list_student);
        convertView.startAnimation(animation);

        return convertView;
    }
}
