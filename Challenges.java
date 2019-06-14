package com.example.navigationsandtabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Challenges extends Fragment {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableChallengeList;
    private HashMap<String, InsertChallenge> challengesHashMap;
    private int lastExpandedPosition = -1;

    private OnFragmentInteractionListener mListener;

    public Challenges() {

    }

    public static Challenges newInstance(String param1, String param2) {
        Challenges fragment = new Challenges();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        expandableListView = view.findViewById(R.id.expandableListView);
        challengesHashMap = getChallenges();
        expandableChallengeList = new ArrayList<>(challengesHashMap.keySet());
        expandableListAdapter = new ExpandableListAdapter(getContext(), expandableChallengeList, challengesHashMap);

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    private HashMap<String, InsertChallenge> getChallenges() {
        HashMap<String, InsertChallenge> challengesList = new HashMap<>();

        challengesList.put("Challenge1", new InsertChallenge("Walk an extra mile", "Instead of talking a ride on vehicle going somewhere, why not try to walk?", 5));
        challengesList.put("Challenge2", new InsertChallenge("Photo Activist", "Take photos of unhealthy habits affecting the environment and share them online.", 5));
        challengesList.put("Challenge3", new InsertChallenge("Balde at Tabo", "Instead of using the shower, try to use pale and dipper in taking a bath.", 5));
        challengesList.put("Challenge4", new InsertChallenge("Hour of Vain", "Put your phone away from your sight for an hour.", 5));
        challengesList.put("Challenge5", new InsertChallenge("Vegan for a day", "Eat no meat or the least that you can within the day.", 5));
        challengesList.put("Challenge6", new InsertChallenge("Plant the future", "Plant a tree.", 5));
        challengesList.put("Challenge7", new InsertChallenge("No litter day", "Avoid making litters for the whole day.", 5));

        return challengesList;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<String> list;
        private HashMap<String, InsertChallenge> expandableChallengesDetails;
        private SharedPreferences prefs;

        public ExpandableListAdapter (Context context, List<String> list, HashMap<String, InsertChallenge> challengesDetails) {
            this.context = context;
            this.list = list;
            this.expandableChallengesDetails = challengesDetails;
        }

        @Override
        public int getGroupCount() {
            return list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return list.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this.expandableChallengesDetails.get(this.list.get(groupPosition));
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();

            final InsertChallenge challenges = (InsertChallenge) getChild(groupPosition, 0);

            if (convertView == null) {

                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = layoutInflater.inflate(R.layout.challenges_list, null);
            }

            TextView challengeNameTextView = convertView.findViewById(R.id.challengeNameTextView);

            final TextView duration = convertView.findViewById(R.id.duration);

            final TextView durationText = convertView.findViewById(R.id.durationText);

            challengeNameTextView.setText(challenges.getChallengeName());

            SharedPreferences prefs = context.getSharedPreferences(challenges.challengeName, Context.MODE_PRIVATE);

            if (prefs.getBoolean(challenges.challengeName, false) == true) {

                durationText.setVisibility(View.INVISIBLE);

                CountDownTimer countDownTimer = new CountDownTimer(prefs.getInt("timeLeft", 360000), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        SharedPreferences prefs = context.getSharedPreferences(challenges.challengeName, Context.MODE_PRIVATE);

                        prefs = context.getSharedPreferences(challenges.challengeName, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("timeLeft", (int) millisUntilFinished);
                        editor.apply();

                        int hours = prefs.getInt("timeLeft", 86400000) / 3600000;
                        int minutes = (prefs.getInt("timeLeft", 86400000) / 60000) % 60;
                        int seconds = prefs.getInt("timeLeft", 86400000) % 60000 / 1000;

                        String timeLeftText = "";
                        timeLeftText += hours + ":";

                        if (minutes >= 60) minutes = 0;

                        if (minutes < 10) timeLeftText += "0";

                        timeLeftText += minutes;
                        timeLeftText += ":";

                        if (seconds < 10) timeLeftText += "0";
                        timeLeftText += seconds;
                        duration.setText(timeLeftText);
                    }

                    @Override
                    public void onFinish() {

                        SharedPreferences myScore = context.getSharedPreferences(mAuth.getUid(), Context.MODE_PRIVATE);
                        int score = myScore.getInt("score", 0);
                        score += challenges.getPoints();
                        SharedPreferences.Editor editor = myScore.edit();
                        editor.putInt("score", score);
                        editor.commit();

                        duration.setText("FINISHED");
                    }

                }.start();
            } else {
                durationText.setVisibility(View.INVISIBLE);
                duration.setText(challenges.getPoints() + " POINTS");
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final InsertChallenge challenges = (InsertChallenge) getChild(groupPosition, 0);

            if (convertView == null) {

                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = layoutInflater.inflate(R.layout.list_layout, null);
            }

            final LinearLayout acceptIcon = convertView.findViewById(R.id.acceptIcon);

            final ImageView acceptIconImage = convertView.findViewById(R.id.acceptIconImage);

            final TextView challengeAcceptTextView = convertView.findViewById(R.id.challengeAcceptTextView);

            final TextView challengeDescriptionTextView = convertView.findViewById(R.id.challengeDescriptionTextView);

            challengeDescriptionTextView.setText(challenges.challengeDescription);

            final SharedPreferences prefs = context.getSharedPreferences(challenges.challengeName, Context.MODE_PRIVATE);

            acceptIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(challenges.getChallengeName(), true);
                    editor.commit();

                    acceptIconImage.setImageResource(R.drawable.challenge_accepted);
                    challengeAcceptTextView.setText("Challenge Accepted");
                    challengeAcceptTextView.setTextColor(Color.parseColor("#FF83867E"));
                }
            });

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            convertView.startAnimation(animation);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
