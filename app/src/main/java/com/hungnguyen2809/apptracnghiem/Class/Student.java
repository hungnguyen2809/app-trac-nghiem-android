package com.hungnguyen2809.apptracnghiem.Class;

import androidx.annotation.NonNull;

public class Student {
    private String msv;
    private String name;
    private String lop;
    private int countAnswer;

    public Student(){}

    public Student(String msv, String name, String lop, int countAnswer) {
        this.msv = msv;
        this.name = name;
        this.lop = lop;
        this.countAnswer = countAnswer;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public int getCountAnswer() {
        return countAnswer;
    }

    public void setCountAnswer(int countAnswer) {
        this.countAnswer = countAnswer;
    }

    /*@NonNull
    @Override
    public String toString() {
        StringBuffer data = new StringBuffer();
        *//*data.append("{\"MSV\": \""+ this.msv +"\", ");
        data.append("\"Name\": \""+ this.name +"\", ");
        data.append("\"Lop\": \""+ this.lop +"\", ");
        data.append("\"Point\": " + this.countAnswer + " }");*//*
        data.append("{ \"MSV\": ");
        return data.toString();
    }*/
}
