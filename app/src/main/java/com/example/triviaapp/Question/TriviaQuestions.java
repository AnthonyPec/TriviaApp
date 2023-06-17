package com.example.triviaapp.Question;

import com.example.triviaapp.OpenTriviaAPI;
import com.example.triviaapp.QuizActivity;
import com.example.triviaapp.TriviaQuestion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
public class TriviaQuestions {
    private List<com.example.triviaapp.TriviaQuestion.Question> questions;
    private QuizActivity parent;
    private TriviaQuestion.Question currentQuestion;


    public TriviaQuestions(QuizActivity parent) {
        this.parent = parent;
    }

    /**
     * Loads trivia qustions from opentdb.com via Retrofit
     */
    public void loadQuestions() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        OpenTriviaAPI question = retrofit.create(OpenTriviaAPI.class);


        Call<com.example.triviaapp.TriviaQuestion> call = question.getQuestions(10, 9);
        call.enqueue(new Callback<com.example.triviaapp.TriviaQuestion>() {
            @Override
            public void onResponse(Call<com.example.triviaapp.TriviaQuestion> call, Response<com.example.triviaapp.TriviaQuestion> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                com.example.triviaapp.TriviaQuestion temp = response.body();
                questions = temp.getResults();
                parent.setNextQuestion();
            }

            @Override
            public void onFailure(Call<com.example.triviaapp.TriviaQuestion> call, Throwable t) {
                return;

            }
        });
    }

    /**
     * Gets the next question.
     *
     * @param num Number of question to get
     * @return Question
     * @throws Exception if Question is null
     */
    public void setNextQuestion(int num) throws Exception {
        this.currentQuestion = questions.get(num);
        if (this.currentQuestion == null) {
            throw new Exception("Empty Question");
        }

    }

    public String getQuestionType() {
        return this.currentQuestion.getType();
    }

    public String getQuestionCategory() {
        return this.currentQuestion.getCategory();
    }

    public String getQuestion() {
        return this.currentQuestion.getQuestion();
    }

    public boolean checkAnswer(String answer) {
        return this.currentQuestion.getCorrect_answer().equals(answer);
    }

    public ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add(this.currentQuestion.getCorrect_answer());
        options.addAll(this.currentQuestion.getIncorrect_answers());
        return options;
    }
}
