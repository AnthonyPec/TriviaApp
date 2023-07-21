package com.example.triviaapp2.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.triviaapp2.Model.TriviaQuestion;
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
    public class Questions {
        private List<Question> questions;
//        private TriviaQuestion.Question currentQuestion;
        private MutableLiveData<TriviaQuestion> triviaQuestion;
        private MutableLiveData<Question> currentQuestion = new MutableLiveData<>();

        private int questionNum = 1;

        public Questions() {
            triviaQuestion = new MutableLiveData<>();
        }

        /**
         * Loads trivia questions from opentdb.com via Retrofit
         */
        public void loadQuestions() {

            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://opentdb.com/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            OpenTriviaAPI question = retrofit.create(OpenTriviaAPI.class);


            Call<TriviaQuestion> call = question.getQuestions(30, 9,"multiple");
            call.enqueue(new Callback<TriviaQuestion>() {
                @Override
                public void onResponse(Call<TriviaQuestion> call, Response<TriviaQuestion> response) {
                    if (!response.isSuccessful()) {

                        return;
                    }

                    TriviaQuestion temp = response.body();
                    questions = temp.getResults();
                    triviaQuestion.postValue(response.body());
                    currentQuestion.postValue(questions.get(0));

                }


                @Override
                public void onFailure(Call<TriviaQuestion> call, Throwable t) {
                    triviaQuestion.postValue(null);

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
            this.currentQuestion.setValue(questions.get(num));
            if (this.currentQuestion == null) {
                throw new Exception("Empty Question");
            }

        }

        public boolean checkAnswer(String answer) {

            return this.currentQuestion.getValue().getCorrect_answer().equals(answer);

        }

        public boolean setNextQuestion(){
            if(questionNum < this.questions.size()) {
                this.currentQuestion.setValue(this.questions.get(questionNum));
                questionNum++;
                return true;
            }
            else{
                this.currentQuestion.setValue(null);
                return false;
            }
        }

        public ArrayList<String> getOptions() {
            ArrayList<String> options = new ArrayList<>();
            options.add(this.currentQuestion.getValue().getCorrect_answer());
            options.addAll(this.currentQuestion.getValue().getIncorrect_answers());
            return options;
        }

        public LiveData<Question> getLiveData(){
            return this.currentQuestion;
        }

    }

