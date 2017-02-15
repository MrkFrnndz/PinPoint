package com.example.markfernandez.pinpoint.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markfernandez.pinpoint.R;
import com.example.markfernandez.pinpoint.model.UserPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Newsfeed_page extends Fragment   {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    private View rootView;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    RecyclerView recyclerView;

    public Newsfeed_page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("post");
        mUserId = mFirebaseUser.getUid();

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_newsfeed_page, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<UserPost,NewsfeedViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserPost, NewsfeedViewHolder>(
                UserPost.class,
                R.layout.row_layout,
                NewsfeedViewHolder.class,
                mDatabase
            ) {

            @Override
            protected void populateViewHolder(NewsfeedViewHolder viewHolder, UserPost model, int position) {
                viewHolder.setAuthorImage(getContext(),model.getAuthorImage());
                viewHolder.setAuthorName(model.getAuthorName());
                viewHolder.setDateCreated(model.getDateCreatedLong());
                viewHolder.setPostDescription(model.getPostDescription());
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class NewsfeedViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public NewsfeedViewHolder(View itemView) {
            super(itemView);
            itemView = mView;
        }

        public void setAuthorImage(Context ctx, String authorImage){
            ImageView post_authorimage = (ImageView)itemView.findViewById(R.id.iv_userImage);
            Picasso.with(ctx).load(authorImage).into(post_authorimage);

        }

        public void setAuthorName(String authorName){
            TextView post_authorname = (TextView) itemView.findViewById(R.id.txtAuthorName);
            post_authorname.setText(authorName);
        }

        public void setDateCreated(long dateCreatedLong){
            TextView post_date = (TextView) itemView.findViewById(R.id.txtAuthorName);
            String date = SIMPLE_DATE_FORMAT.format(new Date(dateCreatedLong));
            post_date.setText(date);
        }

        public void setPostDescription(String postDescription){
            TextView post_description = (TextView) itemView.findViewById(R.id.txtAuthorName);
            post_description.setText(postDescription);
        }
    }
}
