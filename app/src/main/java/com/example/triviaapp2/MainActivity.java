package com.example.triviaapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviaapp2.Model.Question;
import com.example.triviaapp2.ViewModel.TriviaViewModel;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {


    private TriviaViewModel viewModel;
    public TextView nameTextView;
    private RadioGroup radioGroup;
    private boolean start;
    private TextView question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        question_text = (TextView) findViewById(R.id.question_text);
        question_text.setText("Get Ready");
        viewModel = new TriviaViewModel();

        viewModel.init();
        viewModel.getTriviaQuestion().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {

                if(start == false){
                    startTimer();
                    start = true;
                }
                if(question!= null) {
                    setNextQuestion(question);
                }
            }
        });


        this.radioGroup = findViewById(R.id.rGroup);

        for(int i =0; i < this.radioGroup.getChildCount(); i++){
            RadioButton x = (RadioButton) this.radioGroup.getChildAt(i);
            x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit();
                }
            });
        }
    }

    public void setNextQuestion(Question question){


        question_text.setText(question.getQuestion());
        this.radioGroup = findViewById(R.id.rGroup);

        ArrayList<String> options = new ArrayList<>();
        options.add(question.getCorrect_answer());
        options.addAll(question.getIncorrect_answers());
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

        boolean check = viewModel.checkAnswer(radioButton.getText().toString());

        String text = check ? "Correct":"Wrong";
        Toast.makeText(this,
                text, Toast.LENGTH_SHORT).show();

        viewModel.setNextQuestion();

    }

    // make this live data and add to view model
    public void startTimer(){

        TextView timer = findViewById(R.id.timer);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                timer.setText(Long.toString(sec));
            }

            public void onFinish() {
                timer.setText("done!");
            }
        }.start();
    }

}