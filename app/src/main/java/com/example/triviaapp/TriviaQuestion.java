package com.example.triviaapp;

import com.google.gson.annotations.SerializedName;

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

    public class Question {

        private String category;
        private String type;
        private String difficulty;
        private String question;
        private String correct_answer;
        private List<String> incorrect_answers;

        public String getCategory() {
            return category;
        }

        public String getType() {
            return type;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public String getQuestion() {
            return question;
        }

        public String getCorrect_answer() {
            return correct_answer;
        }

        public List<String> getIncorrect_answers() {
            return incorrect_answers;
        }

    }

}
