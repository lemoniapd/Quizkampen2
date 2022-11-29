package Quizkampen.Server;

import Quizkampen.Server.Questions.Question;

import java.util.List;

public class Response {

    protected String operation = "QuestionSent";

    protected List<Question> qList;

    public Response(List<Question> qList){
        this.qList = qList;
    }

    public List<Question> getqList() {
        return qList;
    }

    public String getOperation() {
        return operation;
    }
}
