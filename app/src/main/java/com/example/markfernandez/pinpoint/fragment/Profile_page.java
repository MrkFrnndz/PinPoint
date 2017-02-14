package com.example.markfernandez.pinpoint.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markfernandez.pinpoint.MainActivity;
import com.example.markfernandez.pinpoint.R;
import com.example.markfernandez.pinpoint.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_page extends Fragment implements View.OnClickListener {
    private ImageView ivProfile;
    private TextView txtName;
    private TextView txtMName;
    private TextView txtLname;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;


    private Button buttonLogout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_page, container, false);
        ivProfile = (ImageView) rootView.findViewById(R.id.imgProfile);
        txtName = (TextView)rootView.findViewById(R.id.txtName);
        txtMName = (TextView)rootView.findViewById(R.id.txtMiddleName);
        txtLname = (TextView)rootView.findViewById(R.id.txtLastName);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();

        buttonLogout = (Button)rootView.findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(this);

        mDatabase.child("users").child(mUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                String fn = userProfile.getUserName();

                String sUri = userProfile.getUserImage().toString();
//                Uri imgUri = Uri.parse(sUri);
//                Uri mImageUri = userProfile.getUserImage();
                Picasso.with(getContext()).load(sUri).into(ivProfile);
                txtName.setText(fn);
                //ivProfile.setImageURI(imgUri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            return  rootView;
    }


    @Override
    public void onClick(View v) {
        if (v == buttonLogout){
            mFirebaseAuth.signOut();
            getActivity().finish();
            startActivity(new Intent(getActivity(),MainActivity.class));
        }
    }

}
