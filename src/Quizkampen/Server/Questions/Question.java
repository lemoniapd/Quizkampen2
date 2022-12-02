package Quizkampen.Server.Questions;

import java.io.Serializable;

public class Question implements Serializable {

    public String category;
    public String question;
    public String[] options;
    public char rightAnswer;


    public Question(String category, String question, String[] options, char rightAnswer) {
        this.category = category;
        this.question = question;
        this.options = options;
        this.rightAnswer = rightAnswer;
    }

    public String getCategory() { return category; }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public char getRightAnswer() {
        return rightAnswer;
    }
}