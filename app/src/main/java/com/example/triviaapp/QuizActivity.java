package com.example.triviaapp;

import android.os.Bundle;

import com.example.triviaapp.Question.TriviaQuestions;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.triviaapp.databinding.ActivityQuizBinding;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityQuizBinding binding;
    private TriviaQuestions triviaQuestion;
    private int nextQuestion = 0;
    TextView textView;
    private RadioGroup radioGroup;
    private Button startButton;
    private int numCorrect=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_quiz);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        triviaQuestion = new TriviaQuestions(this);
        triviaQuestion.loadQuestions();

        this.startButton = findViewById(R.id.button_first);
        this.startButton.setEnabled(false);



    }

    public void ready(){
        this.startButton.setEnabled(true);

    }


    public void start(){
        this.textView = findViewById(R.id.textview_first);

        this.radioGroup = findViewById(R.id.rGroup);

        for(int i =0; i < this.radioGroup.getChildCount(); i++){
            RadioButton x = (RadioButton) this.radioGroup.getChildAt(i);
            x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit();
                    setNextQuestion();
                }
            });
        }
        setNextQuestion();
    }


    /**
     * Sets next question in GUI
     */
    public void setNextQuestion(){
        try {
            triviaQuestion.setNextQuestion(nextQuestion);
            nextQuestion++;
        }
        catch(Exception ex){
            this.end();
        }




        this.textView.setText(triviaQuestion.getQuestion());

        if(triviaQuestion.getQuestionType().equals("boolean")){
//            createTFQuestion();
            setNextQuestion();
        }
        else {
            createMultCQuestion();
        }
    }


    private void createTFQuestion(){

    }


    private void createMultCQuestion(){
        ArrayList<String> options = this.triviaQuestion.getOptions();
        Collections.shuffle(options);
        for(int i =0; i < this.radioGroup.getChildCount(); i++){
            RadioButton x = (RadioButton) this.radioGroup.getChildAt(i);
            x.setText(options.get(i));
        }
    }

    public void submit(){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioGroup.clearCheck();
        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        boolean check = this.triviaQuestion.checkAnswer(radioButton.getText().toString());

        String text = check ? "Correct":"Wrong";
        Toast.makeText(this,
                text, Toast.LENGTH_SHORT).show();

        if(check){
            this.numCorrect++;
        }
    }


    public void end(){
        finish();
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_quiz);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}