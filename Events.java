package com.example.navigationsandtabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Events extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public Events() {
        // Required empty public constructor
    }

    public static Events newInstance(String param1, String param2) {
        Events fragment = new Events();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_events, container, false);

        TextView dailyTaskTitle = myView.findViewById(R.id.dailyTaskTitle);
        TextView dailyTaskDescription = myView.findViewById(R.id.dailyTaskDescription);

        TextView weeklyTaskTitle = myView.findViewById(R.id.weeklyTaskTitle);
        TextView weeklTaskDescription = myView.findViewById(R.id.weeklyTaskDescription);

        final TextView dailyTaskBtn = myView.findViewById(R.id.dailyPointsBtn);
        final TextView weeklyTaskBtn = myView.findViewById(R.id.weeklyPointsBtn);

        dailyTaskTitle.setText("No Plastic");
        dailyTaskDescription.setText("This is a hard one. Don't buy plastic of any kind during the whole week.");

        weeklyTaskTitle.setText("The Before and After");
        weeklTaskDescription.setText("Choose a place that needs cleaning or maintenance, then take a picture before and after you have done something about it and post it online to influence others.");

        dailyTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), UploadImage.class));
                dailyTaskBtn.setEnabled(false);
//                SharedPreferences myScore = getContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
//                int score = myScore.getInt("score", 0);
//                score += 5;
//                SharedPreferences.Editor editor = myScore.edit();
//                editor.putInt("score", score);
//                editor.commit();
//                dailyTaskBtn.setEnabled(false);

            }
        });

        weeklyTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), UploadImage.class));
                weeklyTaskBtn.setEnabled(false);
//                SharedPreferences myScore = getContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
//                int score = myScore.getInt("score", 0);
//                score += 5;
//                SharedPreferences.Editor editor = myScore.edit();
//                editor.putInt("score", score);
//                weeklyTaskBtn.setEnabled(false);
            }
        });
        return myView;
    }
    
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}