package com.hungnguyen2809.apptracnghiem.Class;

import java.io.Serializable;

public class Question implements Serializable {
    private int id;
    private String contentQuestion;
    private String contentA;
    private String contentB;
    private String contentC;
    private String contentD;
    private String resultQuestion;

    public Question(int id, String contentQuestion, String contentA, String contentB, String contentC, String contentD, String resultQuestion) {
        this.id = id;
        this.contentQuestion = contentQuestion;
        this.contentA = contentA;
        this.contentB = contentB;
        this.contentC = contentC;
        this.contentD = contentD;
        this.resultQuestion = resultQuestion;
    }

    public Question(String contentQuestion, String contentA, String contentB, String contentC, String contentD, String resultQuestion) {
        this.contentQuestion = contentQuestion;
        this.contentA = contentA;
        this.contentB = contentB;
        this.contentC = contentC;
        this.contentD = contentD;
        this.resultQuestion = resultQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public void setContentQuestion(String contentQuestion) {
        this.contentQuestion = contentQuestion;
    }

    public String getContentA() {
        return contentA;
    }

    public void setContentA(String contentA) {
        this.contentA = contentA;
    }

    public String getContentB() {
        return contentB;
    }

    public void setContentB(String contentB) {
        this.contentB = contentB;
    }

    public String getContentC() {
        return contentC;
    }

    public void setContentC(String contentC) {
        this.contentC = contentC;
    }

    public String getContentD() {
        return contentD;
    }

    public void setContentD(String contentD) {
        this.contentD = contentD;
    }

    public String getResultQuestion() {
        return resultQuestion;
    }

    public void setResultQuestion(String resultQuestion) {
        this.resultQuestion = resultQuestion;
    }

    /* public Question(String contentQuestion, String contentA, String contentB, String contentC, String contentD, String resultQuestion) {
        this.contentQuestion = contentQuestion;
        this.contentA = contentA;
        this.contentB = contentB;
        this.contentC = contentC;
        this.contentD = contentD;
        this.resultQuestion = resultQuestion;
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public void setContentQuestion(String contentQuestion) {
        this.contentQuestion = contentQuestion;
    }

    public String getContentA() {
        return contentA;
    }

    public void setContentA(String contentA) {
        this.contentA = contentA;
    }

    public String getContentB() {
        return contentB;
    }

    public void setContentB(String contentB) {
        this.contentB = contentB;
    }

    public String getContentC() {
        return contentC;
    }

    public void setContentC(String contentC) {
        this.contentC = contentC;
    }

    public String getContentD() {
        return contentD;
    }

    public void setContentD(String contentD) {
        this.contentD = contentD;
    }

    public String getResultQuestion() {
        return resultQuestion;
    }

    public void setResultQuestion(String resultQuestion) {
        this.resultQuestion = resultQuestion;
    }*/
}
