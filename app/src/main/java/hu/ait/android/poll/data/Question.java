package hu.ait.android.poll.data;

import java.util.HashMap;

public class Question {
    private String uid;
    private String key;
    private String author;
    private String question;
    private HashMap<String, Answer> answers;
    private HashMap<String, String> answeredBy;


    public Question(String uid, String key, String author, String question, HashMap<String, Answer> answers, HashMap<String, String> answeredBy) {
        this.uid = uid;
        this.key = key;
        this.author = author;
        this.question = question;
        this.answers = answers;
        this.answeredBy = answeredBy;
    }

    public Question() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public HashMap<String, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, Answer> answers) {
        this.answers = answers;
    }

    public HashMap<String, String> getAnsweredBy() {
        return answeredBy;
    }

    public void setAnsweredBy(HashMap<String, String> answeredBy) {
        this.answeredBy = answeredBy;
    }
}
