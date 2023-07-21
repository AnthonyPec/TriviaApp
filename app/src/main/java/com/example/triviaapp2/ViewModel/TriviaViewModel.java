package com.example.triviaapp2.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.triviaapp2.Model.Question;
import com.example.triviaapp2.Model.Questions;
import com.example.triviaapp2.Model.TriviaQuestion;

public class TriviaViewModel extends ViewModel {

    private Questions question;
    private LiveData<TriviaQuestion> triviaQuestion = new MutableLiveData(new TriviaQuestion());
    private LiveData<Question> currentQuestion = new MutableLiveData(new Question());

    public void init(){
        question = new Questions();
        question.loadQuestions();



    }

    public boolean checkAnswer(String answer){
        return question.checkAnswer(answer);
    }

    public void setNextQuestion(){
       question.setNextQuestion();
    }

    public LiveData<Question> getTriviaQuestion(){
        return question.getLiveData();
    }

}
