package com.example.navigationsandtabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        VideoView videoVideo = view.findViewById(R.id.video_view);
        String videoPath = "android.resource://" + "com.example.navigationsandtabs" + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoVideo.setVideoURI(uri);

        MediaController mediaController = new MediaController(getContext());
        videoVideo.setMediaController(mediaController);
        mediaController.setAnchorView(videoVideo);

        final TextView pointsTextView = view.findViewById(R.id.pointsTextView);

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}