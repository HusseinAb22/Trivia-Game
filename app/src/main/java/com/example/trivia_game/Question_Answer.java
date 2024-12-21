package com.example.trivia_game;

public class Question_Answer {
    private String question;
    private String[] answers;
    private String correct_answer;

    public Question_Answer(String[] answers, String correct_answer, String question) {
        this.answers = answers;
        this.correct_answer = correct_answer;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }
}
