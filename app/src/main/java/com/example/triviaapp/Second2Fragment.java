package com.example.triviaapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.triviaapp.databinding.FragmentSecond2Binding;

public class Second2Fragment extends Fragment {

    private FragmentSecond2Binding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecond2Binding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(Second2Fragment.this)
//                        .navigate(R.id.action_Second2Fragment_to_First2Fragment);
//            }
//        });



        TextView timer = view.findViewById(R.id.timer);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                timer.setText(Long.toString(sec));
            }

            public void onFinish() {
                timer.setText("done!");
                ((QuizActivity)getActivity()).finish();
            }
        }.start();


        ((QuizActivity)getActivity()).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}