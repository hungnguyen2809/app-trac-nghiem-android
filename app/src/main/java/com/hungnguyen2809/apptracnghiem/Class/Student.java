package com.hungnguyen2809.apptracnghiem.Class;

public class Student {
    private String msv;
    private String name;
    private String lop;
    private int countAnswer;

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
}
