package com.hungnguyen2809.apptracnghiem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hungnguyen2809.apptracnghiem.Class.Question;
import com.hungnguyen2809.apptracnghiem.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class AdapterQuestion extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Question> listQuestion;

    public AdapterQuestion(Context context, int layout, ArrayList<Question> listQuestion) {
        this.context = context;
        this.layout = layout;
        this.listQuestion = listQuestion;
    }

    @Override
    public int getCount() {
        return listQuestion.size();
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
        TextView txtSTT, txtContentQuestion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.txtSTT = (TextView) convertView.findViewById(R.id.textViewSTTQuestion);
            holder.txtContentQuestion = (TextView) convertView.findViewById(R.id.textViewContentQuestion);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question question = listQuestion.get(position);
        holder.txtSTT.setText((position + 1) + "");
        String content = question.getContentQuestion();
        if (content.length() >= 20){
            holder.txtContentQuestion.setText(content.substring(0,20) + " ....");
        }
        else {
            holder.txtContentQuestion.setText(content);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_list_question);
        convertView.startAnimation(animation);

        return convertView;
    }
}
