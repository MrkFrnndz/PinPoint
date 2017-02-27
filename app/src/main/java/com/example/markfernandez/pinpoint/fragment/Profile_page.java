package com.example.markfernandez.pinpoint.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markfernandez.pinpoint.MainActivity;
import com.example.markfernandez.pinpoint.R;
import com.example.markfernandez.pinpoint.model.UserPost;
import com.example.markfernandez.pinpoint.model.UserProfile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_page extends Fragment implements View.OnClickListener {
    private CircleImageView ivProfile;
    private TextView txtName;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseRefUser;
    private DatabaseReference mDatabaseRefUserPost;
    private String mUserId;

    private Button buttonLogout;

    //START
    private View rootView;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    RecyclerView recyclerView;

    public Profile_page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile_page, container, false);
        ivProfile = (CircleImageView) rootView.findViewById(R.id.imgProfile);
        txtName = (TextView)rootView.findViewById(R.id.txtName);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseRefUser = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();
        mDatabaseRefUserPost = FirebaseDatabase.getInstance().getReference().child("user-post").child(mUserId);

        buttonLogout = (Button)rootView.findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(this);



        //RECYCLERVIEW
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        FirebaseRecyclerAdapter<UserPost,ProfileViewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<UserPost, ProfileViewHolder>
                        (UserPost.class, R.layout.row_layout2, ProfileViewHolder.class,mDatabaseRefUserPost) {

            @Override
            protected void populateViewHolder(ProfileViewHolder viewHolder, UserPost model, int position) {
                viewHolder.setAuthorImage(getContext(),model.getAuthorImage());
                viewHolder.setAuthorName(model.getAuthorName());
                viewHolder.setDateCreated(model.getDateCreatedLong());
                viewHolder.setPostDescription(model.getPostDescription());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //DO THIS WHEN ITEM CLICKED!
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        recyclerView.setLayoutManager(mLayoutManager);


        mDatabaseRefUser.child("users").child(mUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                String fn = userProfile.getUserName();

                String sUri = userProfile.getUserImage().toString();

                if(getContext() !=null){
                    Picasso.with(getContext()).load(sUri).noFade().into(ivProfile);
                    txtName.setText(fn);
                }else{
                    Toast.makeText(getContext(), "Slow internet connection.", Toast.LENGTH_SHORT).show();
                }
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

    //NEW CLASS
    public static class ProfileViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setAuthorImage(Context ctx, String authorImage){
            CircleImageView post_authorimage = (CircleImageView)mView.findViewById(R.id.iv_userImage);
            Picasso.with(ctx).load(authorImage).noFade().into(post_authorimage);

        }

        public void setAuthorName(String authorName){
            TextView post_authorname = (TextView) mView.findViewById(R.id.txtAuthorName);
            post_authorname.setText(authorName);
        }

        public void setPostDescription(String postDescription){
            TextView post_description = (TextView) mView.findViewById(R.id.txtDescription);
            post_description.setText(postDescription);
        }

        public void setDateCreated(long dateCreatedLong){
            TextView post_date = (TextView) mView.findViewById(R.id.txtDateCreated);
            String date = SIMPLE_DATE_FORMAT.format(new Date(dateCreatedLong));
            post_date.setText(date);
        }
    }

}
