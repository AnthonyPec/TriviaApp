package com.example.triviaapp2.Model;
import com.example.triviaapp2.Model.Question;
import java.util.List;

public class TriviaQuestion {
    private int response;
    private List<Question> results;

    public int getResponse() {
        return response;
    }

    public List<Question> getResults() {
        return results;
    }
}
