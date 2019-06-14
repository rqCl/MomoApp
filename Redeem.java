package com.example.navigationsandtabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Redeem extends Fragment {

    ArrayList<AddReward> rewards;
    private ListView listView;

    private OnFragmentInteractionListener mListener;

    public Redeem() {
        // Required empty public constructor
    }

    public static Redeem newInstance(String param1, String param2) {
        Redeem fragment = new Redeem();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_redeem, container, false);

        final TextView pointsTextView = view.findViewById(R.id.pointsTextView);

//        SharedPreferences myScore = getContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
//        int score = myScore.getInt("score", 0);
//
//        pointsTextView.setText(score + "");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userUID = mAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection(" Users..").document(userUID);

        SharedPreferences myScore = getContext().getSharedPreferences(userUID, Context.MODE_PRIVATE);
        int score = myScore.getInt("score", 0);

        pointsTextView.setText(score + "");


//        docRef
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        String points = documentSnapshot.getString("Points");
//
//                        pointsTextView.setText(points);
//                    }
//                });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rewards = new ArrayList<>();
        ListView listView = view.findViewById(R.id.listView);
        ListAdapter listAdapter = new ListAdapter(getContext(), rewards);

        rewards.add(new AddReward("Regular Load 10", 5, R.drawable.globe_logo));
        rewards.add(new AddReward("Regular Load 20", 20, R.drawable.globe_logo));
        rewards.add(new AddReward("Regular Load 10", 5, R.drawable.smart_logo));
        rewards.add(new AddReward("Regular Load 20", 20, R.drawable.smart_logo));

        listView.setAdapter(listAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class ListAdapter implements android.widget.ListAdapter {

        ArrayList<AddReward> rewards;
        Context context;

        public ListAdapter (Context context, ArrayList<AddReward> rewards) {
            this.context = context;
            this.rewards = rewards;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return rewards.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final AddReward reward = rewards.get(position);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = layoutInflater.inflate(R.layout.list_view, null);
            }

            TextView title = convertView.findViewById(R.id.txttitle);
            TextView cost = convertView.findViewById(R.id.txtcost);
            ImageView image = convertView.findViewById(R.id.imageView);
            Button redeemBtn = convertView.findViewById(R.id.buget);

            title.setText(reward.getReward());

            cost.setText(reward.getCost() + " points");

            image.setImageResource(reward.getImg());

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            final String userUID = mAuth.getCurrentUser().getUid();
            DocumentReference docRef = db.collection(" Users..").document(userUID);

            redeemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences myScore = context.getSharedPreferences(userUID, Context.MODE_PRIVATE);
                    int score = myScore.getInt("score", 0);

                    if (score < reward.getCost()) {
                        Toast.makeText(getContext(), "Reward points insufficient", Toast.LENGTH_LONG).show();
                    } else {
                        score -= reward.getCost();
                        SharedPreferences.Editor editor = myScore.edit();
                        editor.putInt("score", score);
                        editor.commit();
                    }

//                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
//                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                    final String userUID = mAuth.getCurrentUser().getUid();
//                    final DocumentReference docRef = db.collection(" Users..").document(userUID);
//
//                    docRef
//                            .get()
//                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                @Override
//                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    String points = documentSnapshot.getString("Points");
//                                    int pointsInt = Integer.parseInt(points);
//
//                                    if (pointsInt <reward.getCost()) {
//
//                                        String pointsNew = Integer.toString(pointsInt - reward.getCost());
//
//                                        Map<String, Object> Users = new HashMap<>();
//                                        Users.put("Points", pointsNew);
//
//                                        db
//                                                .collection("Users..").document(userUID)
//                                                .set(Users)
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//                                                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_LONG)
//                                                                .show();
//                                                    }
//                                                })
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG)
//                                                                .show();
//                                                    }
//                                                });
//
//                                    } else {
//                                        Toast.makeText(getContext(), "Reward points insufficient", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            });
                }
            });

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }
}