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
import android.widget.ImageButton;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Newsfeed_page extends Fragment   {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseRefPost;
    private DatabaseReference mDatabaseRefLike;
    private String mUserId;

    private View rootView;
    private boolean mProcessLike = false;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    RecyclerView recyclerView;

    public Newsfeed_page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseRefPost = FirebaseDatabase.getInstance().getReference().child("post");
        mDatabaseRefLike = FirebaseDatabase.getInstance().getReference().child("like");
        mUserId = mFirebaseUser.getUid();

        //Saving offline from cache
        mDatabaseRefPost.keepSynced(true);
        mDatabaseRefLike.keepSynced(true);

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_newsfeed_page, container, false);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);




        FirebaseRecyclerAdapter<UserPost,NewsfeedViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserPost, NewsfeedViewHolder>(
                UserPost.class,
                R.layout.row_layout,
                NewsfeedViewHolder.class,
                mDatabaseRefPost
        ) {

            @Override
            protected void populateViewHolder(NewsfeedViewHolder viewHolder, UserPost model, int position) {
                final String mPostKey = getRef(position).getKey();

                viewHolder.setLikeButton(mPostKey);
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

                viewHolder.mLikeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProcessLike = true;

                            mDatabaseRefLike.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserPost userLike = new UserPost();
                                    int likeCount = 0;
                                    int gLikes = 0;
                                    if(mProcessLike){

                                        if(dataSnapshot.child(mPostKey).hasChild(mUserId)){
                                            mDatabaseRefLike.child(mPostKey).child(mUserId).removeValue();
                                            mProcessLike = false;

                                        }else {
                                            mDatabaseRefLike.child(mPostKey).child(mUserId).setValue(likeCount + 1);
                                            userLike.setPostLikes(likeCount);
                                            mProcessLike = false;
                                        }

                                        for (DataSnapshot snap: dataSnapshot.getChildren()) {
                                            UserPost getLikes= snap.getValue(UserPost.class);
                                            gLikes = getLikes.getPostLikes();
                                        }
                                        mDatabaseRefPost.child(mPostKey).child("postLikeUgh").setValue(gLikes);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }

//NEW CLASS
    public static class NewsfeedViewHolder extends RecyclerView.ViewHolder{
        View mView;
        ImageButton mLikeButton;
        DatabaseReference mDatabaseLike;
        FirebaseAuth mAuth;

        public NewsfeedViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mLikeButton = (ImageButton) mView.findViewById(R.id.btnLike);

            mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("like");
            mAuth = FirebaseAuth.getInstance();

            mDatabaseLike.keepSynced(true);
        }

        public void setLikeButton(final String mPostKey){
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(mPostKey).hasChild(mAuth.getCurrentUser().getUid())){
                            mLikeButton.setImageResource(R.drawable.ic_thumb_up_green_36dp);

                        }else {
                            mLikeButton.setImageResource(R.drawable.ic_thumb_up_gray_36dp);
                        }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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
