package com.example.navigationsandtabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PICK_IMAGE_REQUEST = 101;

    ImageView edit;
    Button saveChanges;
    EditText name, birthday, phone, location;
    private OnFragmentInteractionListener mListener;
    int pointValue = 0;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String userUID;
    DocumentReference docRef;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();
        docRef = db.collection(" Users..").document(userUID);

        edit = view.findViewById(R.id.editBtn);
        saveChanges = view.findViewById(R.id.saveBtn);
        name = view.findViewById(R.id.nameEditText);
        location = view.findViewById(R.id.locationEditText);
        phone = view.findViewById(R.id.phoneNoEditText);
        birthday = view.findViewById(R.id.birthdayEditText);

        saveChanges.setVisibility(View.INVISIBLE);
        name.setEnabled(false);
        location.setEnabled(false);
        phone.setEnabled(false);
        birthday.setEnabled(false);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(false);
                location.setEnabled(false);
                phone.setEnabled(false);
                birthday.setEnabled(false);
                saveChanges.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);

                String pangalan = name.getText().toString();
                String kaarawan = birthday.getText().toString();
                String lugar = location.getText().toString();
                String number = phone.getText().toString();

                Map<String, Object> Users = new HashMap<>();
                Users.put("Name", pangalan);
                Users.put("Birthday", kaarawan);
                Users.put("Location", lugar);
                Users.put("Phone", number);
                Users.put("Points", pointValue);

                db
                        .collection("Users..").document(userUID)
                        .set(Users)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                name.setEnabled(true);
                location.setEnabled(true);
                phone.setEnabled(true);
                birthday.setEnabled(true);
                edit.setVisibility(View.INVISIBLE);
                saveChanges.setVisibility(View.VISIBLE);

                docRef
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String namesu = documentSnapshot.getString("Name");
                                String birthdaysu = documentSnapshot.getString("Birthday");
                                String locationsu = documentSnapshot.getString("Location");
                                String phonesu = documentSnapshot.getString("Phone");

                                name.setText(namesu);
                                birthday.setText(birthdaysu);
                                location.setText(locationsu);
                                phone.setText(phonesu);
                            }
                        });
            }
        });
        return view;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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