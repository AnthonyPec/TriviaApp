package com.example.triviaapp.Question;

import com.example.triviaapp.OpenTriviaAPI;
import com.example.triviaapp.QuizActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TriviaQuestions {
    private List<com.example.triviaapp.TriviaQuestion.Question> questions;
    private QuizActivity parent;


    public TriviaQuestions(QuizActivity parent) {
        this.parent = parent;
    }

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
                parent.start();
            }

            @Override
            public void onFailure(Call<com.example.triviaapp.TriviaQuestion> call, Throwable t) {

            }
        });
    }

    public com.example.triviaapp.TriviaQuestion.Question getQuestion(int num){
        return questions.get(num);
    }

}
