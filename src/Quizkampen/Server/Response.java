package Quizkampen.Server;

import Quizkampen.Server.Questions.Question;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    protected String operation;

    protected String message;

    protected List<Question> qList;

    public Response(List<Question> qList){
        this.qList = qList;
    }

    public Response(String operation) {
        this.operation = operation;
    }

    public Response(String operation, List<Question> qList) {
        this.operation = operation;
        this.qList = qList;
    }

    public Response(String operation, String message) {
        this.operation = operation;
        this.message = message;
    }

    public List<Question> getqList() {
        return qList;
    }

    public String getMessage() {
        return message;
    }

    public String getOperation() {
        return operation;
    }
}
