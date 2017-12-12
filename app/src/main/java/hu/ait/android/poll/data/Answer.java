package hu.ait.android.poll.data;

public class Answer {
    private String answerText;
    private Integer numAnswers = 0;

    public Answer(String answerText) {
        this.answerText = answerText;
    }

    public Answer() {
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getNumAnswers() {
        return numAnswers;
    }

    public void setNumAnswers(Integer numAnswers) {
        this.numAnswers = numAnswers;
    }
}
