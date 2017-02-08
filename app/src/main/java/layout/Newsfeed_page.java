package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markfernandez.pinpoint.MyDialogFrag;
import com.example.markfernandez.pinpoint.R;
import com.example.markfernandez.pinpoint.Recycler_View_Adapter;
import com.example.markfernandez.pinpoint.model.UserPost;
import com.example.markfernandez.pinpoint.model.UserProfile;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Newsfeed_page extends Fragment   {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;


    private List<UserPost> data;
    private View rootView;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//    private String postData;

    public Newsfeed_page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_newsfeed_page, container, false);

        data = fill_with_data(); //getting the values

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        final Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        //end of CARDVIEW/RECYCLERVIEW

        mDatabase.child("post").addChildEventListener(new ChildEventListener() {
            public static final String TAG = "MARK's error";

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    try{

                        UserPost userPost = dataSnapshot.getValue(UserPost.class);

                        String author = userPost.getAuthorName();
                        String uid = userPost.getUserId();
                        int emo = userPost.getPostEmotion();
                        String post = userPost.getPostDescription();
                        String date = "";
                        if(userPost.getDateCreatedLong() != 0){
                            date = SIMPLE_DATE_FORMAT.format(new Date(userPost.getDateCreatedLong()));
                            Log.e("MARK log", "Date" + date);
                        }
                        double latlng = 0;
                        data.add(new UserPost(author,uid,emo,post,date,latlng));

                    } catch (Exception ex) {
                        Log.e(TAG, ex.getMessage());
                    }
                }

                adapter.notifyDataSetChanged(); //to load the data if there's a value initially on my Firebase DB

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }


    private List<UserPost> fill_with_data() {
        final List<UserPost> data = new ArrayList<>();
        return data;
    }

//    @Override
//    public void onPushedPin(List<UserPost> data) {
//        data = fill_with_data();
//    }
}
